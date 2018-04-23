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
 * Created by ASUS TR on 10.03.2018.
 */

public class KayitOl extends AppCompatActivity {

    EditText kulAd, sifre, ad, soyad, mail, telefon, yas;
    Button btnkayitOl;
    Boolean sonuc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        kulAd = (EditText) findViewById(R.id.editTextKayitOlKulAdi);
        sifre = (EditText) findViewById(R.id.editTextKayitOlSifre);
        ad = (EditText) findViewById(R.id.editTextKayitOlAd);
        soyad = (EditText) findViewById(R.id.editTextKayitOlSoyad);
        mail = (EditText) findViewById(R.id.editTextKayitOlMail);
        telefon = (EditText) findViewById(R.id.editTextKayitOlTelefon);
        yas = (EditText) findViewById(R.id.editTextKayitOlYas);

        btnkayitOl = (Button) findViewById(R.id.buttonKayitOl);

        btnkayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String KulAd = kulAd.getText().toString();
                String Sifre = sifre.getText().toString();
                String Ad = ad.getText().toString();
                String Soyad = soyad.getText().toString();
                String Mail = mail.getText().toString();
                String Telefon = telefon.getText().toString();
                String Yas = yas.getText().toString();

                if(KulAd.equals("") || Sifre.equals("") || Ad.equals("") || Soyad.equals("") || Mail.equals("") || Telefon.equals("") || Yas.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Alanlar Boş Bırakılamaz!", Toast.LENGTH_LONG).show();
                }else {
                    new WebServisKullaniciAdiKontrol().execute();
                }
            }
        });
    }


    class WebServisKullaniciAdiKontrol extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketKullaniciAdiKontrol";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketKullaniciAdiKontrol";
        private static final String METHOD_NAME = "anketKullaniciAdiKontrol";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("KullaniciAdi", kulAd.getText().toString());
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

                //Toast.makeText(getApplicationContext(), "Lütfen Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();
                new WebServisAnketKullaniciKayit().execute();
                //mProgress.dismiss();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(getApplicationContext(), "Seçtiğiniz Kullanıcı Adı Kullanılmaktadır! Lütfen Başka bir Kullanıcı Adı Giriniz.", Toast.LENGTH_SHORT).show();
                kulAd.setText("");
            }
        }
    }


    class WebServisAnketKullaniciKayit extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketKullaniciKayit";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketKullaniciKayit";
        private static final String METHOD_NAME = "anketKullaniciKayit";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("kullaniciAdi", kulAd.getText().toString());
            spObjectGonder.addProperty("sifre", sifre.getText().toString());
            spObjectGonder.addProperty("ad", ad.getText().toString());
            spObjectGonder.addProperty("soyad", soyad.getText().toString());
            spObjectGonder.addProperty("mail", mail.getText().toString());
            spObjectGonder.addProperty("telefon", telefon.getText().toString());
            spObjectGonder.addProperty("yas", yas.getText().toString());
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

                Toast.makeText(getApplicationContext(), "Kayıt Başarısız!.", Toast.LENGTH_LONG).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(getApplicationContext(), "Kayıt Olma Başarılı..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(KayitOl.this, AnketGiris.class));



            }
        }
    }

}
