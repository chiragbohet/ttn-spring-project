package com.chiragbohet.ecommerce.entities;


import com.chiragbohet.ecommerce.security.Role;
import com.chiragbohet.ecommerce.utilities.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER")
public abstract class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;  // for a soft delete

    @Column(name = "IS_ACTIVE") // also IS_ACTIVE
    private boolean isActive;

    // Spring Security fields

    @Column(name = "IS_ACCOUNT_NOT_EXPIRED")
    private boolean isAccountNonExpired;

    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private boolean isAccountNonLocked;

    @Column(name = "IS_CREDENTIALS_NON_EXPIRED")
    private boolean isCredentialsNonExpired;

    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<Role> roleList;

    public void addRoles(Role... roleArray) {
        if(roleArray != null) {
            if(roleList == null)
                roleList = new ArrayList<>();

            for(Role role : roleArray) {
                if(!roleList.contains(role)) {
                    roleList.add(role);
                    role.addUsers(this);

                }
            }
        }
    }


}
