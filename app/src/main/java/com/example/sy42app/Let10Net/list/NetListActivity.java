package com.example.sy42app.Let10Net.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sy42app.Let10Net.HttpProxy;
import com.example.sy42app.Let10Net.NetInputUtils;
import com.example.sy42app.Let10Net.SetActivity;
import com.example.sy42app.Let10Net.WebActivity;
import com.example.sy42app.Let10Net.bean.VideoInfo;
import com.example.sy42app.Let10Net.bean.VideoListResponse;
import com.example.sy42app.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NetListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDT = 10;
    private static final String TAG = "NetListActivity";
    private Handler mHandler = new Handler();
    private VideoAdapter mAdapter;
    private List<VideoInfo> mDataList;
    private TextView txt_name;
    private TextView txt_sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_list);
        ListView mListView = findViewById(R.id.lv);
        View headLayout = buildListHeader();
        mListView.addHeaderView(headLayout);
        txt_name = headLayout.findViewById(R.id.txt_name);
        txt_sign = headLayout.findViewById(R.id.txt_sign);
        mDataList = new ArrayList<>();
        mAdapter = new VideoAdapter(mDataList, this);
        mListView.setAdapter(mAdapter);
        Intent intent = getIntent();
        txt_name.setText(intent.getStringExtra("Sign"));
        // mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //   @Override
        //  public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //     VideoInfo videoInfo= mDataList.get(position);
        //    Intent intent=new Intent(NetListActivity.this, WebActivity.class);
        //     intent.putExtra(WebActivity.WEB_URL,videoInfo.getFilePath());
        //     startActivity(intent);
        // }
        // });
        initData();
    }

    private View buildListHeader() {
        View headLayout = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        TextView signTV = headLayout.findViewById(R.id.txt_sign);
        signTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NetListActivity.this, "去设置签名", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NetListActivity.this, SetActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDT);
            }
        });
        return headLayout;
    }


    private void initData() {
        String raUrl = "http://ramedia.sinaapp.com/videolist.json";
        String movieUrl = "http://ramedia.sinaapp.com/res/DouBanMovie2.json";
        HttpProxy.getInstance().load(movieUrl, new HttpProxy.NetInputCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                String respJson = null;
                try {
                    respJson = NetInputUtils.readString(inputStream);
                    Log.i(TAG, "----response json:\n" + respJson);
                    VideoListResponse videoListResponse = convertJsonToBean(respJson);
                    final List<VideoInfo> list = videoListResponse.getList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private VideoListResponse convertJsonToBean(String json) {
        Gson gson = new Gson();
        VideoListResponse response = gson.fromJson(json, VideoListResponse.class);
        return response;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10: {
                if (RESULT_OK == resultCode) {
                    String set = data.getStringExtra("Set");
                    txt_sign.setText(set);
                } else {
                    Toast.makeText(this, "取消设置", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}