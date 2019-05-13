package literarium.localDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    public void insert(Book... books);
    @Update
    public void update(Book... books);
    @Delete
    public void delete(Book b);
    
    @Query("SELECT * FROM book AND userId = '"+id+"'")
    public List<Book> getAllBooks(int id);
    @Query("SELECT * FROM book WHERE status = 1 AND userId = '"+id+"'")
    public List<Book> getSavedBooks(int id);
    @Query("SELECT * FROM book WHERE status = 0 AND userId = '"+id+"'")
    public List<Book> getSentBooks(int id);
}