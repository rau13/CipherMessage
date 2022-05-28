package com.rau.CipherMessage;

import java.security.MessageDigest;

public class CryptMessage {

    public String SHA(String text) throws Exception{
        //Creating the MessageDigest object
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //Passing data to the created MessageDigest Object
        md.update(text.getBytes());

        //Compute the message digest
        byte[] digest = md.digest();


        //Converting the byte array in to HexString format
        StringBuffer hexString = new StringBuffer();

        for (int i = 0;i<digest.length;i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        String res = hexString.toString();
        return res;
    }
    /*public String RC4(String text) throws Exception{
        //Creating the MessageDigest object
        MessageDigest md = MessageDigest.getInstance("RC4");

        //Passing data to the created MessageDigest Object
        md.update(text.getBytes());

        //Compute the message digest
        byte[] digest = md.digest();


        //Converting the byte array in to HexString format
        StringBuffer hexString = new StringBuffer();

        for (int i = 0;i<digest.length;i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        String res = hexString.toString();
        return res;
    }*/
}
