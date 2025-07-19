package org.example.schoolmanagementsystem.services.impls;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;
import org.example.schoolmanagementsystem.services.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String toEmail, String fullName, String role, String email) {
        String subject = "Mirësevini në Sistemin Shkollor";

        String message = String.format("""
                I nderuar/I nderuar %s,
                
                Ju urojmë mirëseardhje në sistemin shkollor elektronik!
                
                Si %s në këtë institucion, ju tashmë keni qasje në platformën tonë për menaxhimin e aktiviteteve shkollore, duke përfshirë oraret, notat, komunikimin dhe shumë më tepër.
                
                Email juaj eshte: %s
                Ju lutemi të verifikoni të dhënat tuaja dhe të ndryshoni fjalëkalimin sa më parë për sigurinë tuaj.
                
                Në rast se keni nevojë për ndihmë, mos hezitoni të kontaktoni administratën.
                
                Me respekt,
                Stafi i Shkollës
                """, fullName, role, email);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, false);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Dështoi dërgimi i emailit të mirëseardhjes", e);
        }
    }

    @Override
    public void sendChangePasswordNotification(UserBaseInfo user) {
        String to = user.getEmail();
        String subject = "Fjalëkalimi juaj është ndryshuar";
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        String trupi = """
                Përshëndetje %s,
                
                Fjalëkalimi i llogarisë suaj në sistemin shkollor është ndryshuar me datë %s.
                
                Nëse nuk keni qenë ju që e keni bërë këtë ndryshim, ju lutemi kontaktoni menjëherë administratorin e sistemit.
                
                Me respekt,
                Stafi i Shkollës
                """.formatted(user.getName(), dateTime);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(trupi);
        message.setFrom("noreply@shkolla.com");

        mailSender.send(message);
    }

    @Override
    public void sendGradeNotification(StudentEntity student, GradeEntity grade) {
        String to = student.getEmail();
        String subject = "Nota juaj është regjistruar në sistem";
        String message = String.format("""
                        Përshëndetje %s,
                        
                        Ju informojmë se nota juaj për lëndën '%s' është regjistruar me vlerën: %s.
                        
                        Viti akademik: %s
                        Semestri: %s
                        Data e regjistrimit: %s
                        Profesori: %s
                        
                        Nëse keni pyetje, ju lutemi kontaktoni profesorin ose administratën.
                        
                        Me respekt,
                        Stafi i Shkollës
                        """, student.getName(), grade.getSubject().getName(), grade.getGrade(),
                grade.getAcademicYear(), grade.getSemester(), grade.getDateGiven(),
                grade.getTeacher().getName() + " " + grade.getTeacher().getSurname()
        );

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("noreply@shkolla.com");

        mailSender.send(email);
    }

    @Override
    public void sendGradeUpdateNotification(StudentEntity student, GradeEntity updatedGrade) {
        String to = student.getEmail();
        String subject = "Ndryshim i notas në sistemin shkollor";

        String message = String.format("""
                         I/e nderuar %s,
                        
                         Ju njoftojmë se nota juaj për lëndën '%s' është përditësuar në sistemin shkollor.
                        
                         Nota e re: %s
                         Viti akademik: %s
                         Semestri: %s
                         Data e ndryshimit: %s
                        Profesori: %s
                        
                         Kërkojmë ndjesë për çdo keqkuptim apo gabim që ka ndodhur më parë.
                        
                         Për çfarëdo paqartësie, ju lutemi kontaktoni profesorin ose administratën.
                        
                         Me respekt,
                         Stafi i Shkollës
                        """, student.getName(), updatedGrade.getSubject().getName(),
                updatedGrade.getGrade(), updatedGrade.getAcademicYear(),
                updatedGrade.getSemester(), LocalDate.now(), updatedGrade.getTeacher().getName() + " " + updatedGrade.getTeacher().getSurname());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("noreply@shkolla.com");

        mailSender.send(email);
    }


    @Override
    public void sendPasswordChangeEmail(String toEmail, String fullName, String currentPassword) {
        String subject = "Njoftim për Ndryshim të Fjalëkalimit";
        String message = String.format("""
                Përshëndetje %s,
                
                Ky është fjalëkalimi juaj aktual: %s
                
                Ju lutem, për arsye sigurie, ndryshoni fjalëkalimin sa më shpejt që të jetë e mundur.
                
                Pasi te logirateni ne E-School, ju mund ta ndryshoni fjalëkalimin.
                
                Me respekt,
                Administrata e Shkolles
                """, fullName, currentPassword);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, false);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Dështoi dërgimi i emailit", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
