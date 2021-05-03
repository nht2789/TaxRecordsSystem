
package com.trs.util;


public class TaxUtilities {
    public static boolean isStringOfNumber(String stringOfNumber){
        
        try{
            Long.valueOf(stringOfNumber);    
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}