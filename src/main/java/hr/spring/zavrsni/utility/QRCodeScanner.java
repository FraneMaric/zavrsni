package hr.spring.zavrsni.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class QRCodeScanner {
    public String readQRCode(File qrCodeImage) throws ChecksumException, FormatException {
        try {
            if (!qrCodeImage.exists()) {
                throw new IOException("File not found: " + qrCodeImage.getAbsolutePath());
            }
            BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            QRCodeReader reader = new QRCodeReader();
            Result result = reader.decode(bitmap);
            
            return result.getText();
        }   catch (IOException e) {
            e.printStackTrace();
            return "prvi";
        } catch (NotFoundException e) {
            e.printStackTrace();
            return "Drugi";
        }
        
    }
}