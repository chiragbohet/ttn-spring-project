package com.chiragbohet.ecommerce.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    public static final String senderEmail = "chiragtest9654@gmail.com";

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public  void sendEmail(SimpleMailMessage email)
    {
        javaMailSender.send(email);
    }


    public SimpleMailMessage getCustomerAwaitingActivationMail(String customerEmail, String confirmationToken)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(customerEmail);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("To complete your account registration, please click here : "
                +"http://localhost:8080/register/confirm?token="+confirmationToken);

        return mailMessage;
    }

    public SimpleMailMessage getCustomerRegistrationCompletedMail(String customerEmail)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(customerEmail);
        mailMessage.setSubject("Registration Completed!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Congratulations! Your account is now verified.");

        return mailMessage;
    }

    public SimpleMailMessage getCustomerDeactivationMail(String customerEmail)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(customerEmail);
        mailMessage.setSubject("Account deactivated!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear Customer, your account has been deactivated.\nPlease contact customer care for more details.");
        return mailMessage;
    }

    public SimpleMailMessage getSellerActivationMail(String sellerEmail)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sellerEmail);
        mailMessage.setSubject("Registration Completed!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Congratulations! Your account is now verified.");

        return mailMessage;
    }

    public SimpleMailMessage getSellerDeactivationMail(String sellerEmail)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sellerEmail);
        mailMessage.setSubject("Account deactivated!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear Customer, your account has been deactivated.\nPlease contact customer care for more details.");
        return mailMessage;
    }

    public SimpleMailMessage getPasswordUpdatedMail(String email)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Updated!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear User, your password was recently changed.\nIf this was not done by you, contact customer care immediately.");
        return mailMessage;
    }

    public SimpleMailMessage getSellerAwaitingActivationMail(String email)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Awaiting approval");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear seller your account has been created and is waiting for an approval.");
        return mailMessage;
    }

    public SimpleMailMessage getUserForgotPasswordEmail(String userEmail, String confirmationToken)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Forgot password?");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear user, to reset your password use this link : "
                +"http://localhost:8080/register/forgot-password?token="+confirmationToken);

        return mailMessage;
    }

    public SimpleMailMessage getAdminNewProductAddedIntimidationEmail(String productDetails)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(GlobalVariables.getAdminEmailAddress());
        mailMessage.setSubject("New product Added!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText(productDetails);

        return mailMessage;
    }

    public SimpleMailMessage getSellerProductDeactivatedIntimidationEmail(String sellerEmail, String productDetails)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sellerEmail);
        mailMessage.setSubject("Product Deactivated!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("The following product has been deactivated by the admin. Contact us for more info...\n"+productDetails);

        return mailMessage;
    }

    public SimpleMailMessage getSellerProductActivatedIntimidationEmail(String sellerEmail, String productDetails) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sellerEmail);
        mailMessage.setSubject("Product Activated!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("The following product has been Activated by the admin.\n" + productDetails);

        return mailMessage;
    }

    public SimpleMailMessage getUserAccountLockedIntimidationEmail(String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Account Locked!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear User,\nYour account has been locked due to 3 unsuccessful login attempts. It will be unlocked after sometime and you will be intimidated about it via email.");

        return mailMessage;
    }


    public SimpleMailMessage getUserAccountUnlockedIntimidationEmail(String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Account Unlocked!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Dear User,\nYour account has been unlocked. You can start using your account now.");

        return mailMessage;
    }


}
