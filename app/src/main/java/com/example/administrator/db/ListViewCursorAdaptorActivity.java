package com.example.administrator.db;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewCursorAdaptorActivity extends Activity {

    private StructuresAdapter dbStructureHelper;
    private ContactAdapter dbContactHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dbStructureHelper = new StructuresAdapter(this);
        dbContactHelper = new ContactAdapter(this);

        dbStructureHelper.open();
        dbContactHelper.open();

        //Clean all data
        //dbStructureHelper.deleteAllStructures();
        //Add some data
        //dbStructureHelper.insertSomeStructures();

        //Generate ListView from SQLite Database
        //displayListView();
        display();

    }

    private void display(){
        ListView listView = (ListView) findViewById(R.id.listView1);
        StructuresCursorAdapter structuresAdapter = new StructuresCursorAdapter(this, dbStructureHelper.fetchAllStructures());
        //ContactsCursorAdapter contactsAdapter = new ContactsCursorAdapter(this, dbContactHelper.fetchAllContact());
        // Attach cursor adapter to the ListView
        listView.setAdapter(structuresAdapter);
        //listView.setAdapter(contactsAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String structureCode = cursor.getString(cursor.getColumnIndexOrThrow("struttura"));
                //String countryCode = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                Toast.makeText(getApplicationContext(), structureCode, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListViewCursorAdaptorActivity.this, ActivityTry.class);
                intent.putExtra("id_struttura", cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                startActivity(intent);
                finish();

            }
        });

    }

    private void displayListView() {


        Cursor cursor = dbStructureHelper.fetchAllStructures();

        // The desired columns to be bound
        String[] columns = new String[] {
                StructuresAdapter.KEY_STRUTTURA,
                StructuresAdapter.KEY_CATEGORIA,
                StructuresAdapter.KEY_SEGMENTO,
                StructuresAdapter.KEY_TIPOLOGIA
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.struttura,
                R.id.categoria,
                R.id.segmento,
                R.id.tipologia
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.structure_info,
                cursor,
                columns,
                to,
                0);


        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String structureCode = cursor.getString(cursor.getColumnIndexOrThrow("struttura"));
                //String countryCode = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                Toast.makeText(getApplicationContext(), structureCode, Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbStructureHelper.fetchStructuresByCategory(constraint.toString());
            }
        });

    }
}