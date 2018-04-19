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
 * Created by ASUS TR on 14.03.2018.
 */

public class BilgilerimiGuncelleYoneticiFragment extends Fragment {

    private EditText editTextAd, editTextSoyad, editTextSifre, editTextMail, editTextTelefon;
    Button guncelle;
    String kisi = "";
    String[] parçalananUser = null;
    ArrayList UserList = new ArrayList();
    boolean sonuc;
    View myView;
    String ad = "";
    String soyad = "";
    String sifre = "";
    String mail = "";
    String telefon = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.bilgileri_guncelle_layout, container, false);

        editTextAd = (EditText) myView.findViewById(R.id.editTextGuncelleAdi);
        editTextSoyad = (EditText) myView.findViewById(R.id.editTextGuncelleSoyad);
        editTextSifre = (EditText) myView.findViewById(R.id.editTextGuncelleSifre);
        editTextMail = (EditText) myView.findViewById(R.id.editTextGuncelleMail);
        editTextTelefon = (EditText) myView.findViewById(R.id.editTextGuncelleTelefon);
        guncelle = (Button) myView.findViewById(R.id.buttonGuncelle);


        new WebServisYoneticiCek().execute();


        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WebServisGuncelle().execute();
            }
        });

        return myView;

    }



    private void UserDoldur() {



        editTextAd.setText(ad,EditText.BufferType.EDITABLE);
        editTextSoyad.setText(soyad,EditText.BufferType.EDITABLE);
        editTextSifre.setText(sifre,EditText.BufferType.EDITABLE);
        editTextMail.setText(mail,EditText.BufferType.EDITABLE);
        editTextTelefon.setText(telefon,EditText.BufferType.EDITABLE);

    }

    class WebServisYoneticiCek extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketYoneticiGetir";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketYoneticiGetir";
        private static final String METHOD_NAME = "AnketYoneticiGetir";

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
            spObjectGonder.addProperty("ID", AnketGiris.yoneticiID);

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

                Userparcala(kisi);
                UserDoldur();

            }
        }
    }


    class WebServisGuncelle extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketYoneticiGuncelle";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketYoneticiGuncelle";
        private static final String METHOD_NAME = "AnketYoneticiGuncelle";

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
            spObjectGonder.addProperty("kullaniciID", AnketGiris.yoneticiID);
            spObjectGonder.addProperty("Adi", editTextAd.getText().toString());
            spObjectGonder.addProperty("Soyadi", editTextSoyad.getText().toString());
            spObjectGonder.addProperty("Sifre", editTextSifre.getText().toString());
            spObjectGonder.addProperty("Mail", editTextMail.getText().toString());
            spObjectGonder.addProperty("Telefon", editTextTelefon.getText().toString());
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
                Toast.makeText(myView.getContext(), "İstenmeyen bir sorun oluştu...", Toast.LENGTH_SHORT).show();
      /*Eğer servisten sıfır değeri gelmişse Kullanıcı Adı Şifre yanlış diye Toast mesajı veriyoruz.*/
            } else {
    /*Eğer servisten bir değeri gelmişse anasayfaya yönlendirme işlemi yapıyoruz.*/
                Toast.makeText(myView.getContext(), "Kullanıcı bilgileri güncellendi..", Toast.LENGTH_SHORT).show();


            }
        }
    }


    private void Userparcala(String gelenVeri) {

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


}
