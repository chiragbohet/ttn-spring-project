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


}
