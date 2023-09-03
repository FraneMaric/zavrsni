package hr.spring.zavrsni.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.pdf417.PDF417Reader;

public class PDF417 {
    public static String readPdf417(byte[] imageBytes) throws NotFoundException, IOException {
        try{
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new PDF417Reader();
        Result result = reader.decode(bitmap);
        return result.getText();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
