package ru.plants.fab.plants;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ru.plants.fab.plants.db.AppDatabase;
import ru.plants.fab.plants.model.Plant;
//класс который отвечает за экран редактирования цветка
public class EditActivity extends AppCompatActivity {

    String[] data = {"Раз в день", "Раз в неделю", "Раз в месяц", "Два раза в месяц"};
    int id;

    Button saveButton;
    EditText editName;
    EditText editPlace;
    Spinner shceduleSpinner;
    AppDatabase db;

    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(plant);
            }
        });
        //мы берем айди той записи, которую хотим отредактирует
        int id = getIntent().getIntExtra("id", 1);
        //инициализируем базу данных
        db = Room.databaseBuilder(this, AppDatabase.class, "plantsdb").allowMainThreadQueries().build();
        //загружаем нужну запись и подставляем в активити
        plant = db.mPlantsDao().getPlant(id);
        editName = findViewById(R.id.editName);
        editName.setHint(plant.getName());

        editPlace = findViewById(R.id.editPlace);
        editPlace.setHint(plant.getPlace());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        shceduleSpinner = findViewById(R.id.spinnerEdit);
        shceduleSpinner.setAdapter(adapter);

        shceduleSpinner.setPrompt("Расписание");
        shceduleSpinner.setSelection(plant.getSchedule());

    }
    //этот метод чет не работает, но должен обновлять запись в базе
    private void update(Plant plant){
        plant.setId(id);
        plant.setName(editName.getText().toString());
        plant.setPlace(editPlace.getText().toString());
        plant.setSchedule(shceduleSpinner.getSelectedItemPosition());
        db.mPlantsDao().update(plant);
        startActivity(new Intent(EditActivity.this, MainActivity.class));
    }
}
