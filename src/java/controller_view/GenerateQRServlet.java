package controller_view;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
// Servlet tạo QR code động từ nội dung được truyền vào
// Sử dụng thư viện ZXing để generate QR code

@WebServlet(name = "GenerateQRServlet", urlPatterns = {"/GenerateQRServlet"})

public class GenerateQRServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy nội dung QR từ URL parameter
        String qrContent = request.getParameter("content");

        if (qrContent == null || qrContent.isEmpty()) {
            // Không có content → trả về lỗi 400
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing QR content");
            return;
        }

        try {
            // Kích thước ảnh QR
            int width = 300;
            int height = 300;

            // Tạo QR code bằng thư viện ZXing
            // Encode nội dung thành BitMatrix (ma trận bit đen/trắng)
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);

            // Chuyển BitMatrix thành BufferedImage (ảnh Java)
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Trả về ảnh PNG qua HTTP response
            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            ImageIO.write(qrImage, "PNG", out);
            out.close();

        } catch (WriterException ex) {
            // Lỗi khi generate QR → trả về lỗi 500
            Logger.getLogger(GenerateQRServlet.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating QR code");

        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
