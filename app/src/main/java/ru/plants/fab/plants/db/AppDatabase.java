package ru.plants.fab.plants.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.plants.fab.plants.model.Plant;
import ru.plants.fab.plants.dao.PlantsDao;
//тут определяются классы, которые являются таблицами в базе данных
//версия базы и все дао классы
@Database(entities = {Plant.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract PlantsDao mPlantsDao();
}
