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
}
