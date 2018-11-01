package com.tzj.http.http;

import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.request.IRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * 举个例子而已
 * 没写完
 */
@Deprecated
public class AndroidHttp implements IHttp{

    @Override
    public void post(IRequest request, IHttpCallBack callBack) {
        try {
            URL url = new URL(request.url());
            URLConnection temp = url.openConnection();
            temp.setDoInput(true);
            temp.setDoOutput(true);
            temp.connect();
            if (temp instanceof HttpURLConnection){
                HttpURLConnection con = (HttpURLConnection) temp;
                int responseCode = con.getResponseCode();
            }else if (temp instanceof HttpsURLConnection){
                HttpsURLConnection con = (HttpsURLConnection) temp;
                int responseCode = con.getResponseCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
