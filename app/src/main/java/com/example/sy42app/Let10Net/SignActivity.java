package com.example.sy42app.Let10Net;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sy42app.Let10Net.list.NetListActivity;
import com.example.sy42app.R;

public class SignActivity extends AppCompatActivity{
    private static final String TAG="SignActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        final EditText nameEdt=findViewById(R.id.btn_name);
        final EditText numEdt=findViewById(R.id.btn_num);

        Button cmitBtn=findViewById(R.id.btn_sign);
        cmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=nameEdt.getText().toString();
                String nameVal=nameEdt.getText().toString().trim();
                String numVal=numEdt.getText().toString().trim();
                if(TextUtils.isEmpty(nameVal)){
                    Toast.makeText(SignActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(numVal)){
                    Toast.makeText(SignActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(SignActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(SignActivity.this,NetListActivity.class);
                SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(SignActivity.this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", content);
                editor.apply();
                intent.putExtra("Sign",content);
                intent.putExtra("name",nameVal);
                intent.putExtra("num",Integer.valueOf(numVal));
                setResult(RESULT_OK,intent);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SignActivity.this);
        String username = sp.getString("username","");
        if ("".equals(username)){
            nameEdt.setHint("请输入用户名");
        }else{
            nameEdt.setText(username);
        }
    }
}
