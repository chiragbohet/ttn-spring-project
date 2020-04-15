package com.chiragbohet.ecommerce.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails requestedUser = userDao.loadUserByEmail(email);

        if(requestedUser != null)
        {
            System.out.println("Trying to authenticate user with email ::" + requestedUser.getUsername());
            System.out.println("Encrypted Password ::"+requestedUser.getPassword());
            return requestedUser;
        }
        else
            {
                throw new UsernameNotFoundException("No user found with email : " + email);
            }






    }
}