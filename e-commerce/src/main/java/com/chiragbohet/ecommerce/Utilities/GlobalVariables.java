package com.chiragbohet.ecommerce.Utilities;

import com.chiragbohet.ecommerce.Repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalVariables {

    @Autowired
    RoleRepository roleRepository;

    // Paging Sorting related

    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_OFFSET = "0";
    public static final String DEFAULT_SORT_PROPERTY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // User Account locking related
    public static Integer MAX_ATTEMPTS = 3;    // number of attempts after which a account will be locked

    public static Long MINIMUM_TIME_BETWEEN_FAILED_ATTEMPTS_SECONDS = 60 * 10L; // Time in seconds within which if 3 wrong attempts are made account is locked


    // Regular expressions

    // for conversion to java compatible regex : https://stackoverflow.com/questions/2945783/easy-way-to-convert-regex-to-a-java-compatible-regex

    // ref : http://regexlib.com/REDetails.aspx?regexp_id=1111
    public static final String REGEX_PASSWORD = "(?=^.{8,15}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";

    // ref : https://stackoverflow.com/questions/3813195/regular-expression-for-indian-mobile-numbers
    public static final String REGEX_MOBILE_NUMBER = "^[789]\\d{9}$";

    //https://stackoverflow.com/questions/44431819/regex-for-gst-identification-number-gstin
    public static final String REGEX_GST_NUMBER = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";

    //https://emailregex.com/
    public static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    //https://stackoverflow.com/questions/19715303/regex-that-accepts-only-numbers-0-9-and-no-characters
    public static final String REGEX_DIGITS = "^[0-9]+$";

    public static final String MESSAGE_PASSWORD_VALIDATION = "Please enter a valid password! 8-15 characters with atleast 1 lowercase, 1 uppercase, 1 special character, 1 number.";

    public static final String ADMIN_EMAIL_ADDRESS = "bohet.chirag@gmail.com";


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    public static String getAdminEmailAddress()
    {   // TODO : Get admin email from db
        return ADMIN_EMAIL_ADDRESS;
    }

}
