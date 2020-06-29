package com.example.sy42app.Let10Net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sy42app.R;

public class SetActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDT = 10;
    private static final String TAG = "SetActivity";
    private Button btn_set;
    private EditText txt_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        txt_set=(EditText)findViewById(R.id.txt_set);
        btn_set =(Button)findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sun=txt_set.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("Set",Sun);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
