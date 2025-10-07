package pupr.capstone.myapplication;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailUtil {

    public static void sendMail(String recipient) throws Exception {
        System.out.println("Preparing to send email...");
        Properties properties = new Properties();

        // Configuración SMTP para Gmail
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "alejandro.texidor02@gmail.com";
        String password = "ehbl idmo miam fvsd"; // App password de Gmail

        // Sesión con autenticación
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        // Preparar y enviar mensaje
        Message message = prepareMessage(session, myAccountEmail, recipient);
        Transport.send(message);
        System.out.println("✅ Correo enviado exitosamente!");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipientList) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));

            // 🔹 Acepta múltiples correos separados por comas
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientList));

            message.setSubject("Recordatorio de mantenimiento");
            message.setText("Saludos,\nEl vehículo necesita mantenimiento. ¡No olvides revisarlo!");

            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

