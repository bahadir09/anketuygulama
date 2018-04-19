package com.example.asustr.anket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS TR on 19.03.2018.
 */

public class AnketCoktanSecmeli extends AppCompatActivity {

    Button btnkaydet;
    EditText editTextSoru;


    // MultiEditText referansı
    MultiEditText multiEditText;
    // Geri dönecek String veriler için liste
    List<String> veriler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_ol_coktan_secmeli);

        multiEditText = new MultiEditText(this, R.id.layoutCoktanSecmeliEkle, "Seçenek");

        editTextSoru=(EditText)findViewById(R.id.editTextCoktanSoru);

        btnkaydet=(Button) findViewById(R.id.btnCoktanKaydet);
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                veriler = new ArrayList<String>();
                // Verilerimizi nesnemizden alıyoruz.
                veriler = multiEditText.getStringData();
                // Listemizi tarayıp bir Log basalım.
                for(int i=0; i<veriler.size(); i++){
                    Log.d("AnketCoktanSecmeli", (i+1) + ".Seçenek: " + veriler.get(i));
                }

                editTextSoru.setText("");

            }
        });
    }
}
