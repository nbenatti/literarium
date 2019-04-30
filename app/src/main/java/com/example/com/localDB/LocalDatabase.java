package localDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.com.localDB.BookDAO;

@Database(entities = {localDB.Book.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract BookDAO getBookDAO();
}
