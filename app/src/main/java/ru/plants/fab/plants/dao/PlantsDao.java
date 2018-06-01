package ru.plants.fab.plants.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.plants.fab.plants.model.Plant;
//это дао класс. Тут определяется, какие методы будут применяться к той или иной таблице
@Dao
public interface PlantsDao {
    @Insert
    void insert(Plant plant);

    @Delete
    void delete(Plant plant);

    @Update
    void update(Plant plant);

    @Query("SELECT * FROM plants")
    List<Plant> getAll();

    @Query("SELECT * FROM plants WHERE id = :id")
    Plant getPlant(int id);

}
