package com.example.asustr.anket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ASUS TR on 15.04.2018.
 */

public class KullaniciCekiliseKatilFragment extends Fragment {

    View Myview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Myview = inflater.inflate(R.layout.kullanici_cekilise_katil, container, false);

        return Myview;
    }
}
