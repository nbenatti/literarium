package com.example.com.localDB;

import com.example.com.parsingData.parseType.AuthorInfo;
import com.example.com.parsingData.parseType.Book;

import java.util.ArrayList;
import java.util.List;

public class DbUtils {

    public static List<Book> convertBookDBToBook(List<BookDB> l) {

        List<Book> ret= new ArrayList<>();
        for(BookDB b : l){
            Book book= new Book(b.getBookId(),
                    b.getTitle(),
                    b.getIsbn(),
                    b.getImage_url(),
                    b.getPublication_year(),
                    b.getPublisher(),
                    b.getDescription(),
                    b.getAverage_rating(),
                    b.getNum_pages(),
                    new AuthorInfo(b.getId_author(), b.getName_author()));
            ret.add(book);
        }
        return ret;
    }

    public static Book convertSingleBookDbToBook(BookDB bookDB) {

        ArrayList<BookDB> tmp = new ArrayList<>();
        tmp.add(bookDB);

        return convertBookDBToBook(tmp).get(0);
    }

    public static BookDB convertBookToBookDB(Book book, int userid, boolean seen, boolean status) {

        return new BookDB(book.getId(),
                String.valueOf(userid),
                book.getTitle(),
                book.getIsbn(),
                book.getImageUrl(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getDescription(),
                book.getAverageRating(),
                book.getNumPages(),
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                seen,
                status);
    }
}
