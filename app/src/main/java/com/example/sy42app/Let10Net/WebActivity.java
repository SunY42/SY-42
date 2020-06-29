package com.example.sy42app.Let10Net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.sy42app.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String WEB_URL="webUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        TextView mTitleTv =findViewById(R.id.tv_title);
        View mBackLayout=findViewById(R.id.layout_back);
        WebView webView=findViewById(R.id.btn_web);
        mTitleTv.setText("孙意42");
        mBackLayout.setOnClickListener(this);
        String webUrl=getIntent().getStringExtra(WEB_URL);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_back:{
                finish();
            }break;
            default:
                break;
        }
    }
}
