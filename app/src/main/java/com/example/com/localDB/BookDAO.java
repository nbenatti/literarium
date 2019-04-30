package localDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    public void insert(Book... books);
    @Update
    public void update(Book... books);
    @Delete
    public void delete(Book book);
    
    @Query("SELECT * FROM book")
    public List<Book> getAllBooks();
    @Query("SELECT * FROM book WHERE status = 'saved'")
    public List<Book> getSavedBooks();
    @Query("SELECT * FROM book WHERE status = 'sent'")
    public List<Book> getSentBooks();
}