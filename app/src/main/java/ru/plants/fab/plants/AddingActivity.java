package ru.plants.fab.plants;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import ru.plants.fab.plants.db.AppDatabase;
import ru.plants.fab.plants.model.Plant;

public class AddingActivity extends AppCompatActivity {

    String[] data = {"Раз в день", "Раз в неделю", "Раз в месяц", "Два раза в месяц"};

    Button addButton;

    EditText name;
    EditText place;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        db = Room.databaseBuilder(AddingActivity.this, AppDatabase.class, "plantsdb").allowMainThreadQueries().build();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = findViewById(R.id.spinnerSchedule);
        spinner.setAdapter(adapter);

        spinner.setPrompt("Расписание");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        name = findViewById(R.id.name_edit);
        place = findViewById(R.id.place_edit);

        addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plant plant = new Plant();
                if(name.getText().length()!=0){
                    plant.setName(name.getText().toString());
                }else {
                    Toast.makeText(AddingActivity.this, "Введите название растения", Toast.LENGTH_LONG).show();
                    return;
                }
                if(place.getText().length()!=0){
                    plant.setPlace(place.getText().toString());
                }else {
                    Toast.makeText(AddingActivity.this, "Введите место расположения", Toast.LENGTH_LONG).show();
                    return;
                }
                plant.setSchedule(spinner.getSelectedItemPosition());
                db.mPlantsDao().insert(plant);
                startActivity(new Intent(AddingActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
