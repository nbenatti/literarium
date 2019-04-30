package localDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract BookDAO getBookDAO();
}
