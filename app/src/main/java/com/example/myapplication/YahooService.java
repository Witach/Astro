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
    private Request request;
    private String consumerKey;
    private String consumerSecret;
    private String url;
    private String appId;

    private String oauthNonce;
    private long timestamp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    YahooService(String consumerKey, String consumerSecret, String url, String appId) {

        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.url = url;
        this.appId = appId;
        setOauthNonce();
        setTimestamp();
    }

    private String getAuthorizationLine(String signature) {
        return  "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\"";
    }

    private void setOauthNonce() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < 32 ; i++)
            stringBuffer.append(random.nextInt(100));

        oauthNonce = stringBuffer.toString().replaceAll("\\W", "");
    }

    private void setTimestamp() {
        timestamp = new Date().getTime() / 1000;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getSignature(String city) throws UnsupportedEncodingException {
        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        // Make sure value is encoded
        parameters.add("location=" + URLEncoder.encode(city, "UTF-8"));
        parameters.add("format=json");
        Collections.sort(parameters);

        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }

        String signatureString = "GET&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(parametersList.toString(), "UTF-8");

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Request getRequest(String city) {
        try {
            String signature = getSignature(city);
            String authorizationLine =  getAuthorizationLine(signature);
            return request = new Request.Builder()
                    .url(url + "?location="+city+ "&format=json")
                    .header("Authorization", authorizationLine)
                    .header("X-Yahoo-App-Id", appId)
                    .header("Content-Type", "application/json")
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return request;
        }
    }
}
