package com.example.asustr.anket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by ASUS TR on 28.02.2018.
 */

public class AnketGiris extends AppCompatActivity {


    //ProgressBar progressBar;
    private ProgressDialog mProgress;

    public EditText sifre, kulAdi;
    CheckBox chkBeniHatirla;
    Button btn_giris;
    TextView kayitOL, sifreUnuttum;
    ShredPreferenc shredPreferenc;
    Context context = this;
    boolean sonuc, sonuc2;
    public static int yoneticiID;
    public  static int kullaniciID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        sifre = (EditText) findViewById(R.id.editTextUserPassword);
        kulAdi = (EditText) findViewById(R.id.editTextKulAdi);
        btn_giris = (Button) findViewById(R.id.buttonGirisYap);
        kayitOL = (TextView) findViewById(R.id.kayitOl);
        sifreUnuttum = (TextView) findViewById(R.id.SifreUnuttum);
        chkBeniHatirla = (CheckBox) findViewById(R.id.checkBox);
        shredPreferenc = new ShredPreferenc();

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgress =new ProgressDialog(this);
        String titleId="Giriş yapılıyor...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Lütfen bekleyiniz...");

        if (shredPreferenc.getValueBoolean(context,"remember")){
            kulAdi.setText(shredPreferenc.getValue(context,"username"));
            sifre.setText(shredPreferenc.getValue(context,"key"));
            chkBeniHatirla.setChecked(shredPreferenc.getValueBoolean(context,"remember"));
        }

        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();
                final String userEmail = kulAdi.getText().toString();
                String userSifre = sifre.getText().toString();

                if(userEmail.equals("") || userSifre.equals(""))
                    {
                    Toast.makeText(getApplicationContext(), "Alanlar boş bırakılamaz!", Toast.LENGTH_SHORT).show();
                    }
                else
                    {
                        if (chkBeniHatirla.isChecked()){
                            shredPreferenc.save(context, "username", kulAdi.getText().toString());
                            shredPreferenc.save(context, "key", sifre.getText().toString());
                        }
                        else
                        {
                            shredPreferenc.save(context, "username", "");
                            shredPreferenc.save(context, "key", "");
                        }
                        shredPreferenc.saveBoolean(context,"remember",chkBeniHatirla.isChecked());
                    new WebServisLogin().execute();
                    }
            }
        });


        kayitOL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(AnketGiris.this, KayitOl.class);
                startActivity(ıntent);
            }
        });

        sifreUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(AnketGiris.this, SifremiUnuttum.class);
                startActivity(ıntent);
            }
        });


    }

    //Yonetici giriş

    class WebServisLogin extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketLogin";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketLogin";
        private static final String METHOD_NAME = "anketLogin";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("KullaniciAdi", kulAdi.getText().toString());
            spObjectGonder.addProperty("Parola", sifre.getText().toString());
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
                new WebServisKullaniciLogin().execute();
                //mProgress.dismiss();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(getApplicationContext(), "Hoş Geldiniz...", Toast.LENGTH_SHORT).show();

                new WebServisYoneticiID().execute();
                Intent intent = new Intent(AnketGiris.this, MainActivity.class);
                startActivity(intent);
                mProgress.dismiss();

            }
        }
    }


    //Kullanici giriş

    class WebServisKullaniciLogin extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketKullaniciLogin";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketKullaniciLogin";
        private static final String METHOD_NAME = "anketKullaniciLogin";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("KullaniciAdi", kulAdi.getText().toString());
            spObjectGonder.addProperty("Parola", sifre.getText().toString());
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
                sonuc2 = Boolean.parseBoolean(spPrimitiveGelen.toString());
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
            if (!sonuc2) {

                Toast.makeText(getApplicationContext(), "Lütfen Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();
                mProgress.dismiss();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(getApplicationContext(), "Hoş Geldiniz Kullanıcı...", Toast.LENGTH_SHORT).show();

                new WebServisKullaniciID().execute();
                Intent intent = new Intent(AnketGiris.this, KullaniciMainActivity.class);
                startActivity(intent);
                mProgress.dismiss();

            }
        }
    }

    //yoneticiID

    class WebServisYoneticiID extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketYoneticiID";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketYoneticiID";
        private static final String METHOD_NAME = "anketYoneticiID";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("KullaniciAdi", kulAdi.getText().toString());
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
                yoneticiID = Integer.parseInt((spPrimitiveGelen.toString()));
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
            if (yoneticiID!=0) {


            } else {

            }
        }
    }

    //kullaniciID

    class WebServisKullaniciID extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketKullaniciID";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketKullaniciID";
        private static final String METHOD_NAME = "anketKullaniciID";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("KullaniciAdi", kulAdi.getText().toString());
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
                kullaniciID = Integer.parseInt((spPrimitiveGelen.toString()));
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
            if (kullaniciID!=0) {


            } else {

            }
        }
    }
}

