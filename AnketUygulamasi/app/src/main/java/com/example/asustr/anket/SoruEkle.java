package com.example.asustr.anket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SoruEkle extends AppCompatActivity {

    LinearLayout linearLayout;
    int i=0;
    EditText editTextSoru,editCheckBir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_ekle);

        Button btnSoruEkle=(Button)findViewById(R.id.btnSoruEkle);

        switch (DataStore.SoruTipi){
            case 0:
                linearLayout=(LinearLayout)findViewById(R.id.linearLayoutCoktanSecmeli);
                break;
            case 1:
                linearLayout=(LinearLayout)findViewById(R.id.linearLayoutCoktanSecmeli);
                break;
            case 2:

                break;
            case 3:

                break;
        }
        linearLayout.setVisibility(View.VISIBLE);
        editTextSoru=(EditText)findViewById(R.id.editTextSoru);
        editCheckBir=(EditText)findViewById(R.id.editCheckBir);

        btnSoruEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(10==i){
                    Toast.makeText(getApplicationContext(),"Max. Soru Sayısı",Toast.LENGTH_SHORT).show();
                }else {
                    String soru=editTextSoru.getText().toString();
                    String soru1=editCheckBir.getText().toString();
                    if((!soru.isEmpty()||soru!=null)
                            ||(!soru1.isEmpty()||soru1!=null)){
                        //database kaydettin

                        editTextSoru.setText("");
                        editCheckBir.setText("");
                        i++;
                    }
                }
            }
        });
    }
}
