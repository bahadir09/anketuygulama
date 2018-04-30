package com.example.asustr.anket;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class AnketlerimFragment extends Fragment {

    public static int AnketSayisi;

    String Anketleridler = "";
    String Anketler = "";
    public static int Anketid;
    String[] parçalananUser = null;
    String[] veri;
    String[] anketIDler;
    ArrayList UserList = new ArrayList();
    boolean sonuc;
    private ListView listView;


    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.anketlerim_layout, container, false);
        listView = (ListView) myView.findViewById(R.id.anketlerim_list_view);

        new WebServisAnketSayisi().execute();
        new WebServisAnketGetir().execute();
        new WebServisAnketIDleriGetir().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());
                builder.setTitle("Anket Başlığı");
                builder.setMessage(veri[i]);
                builder.setPositiveButton("Soruları Gör", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Anketid = Integer.parseInt(anketIDler[i]);
                        Intent intent = new Intent(myView.getContext(),AnketlerimSorular.class);
                        intent.putExtra("anketid", Anketid);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                /*
                builder.setNeutralButton("Sonuçları Göster", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(myView.getContext(), "Sonuçları Göster", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Soruları Göster", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Toast.makeText(myView.getContext(), "İptal", Toast.LENGTH_SHORT).show();
                    }
                });*/
                builder.show();
            }
        });
        return myView;
    }

    class WebServisAnketGetir extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketBasligiGetir";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketBasligiGetir";
        private static final String METHOD_NAME = "AnketBasligiGetir";

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
            spObjectGonder.addProperty("yoneticiID", AnketGiris.yoneticiID);

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
                Anketler = (spPrimitiveGelen.toString());
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
   /*ProgressDialog işlemini sonlandırıyoruz.*/

            if (Anketler.equals(null) || Anketler.equals("null")) {
                Toast.makeText(myView.getContext(), "Henüz gösterilecek anket yok!", Toast.LENGTH_SHORT).show();
            } else {
                AnketBasligiParcala(Anketler);
            }
        }
    }

    class WebServisAnketIDleriGetir extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketIDler";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketIDler";
        private static final String METHOD_NAME = "AnketIDler";

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
            spObjectGonder.addProperty("yoneticiID", AnketGiris.yoneticiID);

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
                Anketleridler = (spPrimitiveGelen.toString());
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
   /*ProgressDialog işlemini sonlandırıyoruz.*/

            if (Anketleridler.equals(null) || Anketleridler.equals("null")) {
                //Toast.makeText(myView.getContext(), "Henüz gösterilecek anket yok!", Toast.LENGTH_SHORT).show();
            } else {
                AnketIDParcala(Anketleridler);
            }
        }
    }

    private void AnketIDParcala(String gelenVeri) {

        int abc = AnketSayisi;
        parçalananUser = null;
        UserList.clear();

        parçalananUser = gelenVeri.split(",");

        for (String a : parçalananUser) {

            int x = a.indexOf(":") + 2;
            int y = a.lastIndexOf("\"");
            UserList.add(a.substring(x, y));
        }

        int ak = 0;
        //initialize();
        anketIDler = new String[AnketSayisi];
        for (int i = 0; i < abc; i++) {

            anketIDler[i] = UserList.get(ak).toString();
            ak++;
        }
    }

    private void AnketBasligiParcala(String gelenVeri) {

        int abc = AnketSayisi;
        parçalananUser = null;
        UserList.clear();

        parçalananUser = gelenVeri.split(",");

        for (String a : parçalananUser) {

            int x = a.indexOf(":") + 2;
            int y = a.lastIndexOf("\"");
            UserList.add(a.substring(x, y));
        }

        int ak = 0;
        //initialize();
        veri = new String[AnketSayisi];
        for (int i = 0; i < abc; i++) {

            veri[i] = UserList.get(ak).toString();
            ak++;
        }
        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>
                (myView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, veri);
        listView.setAdapter(veriAdaptoru);


    }

    class WebServisAnketSayisi extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketSayisi";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketSayisi";
        private static final String METHOD_NAME = "AnketSayisi";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("yoneticiID", AnketGiris.yoneticiID);

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
                AnketSayisi = Integer.parseInt(spPrimitiveGelen.toString());
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
            if (AnketSayisi != 0) {
            } else {
            }
        }
    }
}
