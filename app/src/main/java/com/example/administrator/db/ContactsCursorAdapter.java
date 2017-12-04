package com.example.administrator.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 02/12/2017.
 */

public class ContactsCursorAdapter extends CursorAdapter{

    public ContactsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.structure_info, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        //TextView sitoView = (TextView) view.findViewById(R.idString.sito);
        // Extract properties from cursor
        String sito = cursor.getString(cursor.getColumnIndexOrThrow("sito"));
        // Populate fields with extracted properties
        //sitoView.setText(sito);
    }
}
