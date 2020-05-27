package com.chiragbohet.ecommerce.security;

import com.chiragbohet.ecommerce.entities.User;
import com.chiragbohet.ecommerce.repositories.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails requestedUser = userDao.loadUserByEmail(email);

        if (requestedUser != null) {

            User user = userRepository.findByEmail(email);

            if (!user.isActive())
                throw new RuntimeException("The account is not active, please get it activated first.");

            if (user.isDeleted())
                throw new RuntimeException("The account is Deleted.");

            return requestedUser;

        } else {
            throw new UsernameNotFoundException("No user found with email : " + email);
        }






    }
}