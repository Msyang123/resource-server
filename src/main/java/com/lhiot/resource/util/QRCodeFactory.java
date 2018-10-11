package com.lhiot.resource.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.leon.microx.util.Maps;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

/**
 * 本类用于对我们二维码进行参数的设定,生成我们的二维码：
 *
 * @author kingwen
 */
public class QRCodeFactory {

    /**
     * 给生成的二维码添加中间的logo
     *
     * @param matrixImage 生成的二维码
     * @param logoUri     logo地址
     * @return 带有logo的二维码
     * @throws IOException logo地址找不到会有io异常
     */
    BufferedImage setMatrixLogo(BufferedImage matrixImage, String logoUri) throws IOException {
//        读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();
        float matrixWidth = matrixImage.getWidth();
        float matrixHeigh = matrixImage.getHeight();

//        读取Logo图片
        if (StringUtils.isNotBlank(logoUri)) {
            BufferedImage logo = ImageIO.read(new URL(logoUri));//.read(new File(logoUri));

            //开始绘制图片
            g2.drawImage(logo, (int) matrixWidth / 5 * 2, (int) matrixHeigh / 5 * 2, (int) matrixWidth / 5, (int) matrixHeigh / 5, null);

            //指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
            g2.setColor(Color.white);
            // 绘制圆弧矩形
            g2.draw(round);

            //设置logo 有一道灰色边框
            BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            // 设置笔画对象
            g2.setStroke(stroke2);

            RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
            g2.setColor(new Color(128, 128, 128));
            g2.draw(round2);// 绘制圆弧矩形

        }
        //绘制边框
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke);

        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }


    /**
     * 创建二维码图片
     *
     * @param content      二维码内容
     * @param format       生成二维码的格式
     * @param outputStream 二维码的生成输出流
     * @param logoUri      二维码中间logo的地址
     * @param size         用于设定图片大小（可变参数，宽，高）
     * @throws IOException     抛出io异常
     * @throws WriterException 抛出书写异常
     */
    public void create(String content, String format, OutputStream outputStream, String logoUri, int... size) throws IOException, WriterException {
        int width = 430; // 二维码图片宽度 430
        int height = 430; // 二维码图片高度430

        //如果存储大小的不为空，那么对我们图片的大小进行设定
        if (size.length == 2) {
            width = size[0];
            height = size[1];
        } else if (size.length == 1) {
            width = height = size[0];
        }

        Map<EncodeHintType, Object> hints = Maps.of(
                EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H, // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
                EncodeHintType.CHARACTER_SET, "utf-8", // 内容所使用字符集编码
                EncodeHintType.MARGIN, 1 //设置二维码边的空度，非负数
        );
        /*
         * hints ：生成条形码时的一些配置,此项可选
         * BarcodeFormat.QR_CODE ：
         * 编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
         * Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
         * MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
         */
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputStream, logoUri);
    }

}