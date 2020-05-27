package com.chiragbohet.ecommerce.utilities;

import com.chiragbohet.ecommerce.repositories.LoginAttemptRepository;
import com.chiragbohet.ecommerce.repositories.SellerRepository;
import com.chiragbohet.ecommerce.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@EnableScheduling
@EnableAsync
@Service
public class Scheduler {

    @Autowired
    LoginAttemptRepository loginAttemptRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Async
    @Transactional
    @Scheduled(fixedDelay = GlobalVariables.SCHEDULER_UNLOCKS_ACCOUNTS_AFTER_MILLISECONDS)
    public void enableLockedAccounts() {
        log.trace("Running scheduler to unlock locked accounts...");

        List<Object[]> lockedAccountEmails = loginAttemptRepository.getEmailOfAccountsLockedForSeconds(GlobalVariables.UNLOCK_ACCOUNTS_TIME_DIFFERENCE_SECONDS);  // 1 minutes

        if (!lockedAccountEmails.isEmpty()) {
            for (Object[] email : lockedAccountEmails) {
                String userEmail = email[0].toString();

                log.trace("Unlocking : " + userEmail);

                // 1) Deleting entry from LoginAttempts table
                loginAttemptRepository.deleteEntryByUserEmail(userEmail);
                // 2) Setting IS_ENABLED = true, in User table
                userRepository.enableAccountsByEmail(userEmail);
                // 3) Intimidating the user about account unlock.
                emailSenderService.sendEmail(emailSenderService.getUserAccountUnlockedIntimidationEmail(userEmail));

            }
        }


    }

//    @Scheduled(cron = "")    // every morning at 6 AM
//    public void informSellerOfLowQuantityProducts()
//    {
//        List<Seller> sellerList = sellerRepository.getAllActiveSellers();
//
//        for(Seller seller : sellerList)
//        {
//            if(seller.getProductSet().g)
//        }
//
//    }


}
