package com.keli.worktalk.Model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sign {
    public static String signBuider(String sign){
        String ans;
        byte[] digest = new byte[0];
        try {
            digest = MessageDigest.getInstance("md5").digest(sign.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ans = new BigInteger(1, digest).toString(16);
        String begin = "";
        for(int i=0;i<32-ans.length();i++){
            begin+="0";
        }
        ans = begin+ans;
        return ans;
    }
}
