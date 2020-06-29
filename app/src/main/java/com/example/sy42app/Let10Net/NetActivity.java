package com.example.sy42app.Let10Net;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.sy42app.Let10Net.bean.VideoListResponse;
import com.example.sy42app.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetActivity extends AppCompatActivity {
    private static final String TAG=NetActivity.class.getSimpleName();

    private ImageView mLogoIV;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        mLogoIV=findViewById(R.id.img_icon);

    }
    public void toJsonClick(View view) {
        String json="{\n"+
                "\"result\":\"0\",\n"+
                "\"list\": [\n"+
        "{\n"+
        "\"title\":\"Big Wedding Day\",\n"+
                "\"filePath\":\"http://ramedia.sinaapp.com/res/Video/BigWeddingDay.hlv\",\n"+
                "\"thumbPath\":\"http://ramedia.sinaapp.com/res/Video/BigWeddingDay.png\",\n"+
                "\"id\":2\n"+
        "}\n"+
        "]\n"+
        "}";
        VideoListResponse videoListResponse= convertJsonToBean(json);
    }
    private VideoListResponse convertJsonToBean(String json){
        Gson gson=new Gson();
        VideoListResponse response=gson.fromJson(json,VideoListResponse.class);
        return response;
    }


    public void toRequestNet(View view) {
        new Thread(new Runnable() {
            public void run() {
                String raUrl = "http://ramedia.sinaapp.com/videolist.json";
                String imgUrl="http://ramedia.sinaapp.com/res/Video/GetColdFeet.png";
                String movieUrl="http://ramedia.sinaapp.com/res/DouBanMovie.json";
                try {
                    URL url = new URL(movieUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    Log.i(TAG, "----responseCode:" + responseCode);
                    if (200 == responseCode) {
                        InputStream inputStream = urlConnection.getInputStream();
                        final Bitmap bitmap=NetInputUtils.readImg(inputStream);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLogoIV.setImageBitmap(bitmap);
                            }
                        });
                        inputStream.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

