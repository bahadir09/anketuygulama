package com.example.asustr.anket;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by ASUS TR on 25.03.2018.
 */

public class AnketBitisTarih extends AppCompatActivity {

    public static int AnketID;

    private ProgressDialog mProgress;
    boolean sonuc;
    DatePicker tarih;
    TextView lblTarih;
    int yil, ay, gun;
    DatePickerDialog datePicker;
    String anketBasligi = "";

    Button btnBitisTarih, btnTarihSec;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_tarih);

        lblTarih = (TextView) findViewById(R.id.gosterilentarih);
        btnTarihSec = (Button) findViewById(R.id.btnTarihSec);

        anketBasligi = getIntent().getExtras().getString("anketBaslik");

        mProgress =new ProgressDialog(this);
        //String titleId="Giriş yapılıyor...";
        //mProgress.setTitle(titleId);
        mProgress.setMessage("Lütfen bekleyiniz...");

        btnTarihSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar takvim = Calendar.getInstance(); // Takvim objesini oluşturuyoruz.
                int yil = takvim.get(Calendar.YEAR); //Güncel Yılı alıyoruz.
                int ay = takvim.get(Calendar.MONTH); //Güncel Ayı alıyoruz.
                int gun = takvim.get(Calendar.DAY_OF_MONTH); //Güncel Günü alıyoruz.
                datePicker = new DatePickerDialog(AnketBitisTarih.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yil, int ay, int gun) {
                        // TODO Auto-generated method stub
                        ay += 1; // Aylar sıfırdan başladığı için ayı +1 ekliyoruz.
                        lblTarih.setText(gun + "/" + ay + "/" + yil); //Ekrana tüm tarihi yazdırıyoruz
                    }
                }, yil, ay, gun); //Sırasıyla set edilecek değerleri yazıyoruz.
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "SEÇ", datePicker);
                datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İPTAL", datePicker);
                datePicker.show();
            }
        });


        btnBitisTarih = (Button) findViewById(R.id.btnTarihDevam);

        btnBitisTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();

                if (lblTarih.getText().toString().trim().equals("")  || lblTarih==null){
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Lütfen Anket Bitiş Tarihini Giriniz..", Toast.LENGTH_SHORT).show();
                }
                else {
                    new WebServisAnketKaydet().execute();
                    mProgress.dismiss();
                }
            }
        });


    }

    class WebServisAnketKaydet extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketMobilAdd";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketMobilAdd";
        private static final String METHOD_NAME = "anketMobilAdd";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("yoneticiid", AnketGiris.yoneticiID);
            spObjectGonder.addProperty("anketBaslik", anketBasligi);
            spObjectGonder.addProperty("tarih", lblTarih.getText());
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

                Toast.makeText(getApplicationContext(), "Anket Kaydedilemedi.", Toast.LENGTH_LONG).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                new WebServisAnketID().execute();
                startActivity(new Intent(AnketBitisTarih.this, AnketOlustur.class));



            }
        }
    }

    //AnketID

    class WebServisAnketID extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=anketID";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/anketID";
        private static final String METHOD_NAME = "anketID";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("yoneticiid", AnketGiris.yoneticiID);

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
                AnketID = Integer.parseInt(spPrimitiveGelen.toString());
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
            if (AnketID!=0) {}
            else {}
        }
    }
}
