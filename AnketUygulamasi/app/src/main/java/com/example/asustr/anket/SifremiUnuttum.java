package com.example.asustr.anket;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by ASUS TR on 11.03.2018.
 */

public class SifremiUnuttum extends AppCompatActivity {

    Boolean sonuc;
    EditText sifremiUnuttum;
    Button sifregonder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        sifremiUnuttum=(EditText) findViewById(R.id.editTextSifremiUnuttumMail);
        sifregonder = (Button) findViewById(R.id.buttonSifremiUnuttumGonder);

        final String sifreyenile = sifregonder.getText().toString();

        sifregonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sifreyenile.equals("")){
                    Toast.makeText(getApplicationContext(), "Lütfen mail adresi giriniz!.", Toast.LENGTH_LONG).show();
                }
                else{
                    new WebServisAnketSifremiUnuttum().execute();
                }
            }
        });


    }

    class WebServisAnketSifremiUnuttum extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=SifremiUnuttum";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/SifremiUnuttum";
        private static final String METHOD_NAME = "SifremiUnuttum";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("Email", sifremiUnuttum.getText().toString());
   /*Tırnak içerisinde yeşil ile yazılmış olan KullanıcıAdı, Sifre web servisteki ilgili  metot ile aynı olmak zorundadır.*/
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;/*envelope.dotNet = true; alanı web servisimiz Microsoft tabanlı olduğunu göstermektedir.*/
            envelope.setOutputSoapObject(spObjectGonder);
            try {
                HttpTransportSE transport2 = new HttpTransportSE(URL);
                transport2.debug = true;
                transport2.call(SOAP_ACTION, envelope);
      /*spObjectGonder Soap paketini HttpTransportSE üzerinden url oluşturarak gönderdik.*/
                SoapPrimitive spPrimitiveGelen = (SoapPrimitive) envelope.getResponse();
      /*Gelen soap paketini alıyoruz. “(SoapPrimitive)” demek tek bir parametre geri dönüş aldığımız için kullandık. Bize servisimiz 0-1 değerlerinden birisi döndürmektedir.*/
                sonuc = Boolean.parseBoolean(spPrimitiveGelen.toString());
      /*Integer Sonuc; oncreate üzerinde global olarak tanımlanmalıdır.*/
      /*Gelen değer integere parse ederek aldık. Sonuc değişkenine atadık.*/
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
   /*Tüm işlemler bittikten sonra çalışacak kod bloğu.*/

   /*ProgressDialog işlemini sonlandırıyoruz.*/
            if (!sonuc) {

                Toast.makeText(getApplicationContext(), "Şifre yenileme işlemi başarısız! Lütfen tekrar deneyiniz.", Toast.LENGTH_LONG).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(getApplicationContext(), "Şifre yenileme işlemi başarılı. yeni şifre gönderildi.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SifremiUnuttum.this, AnketGiris.class));



            }
        }
    }
}
