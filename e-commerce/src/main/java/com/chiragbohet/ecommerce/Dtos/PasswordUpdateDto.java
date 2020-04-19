package com.chiragbohet.ecommerce.Dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PasswordUpdateDto {

    //ref : http://regexlib.com/REDetails.aspx?regexp_id=1111
    @Pattern(regexp = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$", message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Password cannot be null!")
    private String password;

    // ref : http://regexlib.com/REDetails.aspx?regexp_id=1111
    @Pattern(regexp = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$", message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Confirm password cannot be null!")
    private String confirmPassword;

}
