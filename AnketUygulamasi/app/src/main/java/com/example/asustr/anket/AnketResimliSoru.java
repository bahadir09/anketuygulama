package com.example.asustr.anket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ASUS TR on 16.04.2018.
 */

public class AnketResimliSoru extends AppCompatActivity {

    // MultiEditText referansı
    MultiEditText multiEditText;
    // Geri dönecek String veriler için liste
    List<String> veriler;


    private ImageView imageView;
    private Button buttonGaleri, btnKaydet;
    EditText editTextResimliSoru;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_resimli_soru);

        multiEditText = new MultiEditText(this, R.id.layoutResimliEkle, "Seçenek");

        imageView = (ImageView) findViewById(R.id.imageViewResim);
        editTextResimliSoru = (EditText) findViewById(R.id.editTextResimliSoru);
        btnKaydet = (Button) findViewById(R.id.btnResimliKaydet);


        buttonGaleri = (Button) findViewById(R.id.btngaleriden);

        buttonGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeri_int = new Intent();
                galeri_int.setType("image/*");
                galeri_int.setAction(Intent.ACTION_GET_CONTENT);
                galeri_int.addCategory(Intent.CATEGORY_OPENABLE);

                startActivityForResult(galeri_int, 44);
            }
        });

    }

    //Galeriden resim seçme ve gösterme
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap=null;

        if(bitmap!=null){
            bitmap.recycle();
        }
        InputStream stream;

        try {
            stream = getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
