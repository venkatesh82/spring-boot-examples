package com.oss.pii.demo.utils;

import org.apache.commons.lang3.StringUtils;

public class MaskUtils {

    public static String maskString(String strText, int start, int end, char maskChar)
            throws Exception{

        if(strText == null || strText.equals(""))
            return "";

        if(start < 0)
            start = 0;

        if( end > strText.length() )
            end = strText.length();

        if(start > end)
            return strText;

        int maskLength = end - start;

        if(maskLength == 0)
            return strText;

        String strMaskString = StringUtils.repeat(maskChar, maskLength);

        return StringUtils.overlay(strText, strMaskString, start, end);
    }

    public static String mask(String strText,int keepFirstDigits, int keepLastDigits,char maskChar) throws Exception {
        if(strText == null || strText.equals(""))
            return "";

        return maskString(strText,keepFirstDigits,strText.length()-keepLastDigits,maskChar);
    }
}
