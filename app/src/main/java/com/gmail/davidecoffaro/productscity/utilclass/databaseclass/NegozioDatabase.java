package com.gmail.davidecoffaro.productscity.utilclass.databaseclass;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gmail.davidecoffaro.productscity.utilclass.Prodotto;



@Database(entities = {Prodotto.class}, version=1)
public abstract class NegozioDatabase extends RoomDatabase {
    public static NegozioDatabase database;

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Prodotto` (`productid` INTEGER, "
                    + "`nome` TEXT, "
                    + "`descrizione` TEXT, "
                    + "`prezzo` FLOAT, "
                    + "`quantita` INTEGER, "
                    + "PRIMARY KEY(`productid`))");
        }
    };

    public NegozioDatabase(){
        //design pattern singleton
        database = this;
    }

    public abstract ProdottoDao prodottoDao();
}
