package ru.plants.fab.plants;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import ru.plants.fab.plants.db.AppDatabase;
import ru.plants.fab.plants.model.Plant;

public class DetailsActivity extends AppCompatActivity {

    TextView name;
    TextView place;
    TextView earth;
    TextView air;
    TextView temperature;

    int id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = getIntent().getIntExtra("id", 0) + 1;

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "plantsdb").allowMainThreadQueries().build();

        Plant plant = db.mPlantsDao().getPlant(id);

        name = findViewById(R.id.nameTextView);
        place = findViewById(R.id.placeTextView);
        earth = findViewById(R.id.earthTextView);
        air = findViewById(R.id.earTextView);
        temperature = findViewById(R.id.tempTextView);

        name.setText(plant.getName());
        place.setText(plant.getPlace());

        earth.setText(new Random().nextInt(100) + "%");
        air.setText(new Random().nextInt(100) + "%");
        temperature.setText(new Random().nextInt(30) + "c");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, EditActivity.class);
                i.putExtra("id", id);
                startActivity(i);
//                Snackbar.make(view, "Пока что не реализованно", Snackbar.LENGTH_LONG)
//                        .setAction("Редактирование", null).show();
            }
        });
    }

}
