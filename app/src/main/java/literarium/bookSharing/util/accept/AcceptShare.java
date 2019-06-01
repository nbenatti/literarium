package com.example.com.bookSharing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.example.com.literarium.GetBookDataTask;
import com.example.com.literarium.R;
import com.example.com.localDB.SaveBookTask;
import com.example.com.parsingData.enumType.BookType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AcceptShare extends JobIntentService {

    private static final int SERVICE_JOB_ID = 102;

    private List<ShareData> shareDataList;

    private List<com.example.com.parsingData.parseType.Book> fullBookData;

    public AcceptShare() {
    }

    public static void enqueueWork(Context context, Intent work) {
        Log.d("ListenForSharesService", "accept task started");
        enqueueWork(context, AcceptShare.class, SERVICE_JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    protected void onHandleIntent(Intent intent) {
        Log.d("ListenForSharesService", "share accepted");

        Bundle b = intent.getExtras();

        shareDataList = b.getParcelableArrayList(getString(R.string.share_data));

        fullBookData = new ArrayList<>();

        try {

            // get full data of all the books
            for(ShareData sd : shareDataList) {

                GetBookDataTask getBookDataTask = new GetBookDataTask(this, Integer.parseInt(sd.getBookId()));
                com.example.com.parsingData.parseType.Book toSave = (com.example.com.parsingData.parseType.Book)getBookDataTask.execute().get();

                fullBookData.add(toSave);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // save all the books in the local archive
        SaveBookTask saveBookTask = new SaveBookTask(this, fullBookData, BookType.RECEIVED_BOOK);
        saveBookTask.execute();
    }
}
