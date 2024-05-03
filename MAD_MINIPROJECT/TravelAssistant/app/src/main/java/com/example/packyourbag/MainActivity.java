package com.example.packyourbag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packyourbag.Adapter.MyAdapter;
import com.example.packyourbag.Constants.MyConstants;
import com.example.packyourbag.Data.AppData;
import com.example.packyourbag.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    MyAdapter adapter;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide toolbar
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView = findViewById(R.id.recyclerView);

        addAllTitles();
        addAllImages();
        persistAppData();
        database = RoomDB.getInstance(this);

        adapter = new MyAdapter(this, titles, images, MainActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void persistAppData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDB.getInstance(this);
        AppData appData = new AppData(database);
        int last = prefs.getInt(AppData.LAST_VERSION, 0);

        if(!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, false)) {
            appData.persistAllData();
            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, true);
            editor.apply();
        } else if(last < AppData.NEW_VERSION) {
            database.mainDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION);
            editor.apply();
        }

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    private void addAllImages() {
        images = new ArrayList<>();
        images.add(R.drawable.cloths3);
        images.add(R.drawable.prscare1);
        images.add(R.drawable.baby1);
        images.add(R.drawable.health1);
        images.add(R.drawable.gadgets3);
        images.add(R.drawable.food2);
        images.add(R.drawable.beach3);
//        images.add(R.drawable.car);
        images.add(R.drawable.docs1);
        images.add(R.drawable.mylist4);
        images.add(R.drawable.myselection);
//        images.add(R.drawable.selection);
    }

    private void addAllTitles() {
        titles = new ArrayList<>();
//        titles.add(MyConstants.BASIC_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.CLOTHING_CAMEL_CASE);
        titles.add(MyConstants.PERSONAL_CARE_CAMEL_CASE);
        titles.add(MyConstants.BABY_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.HEALTH_CAMEL_CASE);
        titles.add(MyConstants.TECHNOLOGY_CAMEL_CASE);
        titles.add(MyConstants.FOOD_CAMEL_CASE);
        titles.add(MyConstants.BEACH_SUPPLIES_CAMEL_CASE);
//        titles.add(MyConstants.CAR_SUPPLIES_CAMEL_CASE);
        titles.add(MyConstants.DOCS_CAMEL_CASE);
        titles.add(MyConstants.MY_LIST_CAMEL_CASE);
        titles.add(MyConstants.MY_SELECTIONS_CAMEL_CASE);

    }
}