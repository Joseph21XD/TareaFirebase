package com.enigma.myfirebase;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ramir on 5/20/2017.
 */

public class Lista extends ArrayAdapter<Producto> {
    private ArrayList<Producto> n;
    private Activity context;

    public Lista(Activity context, ArrayList<Producto> names) {
        super(context, R.layout.lista_formato, names);
        this.context = context;
        this.n = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lista_formato, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
        textViewName.setText(n.get(position).nombre);
        ImageTask imageTask = new ImageTask();
        try {
            Bitmap bitmap =  imageTask.execute(n.get(position).getImagen()).get();
            image.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  listViewItem;
    }
}

//Environment.getExternalStorageDirectory()+ "/Reconizer/"+n.get(position)
