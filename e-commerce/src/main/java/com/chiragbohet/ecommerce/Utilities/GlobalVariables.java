package com.chiragbohet.ecommerce.Utilities;

public class GlobalVariables {


    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_OFFSET = "0";
    public static final String DEFAULT_SORT_PROPERTY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";


    // Regular expressions

    // for conversion to java compatible regex : https://stackoverflow.com/questions/2945783/easy-way-to-convert-regex-to-a-java-compatible-regex

    // ref : http://regexlib.com/REDetails.aspx?regexp_id=1111
    public static final String REGEX_PASSWORD = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";

    // ref : https://stackoverflow.com/questions/3813195/regular-expression-for-indian-mobile-numbers
    public static final String REGEX_MOBILE_NUMBER = "^[789]\\d{9}$";

    //https://stackoverflow.com/questions/44431819/regex-for-gst-identification-number-gstin
    public static final String REGEX_GST_NUMBER = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";

    //https://emailregex.com/
    public static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static final String MESSAGE_PASSWORD_VALIDATION = "Please enter a valid password! 8-15 characters with atleast 1 lowercase, 1 uppercase, 1 special character, 1 number.";

}
