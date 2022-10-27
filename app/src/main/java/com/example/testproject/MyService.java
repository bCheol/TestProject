package com.example.testproject;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.OpenableColumns;

import java.io.File;
import java.util.ArrayList;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            changeFile(intent);
        }else{
            return super.onStartCommand(intent, flags, startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void changeFile(Intent intent){
        ArrayList<File> fileArrayList = new ArrayList<>();
        ArrayList<Uri> uriArrayList = intent.getParcelableArrayListExtra("list");
        //변환
        for(int i=0; i<uriArrayList.size(); i++){
            fileArrayList.add(changeFile(uriArrayList.get(i)));
        }
        //보내기
        for(int i =0; i<fileArrayList.size(); i++){

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public File changeFile(Uri uri){




    }

}