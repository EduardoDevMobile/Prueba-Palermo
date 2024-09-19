package com.example.prueba_palermo.model.bd.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prueba_palermo.model.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Query("SELECT * FROM items")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM items WHERE name = :name")
    List<Item> getItemByName(String name);
}
