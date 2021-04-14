package com.example.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = (ListView) findViewById(R.id.list_drinks);
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK",
                    new String[]{"_id","NAME"},
                    null,null,null,null,null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,cursor,new String[]{"NAME"},new int[]{android.R.id.text1},0);
            listDrinks.setAdapter(listAdapter);
        } catch (SQLException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }









        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
                startActivity(intent);
            }
        };
        listDrinks.setOnItemClickListener(itemClickListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}