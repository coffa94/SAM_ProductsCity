package com.gmail.davidecoffaro.productscity.utilclass.databaseclass;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;

import java.util.List;

@Dao
public interface ProdottoDao {
    @Insert
    void insertAll(Prodotto... prodotti);

    @Insert
    void insert(Prodotto prodotto);

    @Query("SELECT * FROM prodotto")
    List<Prodotto> getAll();

    @Query("SELECT * FROM prodotto WHERE quantita>0")
    List<Prodotto> getBuyed();

}
