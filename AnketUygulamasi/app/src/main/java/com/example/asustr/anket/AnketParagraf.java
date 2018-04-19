package com.example.asustr.anket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ASUS TR on 16.04.2018.
 */

public class AnketParagraf extends AppCompatActivity {

    Button btnkaydet;
    EditText editTextSoru;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paragraf_layout);
        editTextSoru =(EditText) findViewById(R.id.editTextParagrafSoru);
        btnkaydet=(Button)findViewById(R.id.btnParagrafKaydet);

        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSoru.setText("");
            }
        });
    }
}
