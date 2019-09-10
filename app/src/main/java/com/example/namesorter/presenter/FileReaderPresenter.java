package com.example.namesorter.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.namesorter.models.Name;
import com.example.namesorter.view.IFileReaderView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderPresenter implements IFileReaderPresenter {

    private IFileReaderView fileReaderView;
    private Context context;

    public FileReaderPresenter(IFileReaderView fileReaderView, Context context) {
        this.fileReaderView = fileReaderView;
        this.context = context;
    }

    @Override
    public void grantPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fileReaderView.onPermissionGranted();
            }
        }
    }

    @Override
    public void readNameList(String input) {
        File file = new File(input);
        List<String> listName = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                listName.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileReaderView.onNameListRead(listName);
    }

    @Override
    public void readLastNameList(String input) {
        File file = new File(input);
        List<Name> listName = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                listName.add(new Name(line.substring(0, line.indexOf(" ")), line.substring(line.indexOf(" "))));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileReaderView.onLastNameListRead(listName);
    }
}
