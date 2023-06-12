package com.kratin.service;

import java.time.LocalTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kratin.entity.Treatment;
import com.kratin.repository.TreatmentRepo;

@Service
@Transactional
public class TreatmentService {
	@Autowired
    public TreatmentRepo treatmentRepo;
	@Autowired
    public JavaMailSender mailSender;

    @Autowired
    public TreatmentService(TreatmentRepo treatmentRepo, JavaMailSender mailSender) {
        this.treatmentRepo = treatmentRepo;
        this.mailSender = mailSender;
    }

    public void sendReminders() {
        List<Treatment> treatments = treatmentRepo.findAll();
        for (Treatment treatment : treatments) {
            if (isTimeToTakeTreatment(treatment.getTime())) {
                sendReminder(treatment);
            }
        }
    }

    public boolean isTimeToTakeTreatment(String treatmentTime) {
        LocalTime currentTime = LocalTime.now();
        LocalTime treatmentTimeValue = LocalTime.parse(treatmentTime);
        return currentTime.getHour() == treatmentTimeValue.getHour() &&
                currentTime.getMinute() == treatmentTimeValue.getMinute();
    }

    public void sendReminder(Treatment treatment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("sunita@example.com"); // Replace with Sunita's email address
            helper.setSubject("Treatment Reminder");
            helper.setText("Dear Sunita,\n\nIt's time to take your treatment - " + treatment.getName() + ".\n\nPlease remember to take it as prescribed.\n\nBest regards,\nThe Treatment Reminder App");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
