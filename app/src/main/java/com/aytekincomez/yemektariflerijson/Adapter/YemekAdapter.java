package com.aytekincomez.yemektariflerijson.Adapter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aytekincomez.yemektariflerijson.Model.Yemek;
import com.aytekincomez.yemektariflerijson.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YemekAdapter extends BaseAdapter {

    Context context;
    ArrayList<Yemek> yemekler;
    LayoutInflater layoutInflater;

    public YemekAdapter(Activity activity, ArrayList<Yemek> yemekler){
        this.context = activity;
        this.yemekler = yemekler;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return yemekler.size();
    }

    @Override
    public Object getItem(int position) {
        return yemekler.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.yemek_gorunumu,null);

        ImageView ivResim = (ImageView)view.findViewById(R.id.ivResim);
        TextView tvBaslik = (TextView)view.findViewById(R.id.tvBaslik);
        TextView tvKategori = (TextView)view.findViewById(R.id.tvKategori);

        tvBaslik.setText(yemekler.get(position).getYemekAdi());
        tvKategori.setText(yemekler.get(position).getYemekTur());
        Picasso.get().load(yemekler.get(position).getYemekResim()).into(ivResim);

        return view;
    }
}
