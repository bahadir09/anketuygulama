package com.example.asustr.anket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by ASUS TR on 24.03.2018.
 */

public class AnketOlustur extends AppCompatActivity {

    Spinner spinner;
    ListView listViewGoruntuleme;
    Button btnYayinla;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_olustur);

        final Button btnEkle = (Button) findViewById(R.id.btnEkle);
        btnYayinla = (Button) findViewById(R.id.btnYayinla);
        listViewGoruntuleme=(ListView) findViewById(R.id.listviewCoktanSecmeli);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(AnketOlustur.this, R.array.Soru_Tipi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                String[] diziSorutipi = getResources().getStringArray(R.array.Soru_Tipi);

                btnEkle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (i == 1) {
                            Intent ıntent = new Intent(AnketOlustur.this, AnketCoktanSecmeli.class);
                            startActivity(ıntent);
                        }
                        else if(i==2){
                            Intent ıntent = new Intent(AnketOlustur.this, AnketOnayKutulari.class);
                            startActivity(ıntent);
                        }
                        else if(i==3) {
                            Intent ıntent = new Intent(AnketOlustur.this, AnketKisaYanit.class);
                            startActivity(ıntent);
                        }
                        else if(i==4){
                            Intent ıntent = new Intent(AnketOlustur.this, AnketParagraf.class);
                            startActivity(ıntent);
                        }
                        else if(i==5){
                            Intent ıntent = new Intent(AnketOlustur.this, AnketResimliSoru.class);
                            startActivity(ıntent);
                        }

                    }
                });


                DataStore.SoruTipi = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnYayinla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //Geri Tuşu

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //geri tuşuna basılma durumunu yakalıyoruz
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            AlertDialog.Builder alert = new AlertDialog.Builder(AnketOlustur.this);
            alert.setTitle("Uyarı!");
            alert.setMessage("Çıkmak için anketi yayınlamalısınız. Anketi yayınlamak için soru ekleyiniz.");
            alert.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    //evet seçildiginde yapılacak işlemler
                    finish();
                    //Intent intent = new Intent(AnketOlustur.this, AnaFragment.class);
                    //startActivity(intent);
                }
            });

            alert.show();

        }

        return super.onKeyDown(keyCode, event);
    }

}
