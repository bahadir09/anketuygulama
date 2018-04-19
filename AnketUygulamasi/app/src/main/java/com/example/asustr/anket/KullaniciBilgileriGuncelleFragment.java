package com.example.asustr.anket;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by ASUS TR on 15.04.2018.
 */

public class KullaniciBilgileriGuncelleFragment extends Fragment {

    String kisi = "";
    String[] parçalananUser = null;
    ArrayList UserList = new ArrayList();
    boolean sonuc;

    View Myview;
    EditText KullaniciAd, KullaniciSoyad,KullaniciSifre, KullaniciMail, KullaniciTelefon;
    Button btnKullaniciGuncelle;

    String ad = "";
    String soyad = "";
    String sifre = "";
    String mail = "";
    String telefon = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Myview = inflater.inflate(R.layout.kullanici_bilgilerimi_guncelle, container,false);

        KullaniciAd = (EditText) Myview.findViewById(R.id.KullaniciGuncelleAdi);
        KullaniciSoyad = (EditText) Myview.findViewById(R.id.KullaniciGuncelleSoyad);
        KullaniciSifre = (EditText) Myview.findViewById(R.id.KullaniciGuncelleSifre);
        KullaniciMail = (EditText) Myview.findViewById(R.id.KullaniciGuncelleMail);
        KullaniciTelefon = (EditText) Myview.findViewById(R.id.KullaniciGuncelleTelefon);
        btnKullaniciGuncelle = (Button) Myview.findViewById(R.id.KullanicibuttonGuncelle);

        new WebServisKullaniciCek().execute();

        btnKullaniciGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WebServisKullaniciGuncelle().execute();
            }
        });

        return Myview;
    }

    private void KullaniciDoldur() {



        KullaniciAd.setText(ad,EditText.BufferType.EDITABLE);
        KullaniciSoyad.setText(soyad,EditText.BufferType.EDITABLE);
        KullaniciSifre.setText(sifre,EditText.BufferType.EDITABLE);
        KullaniciMail.setText(mail,EditText.BufferType.EDITABLE);
        KullaniciTelefon.setText(telefon,EditText.BufferType.EDITABLE);

    }

    private void Kullaniciparcala(String gelenVeri) {

        parçalananUser = null;
        UserList.clear();

        parçalananUser = gelenVeri.split(",");


        for (String a : parçalananUser) {

            int x = a.indexOf(":") + 2;
            int y = a.lastIndexOf("\"");
            UserList.add(a.substring(x, y));


        }
        ad = UserList.get(0).toString();
        soyad = UserList.get(1).toString();
        sifre = UserList.get(2).toString();
        telefon = UserList.get(3).toString();
        mail = UserList.get(4).toString();

    }

    class WebServisKullaniciCek extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketKullaniciGetir";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketKullaniciGetir";
        private static final String METHOD_NAME = "AnketKullaniciGetir";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
   /*ProgressDialog  pd; oncreate üzerinde global olarak tanımlanmalıdır.*/

        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("ID", AnketGiris.kullaniciID);

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
                kisi = (spPrimitiveGelen.toString());
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
            if (kisi == null) {

      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/

                Kullaniciparcala(kisi);
                KullaniciDoldur();

            }
        }
    }

    class WebServisKullaniciGuncelle extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketKullaniciGuncelle";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketKullaniciGuncelle";
        private static final String METHOD_NAME = "AnketKullaniciGuncelle";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
   /*ProgressDialog  pd; oncreate üzerinde global olarak tanımlanmalıdır.*/

        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("kullaniciID", AnketGiris.kullaniciID);
            spObjectGonder.addProperty("Adi", KullaniciAd.getText().toString());
            spObjectGonder.addProperty("Soyadi", KullaniciSoyad.getText().toString());
            spObjectGonder.addProperty("Sifre", KullaniciSifre.getText().toString());
            spObjectGonder.addProperty("Mail", KullaniciMail.getText().toString());
            spObjectGonder.addProperty("Telefon", KullaniciTelefon.getText().toString());
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
            if (sonuc == false) {
                Toast.makeText(Myview.getContext(), "İstenmeyen bir sorun oluştu...", Toast.LENGTH_SHORT).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(Myview.getContext(), "Kullanıcı bilgileri güncellendi..", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
