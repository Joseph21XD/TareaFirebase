/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enigma.myfirebase;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();

        collapsingToolbar.setTitle(Main7Activity.productos.get(postion).nombre);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));


        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(Main7Activity.productos.get(postion).descripcion);

        TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText(Main7Activity.productos.get(postion).precio);

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        ImageTask imageTask = new ImageTask();
        Bitmap bitmap = null;
        try {
            bitmap = imageTask.execute(Main7Activity.productos.get(postion).getImagen()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        placePicutre.setImageBitmap(bitmap);
    }
}
