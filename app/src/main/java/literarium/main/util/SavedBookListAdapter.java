package com.example.com.literarium;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.localDB.BookDB;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedBookListAdapter extends ArrayAdapter<BookDB> {

    private int layoutId;
    private Context ctx;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookRating;
    private ImageView ssIcon;

    private ImageView bookCover;

    public SavedBookListAdapter(Context context, int resource, List<BookDB> books) {
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

        BookDB book = getItem(position);

        if(book != null) {

            bookTitle = v.findViewById(R.id.bookTitle);
            bookAuthor = v.findViewById(R.id.bookAuthor);
            bookRating = v.findViewById(R.id.rating);
            bookCover = v.findViewById(R.id.bookCover);
            //ssIcon = v.findViewById(R.id.ssIcon);

            if(bookTitle != null)
                bookTitle.setText(book.getTitle());
            if(bookAuthor != null)
                bookAuthor.setText("by " + book.getName_author());
            if(bookRating != null)
                bookRating.setText(book.getAverage_rating()+"/5");
            if(bookCover != null)
                Picasso.get().load(book.getImage_url()).into(bookCover);
            if(ssIcon != null){
                Log.d("SavedBookListAdapter", "status: " + book.isStatus());
                if(!book.isStatus())
                    ssIcon.setImageResource(R.drawable.savedbook_icon);
                else
                    ssIcon.setImageResource(R.drawable.sentbook_icon);
            }
        }

        return v;
    }

}
