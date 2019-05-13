package com.example.com.literarium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.com.dataAcquisition.parseType.Book;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<Book> {

    private int layoutId;
    private Context ctx;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookRating;

    public BookListAdapter(Context context, int resource, List<Book> books) {
        super(context, resource);
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

            if(bookTitle != null)
                bookTitle.setText(book.getTitle());
            if(bookAuthor != null)
                bookAuthor.setText("by " + book.getAuthor());
            if(bookRating != null) {
                //bookRating.setText(book+"/10");
            }
        }

        return v;
    }
}
