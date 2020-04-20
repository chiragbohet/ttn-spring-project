package com.chiragbohet.ecommerce.Utilities;
// ref : https://www.baeldung.com/registration-verify-user-by-email

import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public static final Long TOKEN_EXPIRATION_IN_SECONDS = (24*60*60L); // one day validity

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;

    public ConfirmationToken(User user){
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }


    public boolean isExpired()
    {
        if( ( (new Date().getTime() - this.createdDate.getTime() ) / 1000 ) <  TOKEN_EXPIRATION_IN_SECONDS )
            return false;
        else
            return true;
    }


}
