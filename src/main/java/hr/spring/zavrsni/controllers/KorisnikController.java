package hr.spring.zavrsni.controllers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hr.spring.zavrsni.models.FileModel;
import hr.spring.zavrsni.models.Korisnik;
import hr.spring.zavrsni.models.Mail;
import hr.spring.zavrsni.services.FileService;
import hr.spring.zavrsni.services.KorisnikService;
import hr.spring.zavrsni.services.MailService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/korisnik")
public class KorisnikController {

	@Autowired
	private KorisnikService korisnikService;
	@Autowired
	private FileService fileService;
	@Autowired
	private MailService mailService;

	public KorisnikController(KorisnikService korisnikService/* , PasswordEncoder encoder */) {
		this.korisnikService = korisnikService;
		// this.encoder = encoder;
	}

	@GetMapping("/listAll")
	public String getAllKorisnik(Model model, HttpSession session) {
		ArrayList<Korisnik> popisKorisnika = (ArrayList<Korisnik>) korisnikService.getAllKorisnik();
		model.addAttribute("listKorisnika", popisKorisnika);
		model.addAttribute("userType", session.getAttribute("type"));
		if (session.getAttribute("type").equals("admin")) {
			return "korisnik/indexKorisnik";
		} else {
			return "testiranje/faliasi";
		}
	}

	@GetMapping("/create")
	public String createNewKorisnikRedirect() {
		return "korisnik/createUser";
	}

	@PostMapping("/createNew")
	public String createNewKorisnik(String username,String name, String surname, String password, String OIB) {
		Korisnik korisnik = new Korisnik(username,name,surname, password,OIB);
		korisnik.setType("user");
		if (isUsernameFree(username).equals("false")) {
			return "testiranje/faliasi";
		} else {
			korisnikService.createKorisnik(korisnik);
			return "testiranje/dobroe";
		}
	}

	@GetMapping("/login")
	public String login() {
		return "korisnik/login";
	}

	@PostMapping("/loginPOST")
	public String logIn(String username, String password, HttpSession session, Model model) {
		Korisnik korisnik = korisnikService.findKorisnikbyUsername(username);
		if (korisnik != null) {
			if (korisnik.getPassword().equals(password)) {
				session.setAttribute("user", korisnik);
				session.setAttribute("username", korisnik.getUserName());
				session.setAttribute("type", korisnik.getType());
				model.addAttribute("user", session.getAttribute("username"));
				return "redirect:/file/inbox";
			} else {
				return "testiranje/faliasi";
			}
		} else {
			return "testiranje/faliasi";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/korisnik/login";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, Model model) {
		Korisnik korisnik = korisnikService.findKorisnikById(id);
		model.addAttribute("korisnik", korisnik);
		return "korisnik/editKorisnik";
	}

	@PostMapping("/editPOST")
	public String editPost(String userName,String name,String surname, String password, String type, Long id) {
		Korisnik korisnik = korisnikService.findKorisnikById(id);
		if (!userName.equals("")) {
			if (korisnikService.userNameTaken(userName) == false) {
				String oldName = korisnik.getUserName();
				korisnik.setUserName(userName);
				changeUsername(oldName, userName);
			}
		} else {
			korisnik.setUserName(korisnik.getUserName());
		}
		if (!name.isEmpty()) {
			korisnik.setIme(name);
		} else {
			korisnik.setIme(korisnik.getIme());
		}
		if (!surname.isEmpty()) {
			korisnik.setPrezime(surname);
		} else {
			korisnik.setPrezime(korisnik.getPrezime());
		}
		if (!password.isEmpty()) {
			korisnik.setPassword(password);
		} else {
			korisnik.setPassword(korisnik.getPassword());
		}
		if (!type.isEmpty()) {
			korisnik.setType(type);
		} else {
			korisnik.setType(korisnik.getType());
		}
		korisnikService.editKorisnik(korisnik);
		return "redirect:/korisnik/listAll";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, Model model) {
		Korisnik korisnik = korisnikService.findKorisnikById(id);
		korisnikService.deleteKorisnik(korisnik);
		return "testiranje/dobroe";
	}

	private void changeUsername(String oldName, String userName) {

		List<Mail> listMail = mailService.findAllBySender(oldName);
		for (Mail mail : listMail) {
			mail.setSender(userName);
			mailService.saveMail(mail);
		}
		List<Mail> lista = mailService.findAllByRecever(oldName);
		for (Mail mail : lista) {
			mail.setRecever(userName);
			mailService.saveMail(mail);
		}
		Iterable<FileModel> listFile = fileService.findAllByRecever(oldName);
		for (FileModel file : listFile) {
			file.setRecever(userName);
			fileService.saveFile(file);
		}
		listFile = fileService.findAllBySender(oldName);
		for (FileModel file : listFile) {
			file.setSender(userName);
			fileService.saveFile(file);
		}
	}

	@GetMapping("/usernameCheck")
	@ResponseBody
	public String isUsernameFree(@RequestParam("username") String username) {
		ArrayList<Korisnik> popisKorisnika = (ArrayList<Korisnik>) korisnikService.getAllKorisnik();
		List<String> lista = new ArrayList<String>();
		for (Korisnik korisnik : popisKorisnika) {
			lista.add(korisnik.getUserName().toString());
		}
		if (lista.contains(username)) {
			return "false";
		} else {
			return "true";
		}
	}

	@GetMapping("/getUsername")
	@ResponseBody
	public String getUsername(HttpSession session) {
		Enumeration<String> sessionName = session.getAttributeNames();
		if (sessionName.hasMoreElements()) {
			return session.getAttribute("username").toString();
		} else {
			return null;
		}
	}

	@GetMapping("/sessionCheck")
	@ResponseBody
	public String sessionCheck(HttpSession session) {
		if(session.getAttributeNames().hasMoreElements()==true){
		return "true";
		}
		else{
			return "false";
		}
	}


	@GetMapping("/getType")
	@ResponseBody
	public String getType(HttpSession session) {
		Enumeration<String> sessionName = session.getAttributeNames();
		if (sessionName.hasMoreElements()) {
			return session.getAttribute("type").toString();
		} else {
			return null;
		}
	}

}
