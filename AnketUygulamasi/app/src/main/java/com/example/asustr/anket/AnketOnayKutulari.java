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
 * Created by ASUS TR on 24.03.2018.
 */

public class AnketOnayKutulari extends AppCompatActivity {

    // MultiEditText referansı
    MultiEditText multiEditText;
    // Geri dönecek String veriler için liste
    List<String> veriler;

    Button btnkaydet;
    EditText editTextSoru;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_onay_kutulari);

        multiEditText = new MultiEditText(this, R.id.layoutEkle, "Seçenek");


        editTextSoru=(EditText)findViewById(R.id.editTextOnayKutulariSoru);

        btnkaydet=(Button) findViewById(R.id.btnOnayKutulariKaydet);
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                veriler = new ArrayList<String>();
                // Verilerimizi nesnemizden alıyoruz.
                veriler = multiEditText.getStringData();
                // Listemizi tarayıp bir Log basalım.
                for(int i=0; i<veriler.size(); i++){
                    Log.d("AnketOnayKutulari", (i+1) + ".Seçenek: " + veriler.get(i));
                }

                editTextSoru.setText("");
            }
        });
    }
}
