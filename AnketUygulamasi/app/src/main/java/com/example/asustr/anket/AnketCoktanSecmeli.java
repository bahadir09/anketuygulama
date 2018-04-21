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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS TR on 19.03.2018.
 */

public class AnketCoktanSecmeli extends AppCompatActivity {

    Button btnkaydet;
    EditText editTextSoru;
    Boolean sonuc;
    int soruTipi = 1;
    public static int soruID;
    public String editTextSecenekler="";


    // MultiEditText referansı
    MultiEditText multiEditText;
    // Geri dönecek String veriler için liste
    List<String> veriler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_ol_coktan_secmeli);

        multiEditText = new MultiEditText(this, R.id.layoutCoktanSecmeliEkle, "Seçenek");

        editTextSoru = (EditText) findViewById(R.id.editTextCoktanSoru);

        btnkaydet = (Button) findViewById(R.id.btnCoktanKaydet);
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                veriler = new ArrayList<String>();
                // Verilerimizi nesnemizden alıyoruz.
                veriler = multiEditText.getStringData();
                // Listemizi tarayıp bir Log basalım.
                for(int i=0; i<veriler.size(); i++){
                    //Log.d("AnketCoktanSecmeli", (i+1) + ".Seçenek: " + veriler.get(i));
                    String editTextSecenekler = veriler.get(i) + "@";
                }
                */

                new WebServisAnketCoktanSecmeliSoruKaydet().execute();

            }
        });
    }

    private void Parcala () {
        veriler = new ArrayList<String>();
        // Verilerimizi nesnemizden alıyoruz.
        veriler = multiEditText.getStringData();
        // Listemizi tarayıp bir Log basalım.
        for (int i = 0; i < veriler.size(); i++) {
            //Log.d("AnketCoktanSecmeli", (i+1) + ".Seçenek: " + veriler.get(i));
            editTextSecenekler += veriler.get(i) + "@";
        }
    }

    class WebServisAnketCoktanSecmeliSecenekKaydet extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketCoktanSecmeliSecenekKaydet";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketCoktanSecmeliSecenekKaydet";
        private static final String METHOD_NAME = "anketCoktanSecmeliSecenekKaydet";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("secenekler", editTextSecenekler);
            spObjectGonder.addProperty("soruId", soruID);
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

                Toast.makeText(getApplicationContext(), "Soru Kaydedilemedi.", Toast.LENGTH_LONG).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                new WebServisCoktanSecmeliSoruID().execute();
                startActivity(new Intent(AnketCoktanSecmeli.this, AnketOlustur.class));
                editTextSoru.setText("");
            }
        }
    }

    class WebServisAnketCoktanSecmeliSoruKaydet extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketSoruKaydet";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketSoruKaydet";
        private static final String METHOD_NAME = "anketSoruKaydet";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("anketId", AnketBitisTarih.AnketID);
            spObjectGonder.addProperty("Soru", editTextSoru.getText().toString());
            spObjectGonder.addProperty("soruTipi", soruTipi);
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

                Toast.makeText(getApplicationContext(), "Soru Kaydedilemedi.", Toast.LENGTH_LONG).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                new WebServisCoktanSecmeliSoruID().execute();
                Parcala();
                new WebServisAnketCoktanSecmeliSecenekKaydet().execute();
                //startActivity(new Intent(AnketCoktanSecmeli.this, AnketOlustur.class));
                //editTextSoru.setText("");
            }
        }
    }

    class WebServisCoktanSecmeliSoruID extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=SoruID";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/SoruID";
        private static final String METHOD_NAME = "SoruID";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("anketID", AnketBitisTarih.AnketID);

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
                soruID = Integer.parseInt(spPrimitiveGelen.toString());
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
            if (soruID!=0) {}
            else {}
        }
    }
}
