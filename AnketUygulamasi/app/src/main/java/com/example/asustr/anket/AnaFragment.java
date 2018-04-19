package com.example.asustr.anket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kadir on 26.11.2017.
 */

public class AnaFragment extends Fragment {

    EditText editAnketBaslik;
    Button btnTikla;
    String baslik;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.ana_layout, container, false);

        editAnketBaslik = (EditText) myView.findViewById(R.id.edit_anket_basligi);
        btnTikla = (Button) myView.findViewById(R.id.btn_devamEt);

        btnTikla.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {

                baslik = editAnketBaslik.getText().toString().trim();

                if (baslik.isEmpty() || baslik == null || baslik == "") {
                    Toast.makeText(myView.getContext(), "Anket başlığı boş bırakılamaz!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(myView.getContext(), AnketBitisTarih.class);
                    i.putExtra("anketBaslik", baslik);
                    startActivity(i);
                    editAnketBaslik.setText("");
                }
            }
        });

        return myView;

    }
}



