package localDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "book")
public class Book {
    @PrimaryKey
    @NonNull
    private Integer bookId;
    @PrimaryKey
    @NonNull
    private Integer userId;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Integer numPages;
    @NonNull
    private Boolean status;

    @NonNull
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(@NonNull Integer bookId) {
        this.bookId = bookId;
    }

    @NonNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(@NonNull Integer numPages) {
        this.numPages = numPages;
    }

    @NonNull
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(@NonNull Boolean status) {
        this.status = status;
    }
}
