package com.example.asustr.anket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS TR on 27.04.2018.
 */

public class CustomListViewAdapter extends ArrayAdapter<CoktanSecmeliSoruCek> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<CoktanSecmeliSoruCek> coktansecmeli;



    public CustomListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CoktanSecmeliSoruCek> coktansecmeli) {
        super(context, resource, coktansecmeli);
        this.context = context;
        this.coktansecmeli = coktansecmeli;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return coktansecmeli.size();
    }

    @Override
    public CoktanSecmeliSoruCek getItem(int position) {
        return coktansecmeli.get(position);
    }

    @Override
    public long getItemId(int position) {
        return coktansecmeli.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.custom_listview_coktan, null);

            holder = new ViewHolder();

            holder.Soru = (TextView) convertView.findViewById(R.id.txtCoktanSoru);
            holder.secenek1 = (CheckBox) convertView.findViewById(R.id.checkBox1Coktan);
            holder.secenek2 = (CheckBox) convertView.findViewById(R.id.checkBox2Coktan);
            holder.secenek3 = (CheckBox) convertView.findViewById(R.id.checkBox3Coktan);
            holder.secenek4 = (CheckBox) convertView.findViewById(R.id.checkBox4Coktan);

            convertView.setTag(holder);

        }
        else{
            //Get viewholder we already created
            holder = (ViewHolder)convertView.getTag();
        }

        CoktanSecmeliSoruCek soruCek = coktansecmeli.get(position);
        if (soruCek != null){
            holder.Soru.setText(soruCek.getSoru());
            holder.secenek1.setText(soruCek.getSecenek1());
            holder.secenek2.setText(soruCek.getSecenek2());
            holder.secenek3.setText(soruCek.getSecenek3());
            holder.secenek4.setText(soruCek.getSecenek4());

        }
        return convertView;
    }

    private static class ViewHolder {
        TextView Soru;
        CheckBox secenek1;
        CheckBox secenek2;
        CheckBox secenek3;
        CheckBox secenek4;

    }

}
