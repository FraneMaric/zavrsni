package hr.spring.zavrsni.controllers;

import java.util.List;
import java.util.Map;
import java.util.Random;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import hr.spring.zavrsni.models.ErrorModel;
import hr.spring.zavrsni.models.FileModel;
import hr.spring.zavrsni.models.Korisnik;
import hr.spring.zavrsni.models.Mail;
import hr.spring.zavrsni.services.FileService;
import hr.spring.zavrsni.services.KorisnikService;
import hr.spring.zavrsni.services.MailService;
import hr.spring.zavrsni.services.StorageService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/file")
public class FileController {

    @Autowired
    private StorageService s3Service;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/send")
    public String send(HttpSession session, Model model) {
        model.addAttribute("userSending", session.getAttribute("user"));
        return "file/send";
    }

    @GetMapping("/addFile")
    public String addFile(Model model) {
        // model.addAttribute("file", new File());
        return "/file/addFile";
    }

    @GetMapping("/inbox")
    public String inbox(HttpSession session, Model model) {
        // ArrayList<Mail> listaMailova = (ArrayList<Mail>) mailService
        // .findAllByRecever(session.getAttribute("username").toString());
        ArrayList<Mail> listaMailova = (ArrayList<Mail>) mailService
                .findAllByNameAndSurname(session.getAttribute("nameSurname").toString());
        model.addAttribute("lista", listaMailova);

        listaMailova.addAll((ArrayList<Mail>) mailService.findAllByRecever(session.getAttribute("name").toString()));
        return "file/inbox";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("id") Long id) {
        FileModel file = fileService.findById(id);
        byte[] fileContent = s3Service.download(file.getFilePath());

        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("fileId") long fileId, @RequestParam("messageId") long messageId) {
        // fileService.delete(id);
        Mail mail = mailService.findById(messageId);
        FileModel fileModel = fileService.findById(mail.getFileId());
        File file = new File(fileModel.getFilePath());
        file.delete();
        fileService.delete(fileModel.getId());
        mailService.delete(messageId);
        return "redirect:/file/inbox";
    }

    @PostMapping("/searchInbox")
    @ResponseBody
    public ArrayList<Mail> searchInbox(@RequestParam("search") String search, HttpSession session) {
        ArrayList<Mail> listaMailova = (ArrayList<Mail>) mailService.findAllBySearch(search.toLowerCase());

        // Create an iterator to iterate over the ArrayList
        Iterator<Mail> iterator = listaMailova.iterator();
        while (iterator.hasNext()) {
            Mail mail = iterator.next();
            if (!mail.getRecever().contains(session.getAttribute("username").toString())) {
                // Remove the current element using the iterator's remove() method
                iterator.remove();
            }
        }
        return listaMailova;
    }

    @PostMapping("/uploadPDF")
    public ResponseEntity<ErrorModel> uploadFile(@RequestParam("files") List<MultipartFile> files,
            HttpSession session) {
        ErrorModel errorModel = new ErrorModel();
        for (MultipartFile file : files) {
            try {
                // Save the uploaded file
                byte[] pdfBytes = file.getBytes();

                // Process the PDF file to find and read PDF417 barcode
                String barcodeData = processPdf(pdfBytes);
                Random rnd = new Random();
                String newPath = "C:\\Faks\\Zavrsni\\zavrsni\\Files\\pdf\\" + rnd.nextInt(1000000)
                        + file.getOriginalFilename();

                File newSavedFile = new File(newPath);
                file.transferTo(newSavedFile);
                s3Service.saveFile(newSavedFile);
                FileModel model = new FileModel();
                Mail mail = new Mail();
                model.setFileName(file.getOriginalFilename());
                model.setFilePath(newPath);
                model.setType(".pdf");
                model.setSender(session.getAttribute("username").toString());
                String[] recever = barcodeData.split("\n");
                Boolean exsist = testUsername(recever[3]);
                if (exsist == false) {
                    throw new userNameException(recever[3]);
                }
                model.setRecever(recever[6]);
                fileService.saveFile(model);
                mail.setSender(recever[6]);
                mail.setRecever(recever[3]);
                mail.setVrijeme(LocalDate.now().toString());
                mail.setMessage(recever[13]);
                mail.setFileId(model.getId());
                mail.setFileName(model.getFileName());
                // String[] imePrezime = recever[4].split(" ");
                // mail.setReceverIme(imePrezime[1]);
                // mail.setReceverPrezime(imePrezime[0]);
                mailService.saveMail(mail);

            } catch (userNameException ex) {
                errorModel.addUsername(ex.getMessage());
            } catch (Exception e) {
                errorModel.addFile(file.getOriginalFilename());
            }

        }
        errorModel.removeDuplicateFiles();
        errorModel.removeDuplicateUsernames();
        return ResponseEntity.status(HttpStatus.OK).body(errorModel);
    }

