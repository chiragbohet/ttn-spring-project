package com.chiragbohet.ecommerce.Dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserRegistrationDto {

    // ref - https://emailregex.com/
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Please enter a valid email address!")
    @NotNull(message = "Email cannot be null!")
    private String email;

    @NotNull(message = "First name cannot be null!")
    private String firstName;

    private String middleName;

    @NotNull(message = "Last name cannot be null!")
    private String lastName;

    // ref : http://regexlib.com/REDetails.aspx?regexp_id=1111
    @Pattern(regexp = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$", message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Password cannot be null!")
    private String password;

    // ref : http://regexlib.com/REDetails.aspx?regexp_id=1111
    @Pattern(regexp = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$", message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Confirm password cannot be null!")
    private String confirmPassword;

}
