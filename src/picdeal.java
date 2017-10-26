import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.awt.image.JPEGImageDecoder;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.io.FileDescriptor.out;

/**
 * Created by Administrator on 2017/10/24.
 */
public class picdeal {
    public static void main(String[] args) throws IOException {
        int dip1,dip2,dip3,dip4;
        BufferedImage image = ImageIO.read(new File("d:\\one.png"));
        int width = image.getWidth();
        int height = image.getHeight();

//        //切角加边框
//        BufferedImage rounded = setRadius(image, (width*34/100), 2, 2);
//        ImageIO.write(rounded, "png", new File("d:\\one.png"));

       //判断是否有边角，就是判断四角的像素点
        //boolean isTransparent = true;
        boolean isWhite = false;
        dip1=image.getRGB(0,0);
        dip2=image.getRGB(0,height-1);
        dip3=image.getRGB(width-1,0);
        dip4=image.getRGB(width-1,height-1);
        //if(dip1!=-1||dip2!=-1||dip3!=-1||dip4!=-1){isTransparent = false;}
        if(panduan((dip1&0xFFFFFF))&&panduan((dip2&0xFFFFFF))&&panduan((dip3&0xFFFFFF))&&panduan((dip4&0xFFFFFF))){
            isWhite = true;
        }
        if(isWhite){
            System.out.println("无边角图片");
        }else{
            System.out.println("有边角图片");
        }
    }








    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    static boolean panduan(int i){
        boolean flag=false;
        //flase代表有边框
        if(i==8421504)flag = true;
        return flag;
    }

    static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        g2.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }

    public static BufferedImage setRadius(BufferedImage srcImage, int radius, int border, int padding) throws IOException{
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;

        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setColor(Color.WHITE);
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.drawImage(setClip(srcImage, radius), padding, padding, null);
        if(border !=0){
            gs.setColor(Color.GRAY);
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, radius, radius);
        }
        gs.dispose();
        return image;
    }

    /**
     * 图片设置圆角
     * @param srcImage
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage) throws IOException{
        int radius = (srcImage.getWidth() + srcImage.getHeight()) / 6;
        return setRadius(srcImage, radius, 2, 5);
    }

    /**
     * 图片切圆角
     * @param srcImage
     * @param radius
     * @return
     */
    public static BufferedImage setClip(BufferedImage srcImage, int radius){
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        gs.drawImage(srcImage, 0, 0, null);
        gs.dispose();
        return image;
    }
}
