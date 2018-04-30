package com.example.asustr.anket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by ASUS TR on 28.04.2018.
 */

public class AnketlerimSorular extends AppCompatActivity {

    public static int AnketSoruSayisi;

    String sorular = "";
    String[] parçalananUser = null;
    ArrayList UserList = new ArrayList();
    boolean sonuc;
    int anketid;

    String Soru = "";
    String secenekbir = "";
    String secenekiki = "";
    String secenekuc = "";
    String secenekdort = "";

    private ArrayList<CoktanSecmeliSoruCek> soruCeks;
    private ListView listView;
    private CustomListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anket_sorularim_layout);

        listView = (ListView) findViewById(R.id.anket_sorularim_list_view);

        anketid = AnketlerimFragment.Anketid;

        new WebServisSoruSayisi().execute();
        new WebServisSoruGetir().execute();

    }

    private void initialize() {
        soruCeks = new ArrayList<CoktanSecmeliSoruCek>();
        listView = (ListView) findViewById(R.id.anket_sorularim_list_view);
        listViewAdapter = new CustomListViewAdapter(AnketlerimSorular.this, R.layout.custom_listview_coktan, soruCeks);
        listView.setAdapter(listViewAdapter);
    }

    private void fillArrayList(ArrayList<CoktanSecmeliSoruCek> soruCeks) {
        //for (int index = 0; index < UserList.size(); index++) {
        CoktanSecmeliSoruCek soruCek = new CoktanSecmeliSoruCek(Soru, secenekbir, secenekiki, secenekuc, secenekdort);
        soruCeks.add(soruCek);
        //}

    }

    class WebServisSoruGetir extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketSorulariGetir";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketSorulariGetir";
        private static final String METHOD_NAME = "AnketSorulariGetir";

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
            spObjectGonder.addProperty("AnketId", anketid);

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
                sorular = (spPrimitiveGelen.toString());
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

            if (sorular.equals(null) || sorular.equals("null")) {
                Toast.makeText(AnketlerimSorular.this, "Henüz gösterilecek soru yok!", Toast.LENGTH_SHORT).show();
            }
            else {
                Userparcala(sorular);
            }
        }
    }

    private void Userparcala(String gelenVeri) {

        int abc = AnketSoruSayisi;
        parçalananUser = null;
        UserList.clear();

        parçalananUser = gelenVeri.split(",");

        for (String a : parçalananUser) {

            int x = a.indexOf(":") + 2;
            int y = a.lastIndexOf("\"");
            UserList.add(a.substring(x, y));
        }

        int ak = 0;
        initialize();
        for (int i = 0; i < abc; i++) {

            Soru = UserList.get(ak).toString();
            ak++;
            secenekbir = UserList.get(ak).toString();
            ak++;
            secenekiki = UserList.get(ak).toString();
            ak++;
            secenekuc = UserList.get(ak).toString();
            ak++;
            secenekdort = UserList.get(ak).toString();
            ak++;
            fillArrayList(soruCeks);
        }
    }

    class WebServisSoruSayisi extends AsyncTask<Void, Void, Void> {


        private static final String NAMESPACE = "http://service.dahafazlaoku.com/WebService1.asmx";
        private static final String URL = "http://service.dahafazlaoku.com/WebService1.asmx?op=AnketSoruSayisi";
        private static final String SOAP_ACTION = "http://service.dahafazlaoku.com/WebService1.asmx/AnketSoruSayisi";
        private static final String METHOD_NAME = "AnketSoruSayisi";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*Servise gitmeden önce çalışacak kod bloğu.*/
        }

        @Override
        protected Void doInBackground(Void... params) {
   /*Servise gidip geldiği zamanlarda çalışacak kod bloğu.*/

            SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
            spObjectGonder.addProperty("AnketId", anketid);

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
                AnketSoruSayisi = Integer.parseInt(spPrimitiveGelen.toString());
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
            if (AnketSoruSayisi!=0) {}
            else {}
        }
    }
}
