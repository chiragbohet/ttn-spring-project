package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Repositories.UserRepository;
import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public AppUser loadUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        System.out.println(user);

        if (user != null) {
            return new AppUser(user.getEmail(), user.getPassword(), user.getRoleList(), user.isAccountNonExpired(), user.isAccountNonLocked(), user.isCredentialsNonExpired(), user.isEnabled());
        } else {
            throw new RuntimeException();
        }

    }
}
