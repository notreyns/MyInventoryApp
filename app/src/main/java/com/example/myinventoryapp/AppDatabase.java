package com.example.myinventoryapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDao();
}
