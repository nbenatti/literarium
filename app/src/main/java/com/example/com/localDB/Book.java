package literarium.localDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "book", primaryKeys = {"bookId", "userId"})
public class Book {
    @NonNull @PrimaryKey
    private int bookId;
    @NonNull @PrimaryKey
    private String userId;
    @NonNull
    private String title;
    @NonNull
    private Boolean status, seen;

    public Book(int bookId, String userId, String title, Boolean status, Boolean seen) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.status = status;
        this.seen = seen;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getSeen() {
        return seen;
    }

    
}
