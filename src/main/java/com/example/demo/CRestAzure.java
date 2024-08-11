package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.*;



@RestController
public class CRestAzure {

   @Autowired
   private RestAzure operate;

   @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.url-path}")
    private String urlPath;

    @Value("${azure.storage.key}")
    private String key;

   private final static String API_VERSION = "2009-09-19";

   @GetMapping("/GetBlob")
   public ResponseEntity<byte[]> GenerateAuth() throws Exception {

      OkHttpClient client = new OkHttpClient().newBuilder().build();

      Request request = new Request.Builder()
            .url("https://" + accountName + ".blob.core.windows.net" + urlPath)
            .get()
            .addHeader("x-ms-version", API_VERSION)
            .addHeader("x-ms-date", operate.getDate())
            .addHeader("Authorization", operate.getAuth(urlPath, accountName, key))
            .build();
      Response response = client.newCall(request).execute();

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "image/png; charset=utf-8");

      return new ResponseEntity<>(response.body().bytes(), headers, HttpStatus.OK);

   }
}
