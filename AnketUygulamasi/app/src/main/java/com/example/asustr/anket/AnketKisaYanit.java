package com.example.asustr.anket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ASUS TR on 24.03.2018.
 */

public class AnketKisaYanit extends AppCompatActivity {
    Button btnkaydet;
    EditText editTextSoru;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_kisa_yanit);

        editTextSoru=(EditText)findViewById(R.id.editTextKisaYanıtSoru);

        btnkaydet=(Button) findViewById(R.id.btnKisaYanitKaydet);
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTextSoru.setText("");
            }
        });
    }
}
