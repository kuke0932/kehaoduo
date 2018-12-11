package com.htcs.kehaoduo.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.exception.ReturnStatusException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成二维码工具类
 *
 * @author Dragon
 * @create 2017-05-18 11:44
 **/
public class GenerateQRCodeUtil {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private static final String UPLOAD = "upload";

    /**
     * 静态生成二维码 存储在磁盘上
     *
     * @param content     //二维码信息
     * @param contextPath //上下文相对路径
     * @param realPath    //磁盘真实路径
     * @param subPath     //子路径
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String generateQrcode(String content, String contextPath, String realPath, String subPath) {
        if (content == null || realPath == null) {
            return null;
        }
        String fileName = MD5Util.MD5Encode(content) + ".png";
        String url = "/" + UPLOAD + contextPath + "/" + subPath + "/" + fileName;//图片在项目中存储的相对路径
        String filePath = url;
        //如果是部署在服务器上的情况，则需要到webapps/下面的upload目录
        if (StringUtils.isNotBlank(contextPath) || realPath.endsWith("root")) {
            filePath = ".." + url;
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
            File file1 = new File(realPath, filePath); //创建存储图片的文件
            try {
                GenerateQRCodeUtil.writeToFile(bitMatrix, "png", file1); //存储二维码图片
                return filePath;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

    private static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 生成二维码图片 不存储 直接以流的形式输出到页面
     *
     * @param content
     * @param response
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void encodeQrcode(String content, HttpServletResponse response) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            //输出二维码图片流
//            response.setHeader("content-type", "image/png");
            response.setContentType("image/png");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] bs = byteArrayOutputStream.toByteArray();
            String imgBase64 = Base64.encodeBase64String(bs);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bs);
            //response.getWriter().write("data:image/png;base64," + imgBase64);
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        } catch (WriterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encodeQrcodeWithLogo(String content, InputStream logoInputStream, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(content)) {
            return;
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            // 添加logo
            image = logoMatrix(image, logoInputStream);
            //输出二维码图片流
            response.setContentType("image/png");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] bs = byteArrayOutputStream.toByteArray();
            String imgBase64 = Base64.encodeBase64String(bs);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bs);
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
            throw new ReturnStatusException(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "二维码创建失败！请稍后重试");
        }
    }

    public static BufferedImage logoMatrix(BufferedImage matrixImage, InputStream logoInputStream) throws IOException{
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(logoInputStream);

        g2.setColor(Color.white);
        //开始绘制图片
        g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);//绘制
//        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
//        g2.setStroke(stroke);// 设置笔画对象
//        //指定弧度的圆角矩形
//        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
//        g2.setColor(Color.white);
//        g2.draw(round);// 绘制圆弧矩形
//
//        //设置logo 有一道灰色边框
//        BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
//        g2.setStroke(stroke2);// 设置笔画对象
////        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
////        g2.setColor(new Color(128,128,128));
////        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        matrixImage.flush() ;
        return matrixImage ;
    }
}