    private String processPdf(byte[] pdfBytes) throws IOException, NotFoundException {
        // Load the PDF document
        PDDocument document = Loader.loadPDF(pdfBytes);

        // Create a PDF renderer
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // Initialize the barcode reader
        MultiFormatReader reader = new MultiFormatReader();

        // Set up barcode hints
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, Collections.singletonList(BarcodeFormat.PDF_417));

        String barcodeData = null;

        // Iterate through all pages to find the barcode
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = pdfRenderer.renderImageWithDPI(i, 600);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            try {
                Result result = reader.decode(binaryBitmap, hints);
                barcodeData = result.getText();
                break;
            } catch (NotFoundException e) {
                // Continue to the next page
                System.out.println("No barcode found on page " + (i + 1));
            }
        }

        // Close the PDF document
        document.close();

        if (barcodeData == null) {
            System.out.println("No PDF417 barcode found in the document.");
        }

        return barcodeData;
    }


    // private Boolean testUsername(String recever) {
    // // recever = recever.trim();
    // Iterable<Korisnik> korisnici = korisnikService.getAllKorisnik();
    // Boolean exsist = false;
    // for (Korisnik korisnik : korisnici) {
    // // String kor = korisnik.getPrezime() + korisnik.getIme();
    // String kor = userStringSetter(korisnik);
    // System.out.println(kor);
    // if (kor.equalsIgnoreCase(recever)) {
    // exsist = true;
    // }
    // }
    // return exsist;
    // }

    // private String userStringSetter(Korisnik user) {
    // String kor = "";
    // if(user.getPrezime().trim()!=""&&user.getPrezime()!="
    // "&&user.getPrezime()!=null){ //TODO: Testirat
    // kor=kor.concat(user.getPrezime());
    // }if(user.getIme()!=null&&user.getIme()!=" ")

    // {
    // kor = kor.concat(user.getIme());
    // }

    // return kor;
    // }

    public boolean testUsername(String recever) {
        recever = normalise(recever);
        Iterable<Korisnik> users = korisnikService.getAllKorisnik();
        for (Korisnik user : users) {

            // Split the inputs into components (e.g., ["john", "doe"])
            String[] parts1 = recever.split(" ");
            String[] parts2 = (user.getIme() + " " + user.getPrezime()).split(" ");

            for (int i = 0; i < parts2.length; i++) {
                parts2[i] = normalise(parts2[i]);
            }
            // Sort the arrays to make the comparison order-independent
            Arrays.sort(parts1);
            Arrays.sort(parts2);

            if (Arrays.equals(parts1, parts2)) {
                return true;
            }
        }
        return false;
    }

    public static String normalise(String input) {
        return input.trim().toLowerCase();
    }
}

class userNameException extends Exception {
    public userNameException() {

    }

    public userNameException(String message) {
        super(message);
    }
}

class errorException extends Exception {
    public errorException() {

    }

    public errorException(String message) {
        super(message);
    }
}