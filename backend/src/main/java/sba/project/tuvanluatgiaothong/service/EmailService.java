package sba.project.tuvanluatgiaothong.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Your OTP Verification Code");
            helper.setText(createOtpEmailContent(otp), true);
            helper.setFrom("noreply@yourapp.com");

            mailSender.send(message);
            logger.info("OTP email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendOtpEmailAsync(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Your OTP Verification Code");
            helper.setText(createOtpEmailContent(otp), true);
            helper.setFrom("noreply@yourapp.com");

            mailSender.send(message);
            logger.info("OTP email sent successfully to: {}", toEmail);
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to: {}", toEmail, e);
            return CompletableFuture.completedFuture(false);
        }
    }

    private String createOtpEmailContent(String otp) {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <h2 style="color: #333; text-align: center;">Xác thực email của bạn</h2>
                        <p style="color: #666; font-size: 16px;">Xin chào, </p>
                        <p style="color: #666; font-size: 16px;">Mã OTP để xác thực email của bạn là:</p>
                        <div style="text-align: center; margin: 30px 0;">
                            <span style="background-color: #007bff; color: white; padding: 15px 30px; font-size: 24px; font-weight: bold; border-radius: 5px; letter-spacing: 3px;">%s</span>
                        </div>
                        <p style="color: #666; font-size: 14px;">Mã xác thực sẽ hết hạn trong vòng 5 phút.</p>
                        <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                        <p style="color: #999; font-size: 12px; text-align: center;">Đây là tin nhắn tự động, không phản hồi lại.</p>
                    </div>
                </body>
            </html>
        """.formatted(otp);
    }
}
