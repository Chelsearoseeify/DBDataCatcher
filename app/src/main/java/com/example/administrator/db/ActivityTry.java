package com.example.administrator.db;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 02/12/2017.
 */

public class ActivityTry extends AppCompatActivity {

    ContactAdapter dbContact;
    StructuresAdapter dbStructures;
    GeoAdapter dbGeo;
    TextView id;
    TextView name;
    TextView category;
    TextView segment;
    TextView tipology;
    TextView site;
    TextView mail;
    TextView latitude;
    TextView longitude;
    TextView address;
    Cursor structuresCursor, contactCursor, geoCursor;
    Button backButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_todo);

        backButton = findViewById(R.id.back);

        String idStringFromIntent = getIntent().getExtras().getString("id_struttura");

        dbContact = new ContactAdapter(this);
        dbStructures = new StructuresAdapter(this);
        dbGeo = new GeoAdapter(this);

        dbContact.open();
        dbStructures.open();
        dbGeo.open();

        contactCursor = dbContact.getContactById(Integer.parseInt(idStringFromIntent));
        geoCursor = dbGeo.getGeoById(Integer.parseInt(idStringFromIntent));
        structuresCursor = dbStructures.getStructureById(Integer.parseInt(idStringFromIntent));


        id = (TextView) findViewById(R.id.idView);
        name = (TextView) findViewById(R.id.nameView);
        category = (TextView) findViewById(R.id.categoriaView);
        segment = (TextView) findViewById(R.id.segmentoView);
        tipology = (TextView) findViewById(R.id.tipologiaView);
        site = (TextView) findViewById(R.id.sitoView);
        mail = (TextView) findViewById(R.id.emailView);
        latitude = (TextView) findViewById(R.id.latitudineView);
        longitude = (TextView) findViewById(R.id.longitudineView);
        address = (TextView) findViewById(R.id.indirizzoView);

        id.setText(idStringFromIntent);
        name.setText(structuresCursor.getString(structuresCursor.getColumnIndexOrThrow("struttura")));
        category.setText(structuresCursor.getString(structuresCursor.getColumnIndexOrThrow("categoria")));
        segment.setText(structuresCursor.getString(structuresCursor.getColumnIndexOrThrow("segmento")));
        tipology.setText(structuresCursor.getString(structuresCursor.getColumnIndexOrThrow("tipologia")));
        site.setText(contactCursor.getString(contactCursor.getColumnIndexOrThrow("sito")));
        mail.setText(contactCursor.getString(contactCursor.getColumnIndexOrThrow("mail")));
        latitude.setText(geoCursor.getString(geoCursor.getColumnIndexOrThrow("latitudine")));
        longitude.setText(geoCursor.getString(geoCursor.getColumnIndexOrThrow("longitudine")));
        address.setText(geoCursor.getString(geoCursor.getColumnIndexOrThrow("indirizzo")));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTry.this, ListViewCursorAdaptorActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
