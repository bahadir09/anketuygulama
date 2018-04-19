package com.example.asustr.anket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ASUS TR on 14.03.2018.
 */

public class AnketlerimFragment extends Fragment {

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.anketlerim_layout, container, false);




        return myView;

    }
}
