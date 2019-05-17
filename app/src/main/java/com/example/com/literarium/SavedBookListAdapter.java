package com.example.com.literarium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.localDB.BookDAO;
import com.example.com.parsingData.parseType.Book;
import java.util.List;

public class SavedBookListAdapter extends ArrayAdapter<Book> {

    private int layoutId;
    private Context ctx;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookRating;
    private ImageView ssIcon;

    private ImageView bookCover;

    public SavedBookListAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);

        ctx = context;
        layoutId = resource;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        View v = convertView;

        if(v == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(ctx);
            v = layoutInflater.inflate(layoutId, null);
        }

        Book book = getItem(position);

        if(book != null) {

            bookTitle = v.findViewById(R.id.bookTitle);
            bookAuthor = v.findViewById(R.id.bookAuthor);
            bookRating = v.findViewById(R.id.rating);
            bookCover = v.findViewById(R.id.bookCover);
            ssIcon = v.findViewById(R.id.ssIcon);

            if(bookTitle != null)
                bookTitle.setText(book.getTitle());
            if(bookAuthor != null)
                bookAuthor.setText("by " + book.getAuthor().getName());
            if(bookRating != null)
                bookRating.setText(book.getAverageRating()+"/10");
            if(bookCover != null)
                Picasso.get().load(book.getImageUrl()).into(bookCover);
            if(ssIcon != null){
                if(book.())
                    ssIcon.setBackgroundResource(R.drawable.savedbook_icon);
                else
                    ssIcon.setBackgroundResource(R.drawable.sentbook_icon);
            }
        }

        return v;
    }

}
