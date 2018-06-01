package ru.plants.fab.plants;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.plants.fab.plants.db.AppDatabase;
import ru.plants.fab.plants.model.Plant;

import static android.widget.LinearLayout.VERTICAL;

//это главное активити, в котором находится список растений
public class MainActivity extends AppCompatActivity {
    //переменные для показа списка
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView firstPlant;
    List<Plant> data;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstPlant = findViewById(R.id.addFirstPlant);
        firstPlant.setVisibility(View.GONE);
        //инициализация базы данных, что бы она работала не в основном потоке
        db = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "plantsdb").allowMainThreadQueries().build();
        //загрузка списка растений
        data = db.mPlantsDao().getAll();
        //если список не пуст, то убераем сообщение добавить первое растение
        //и выводим список растений
        if(data.size() <= 0){
            firstPlant.setVisibility(View.VISIBLE);
        }else {
            mRecyclerView = findViewById(R.id.recyclerView);

            DriverItemDecoration itemDecoration = new DriverItemDecoration(this);
            mRecyclerView.addItemDecoration(itemDecoration);

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(this) {
                @Override
                public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                    Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                    i.putExtra("id", position);
                    startActivity(i);
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });

            mAdapter = new RecAdapter(data);
            mRecyclerView.setAdapter(mAdapter);
        }
        //круглая кнопка с плюсом, если нажать - откроется активити с добавлением 
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddingActivity.class));
                finish();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        data = db.mPlantsDao().getAll();
        try {
            mAdapter = new RecAdapter(data);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
//            mRecyclerView.invalidate();
            firstPlant.setVisibility(View.INVISIBLE);
        }catch (NullPointerException e){

        }
    }


}
