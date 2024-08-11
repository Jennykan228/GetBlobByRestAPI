package com.example.demo;

import java.util.*;
import java.text.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.binary.Base64;

import org.springframework.stereotype.Service;

@Service
public class RestAzure {

    private static Base64 base64 = new Base64();
    private final static String API_VERSION = "2009-09-19";
    
    public  String getAuth(String urlPath, String account, String key) throws Exception
    {

        StringBuilder sb = new StringBuilder();
        sb.append("GET\n"); // method
        sb.append('\n'); // content encoding
        sb.append('\n'); // content language
        sb.append('\n'); // content length
        sb.append('\n'); // md5 (optional)
        sb.append('\n'); // content type
        sb.append('\n'); // legacy date
        sb.append('\n'); // if-modified-since
        sb.append('\n'); // if-match
        sb.append('\n'); // if-none-match
        sb.append('\n'); // if-unmodified-since
        sb.append('\n'); // range
        sb.append("x-ms-date:" + getDate() + '\n'); // headers
        sb.append("x-ms-version:" + API_VERSION + '\n');
        sb.append("/" + account + urlPath);
     

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(base64.decode(key), "HmacSHA256"));
        String authKey = new String(base64.encode(mac.doFinal(sb.toString().getBytes("UTF-8"))));
        String auth = "SharedKey " + account + ":" + authKey;
       
        return auth;
    
}

    public String getDate() {

        SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = fmt.format(Calendar.getInstance().getTime()) + " GMT";

        return date;    

    }
}