package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YahooService {
    private static YahooService yahooService;
    final String appId = "DNbJMD4q";
    final String consumerKey = "dj0yJmk9QjduZk1TRVFMN250JmQ9WVdrOVJFNWlTazFFTkhFbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTdh";
    final String consumerSecret = "48746667d1352b63b406a30f16edead0c0d47097";
    final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";
    private String nonce;
    private long timestamp;
    OkHttpClient client;

    public YahooService() {
        client = new OkHttpClient.Builder()
                .build();
    }

    public static YahooService getInstance() {
        if (yahooService == null)
            yahooService = new YahooService();
        return yahooService;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void doSth() {
        try {
            Call call = client.newCall(requestInstance(url, null));
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Request requestInstance(String url, List<String> parameters){
        nonce = getAnNonce();
        timestamp = getAnTimeStamp();
        String authorizationLine = "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\", " +
                "oauth_nonce=\"" + nonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + getOAuthSignature() + "\", " +
                "oauth_version=\"1.0\"";
        return new Request.Builder()
                .url(url)
                .addHeader("Authorization",authorizationLine)
                .addHeader("X-Yahoo-App-Id", appId)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    private long getAnTimeStamp() {
        return new Date().getTime() / 1000;
    }

    private String getAnNonce() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0 ; i<32; i++)
            stringBuffer.append(random.nextInt(100));
//        byte[] nonce = new byte[32];
//        Random rand = new Random();
//        rand.nextBytes(nonce);
        return stringBuffer.toString().replaceAll("\\W", "");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getOAuthSignature() {
        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + nonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        // Make sure value is encoded
        try {
            parameters.add("location=" + URLEncoder.encode("sunnyvale,ca", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }

        parameters.add("format=json");
        Collections.sort(parameters);
        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }
        String signatureString = "";
        try{
            signatureString = "GET&" +
                    URLEncoder.encode(url, "UTF-8") + "&" +
                    URLEncoder.encode(parametersList.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            System.exit(0);
        }

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            System.err.println("Unable to append signature");
            System.exit(0);
        }
        return signature;
    }


}
