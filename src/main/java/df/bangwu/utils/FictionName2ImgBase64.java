package df.bangwu.utils;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FictionName2ImgBase64 {
    public static String FictionName2ImgBase64(String imgName,int imgWidth,int imgHeight,String fontName){
        boolean success = str2img(imgName, imgWidth, imgHeight, fontName);
        String imgBase64Str = null;
        if(success){
            imgBase64Str = img2base64(imgName);
        }
        return imgBase64Str;

    }

    public static boolean str2img(String imgName,int imgWidth,int imgHeight,String fontName){
        BufferedImage img = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g.setClip(0,0,imgWidth,imgHeight);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,imgWidth,imgHeight);
        g.setColor(Color.BLACK);
        g.setFont(new Font(fontName,Font.PLAIN,12));

        Rectangle clip= g.getClipBounds();
        FontMetrics fm = g.getFontMetrics();
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int size = fm.getFont().getSize();
        int y = ascent+descent+1;//12

        int x = (clip.width-fm.getMaxAdvance())/2;
        String testStr = imgName;
        char[] str = new char[testStr.length()];
        testStr.getChars(0,testStr.length()-1,str,0);
        if(str.length==1){
            g.drawChars(str,0,1,x,size*(0+1)-2);
        }else if(str.length == 2){
            for(int i = 0;i<testStr.length();i++){
//            g.setPaint(new Color(0, 0, 0, 64));//阴影颜色
//            g.drawChars(str,i,1,x,size*(i+1));//先绘制阴影
//            g.setPaint(Color.BLACK);//正文颜色
                g.drawChars(str,i,1,x,size*(i+2)-2);
            }
        }else{
            for(int i = 0;i<testStr.length();i++){
                g.drawChars(str,i,1,x,size*(i+1)-2);
            }
        }



        g.dispose();
        String fictionImgsDirectory = System.getProperty("user.dir")+File.separator+"fictionImgs"+File.separator;
        File directory = new File(fictionImgsDirectory);
        if(!directory.exists()){
            try {
                directory.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File file = new File(fictionImgsDirectory+imgName+".png");
        try {
            ImageIO.write(img,"png",file);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String img2base64(String imgName){
        BASE64Encoder encoder = new BASE64Encoder();
        String fictionImgsDirectory = System.getProperty("user.dir")+File.separator+"fictionImgs"+File.separator;
        File file = new File(fictionImgsDirectory+imgName+".png");
        InputStream is = null;
        String imgBase64Str = null;
        try {
            is = new FileInputStream(file);
            byte[] imgBytes = new byte[is.available()];
            is.read(imgBytes);
            //将突破转换为base64编码，且去掉其中的换行符，生成的字符串可以直接复制给html的img标签的src属性
            imgBase64Str = "data:image/jpeg;base64,"+encoder.encode(imgBytes);
            imgBase64Str = imgBase64Str.replaceAll("\r|\n","");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return imgBase64Str;
    }
}
