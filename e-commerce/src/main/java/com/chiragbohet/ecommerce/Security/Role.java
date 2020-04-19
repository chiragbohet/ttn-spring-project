package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "AUTHORITY")
    String authority;

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @ManyToMany(mappedBy = "roleList")
    List<User> userList;

    public void addUsers(User... users)
    {
        if(users != null)
        {
            if(userList == null)
                userList = new ArrayList<>();

            for(User user : users)
            {
                if(!userList.contains(user))
                {
                    userList.add(user);
                    user.addRoles(this);

                }

            }
        }
    }
}