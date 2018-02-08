package com.alberto.reproductoryoutube;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alberto.reproductoryoutube.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private Activity activity;
    private ArrayList<Video> ListaDeVideos;

    public Adaptador(Activity activity, ArrayList<Video> list){
        this.activity = activity;
        this.ListaDeVideos = list;
    }

    @Override
    public int getCount() {
        return ListaDeVideos.size();
    }

    @Override
    public Object getItem(int posicion) {
        return ListaDeVideos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_youtube,null);
        }

        Video video = ListaDeVideos.get(posicion);

        TextView title = v.findViewById(R.id.textViewTitle);
        title.setText(video.getTitulo());

        TextView author = v.findViewById(R.id.textViewAuthor);
        author.setText(video.getAutor());

        ImageView imgVideo = v.findViewById(R.id.imageViewVideoItem);
        Picasso.with(v.getContext()).load(video.getImagen()).into(imgVideo);

        return v;
    }
}
