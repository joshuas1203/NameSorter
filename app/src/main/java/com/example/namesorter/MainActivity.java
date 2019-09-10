package com.example.namesorter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.namesorter.models.Name;
import com.example.namesorter.presenter.FileReaderPresenter;
import com.example.namesorter.presenter.IFileReaderPresenter;
import com.example.namesorter.presenter.INameSorterPresenter;
import com.example.namesorter.presenter.NameSorterPresenter;
import com.example.namesorter.view.IFileReaderView;
import com.example.namesorter.view.INameSorterView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IFileReaderView, INameSorterView {

    @BindView(R.id.list_file)
    ListView listFile;

    @BindView(R.id.btn_select)
    Button btnSelect;

    @BindView(R.id.btn_sortfirstname)
    Button btnSortFirstName;

    @BindView(R.id.btn_sortlastname)
    Button btnSortLastName;

    @BindView(R.id.btn_default)
    Button btnDefault;

    private static final int READ_REQUEST_CODE = 42;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private ArrayAdapter adapter;
    private List<String> listName;
    private List<Name> listNameObj;
    private String path;
    private IFileReaderPresenter fileReaderPresenter;
    private INameSorterPresenter nameSorterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnSortFirstName.setEnabled(false);
        btnDefault.setEnabled(false);
        btnSortLastName.setEnabled(false);
        listName = new ArrayList<>();
        listNameObj = new ArrayList<>();
        fileReaderPresenter = new FileReaderPresenter(this, this);
        nameSorterPresenter = new NameSorterPresenter(this);

        fileReaderPresenter.grantPermission();
    }

    @OnClick(R.id.btn_select)
    public void select() {
        performFileSearch();
    }

    @OnClick(R.id.btn_sortfirstname)
    public void sort() {
        nameSorterPresenter.sortFirstName(listName);
    }

    @OnClick(R.id.btn_default)
    public void setDefault() {
        fileReaderPresenter.readNameList(path);
    }

    @OnClick(R.id.btn_sortlastname)
    public void sortLast() {
        fileReaderPresenter.readLastNameList(path);
        nameSorterPresenter.sortLastName(listNameObj);
    }

    //select file from storage
    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    //read content of the file
    private List<Name> readLastName(String input) {
        File file = new File(input);
        List<Name> text = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.add(new Name(line.substring(0, line.indexOf(" ")), line.substring(line.indexOf(" "))));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Uri uri = data.getData();
                path = uri.getPath();
                path = path.substring(path.indexOf(":") +1);
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                fileReaderPresenter.readNameList(path);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onPermissionGranted() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
    }

    @Override
    public void onNameListRead(List<String> result) {
        listName = result;
        adapter = new ArrayAdapter(this,R.layout.activity_listview, result);
        adapter.notifyDataSetChanged();
        listFile.setAdapter(adapter);

        if(result != null) {
            btnSortFirstName.setEnabled(true);
            btnDefault.setEnabled(true);
            btnSortLastName.setEnabled(true);
        }
        else {
            btnSortFirstName.setEnabled(false);
            btnDefault.setEnabled(false);
            btnSortLastName.setEnabled(false);
        }
    }

    @Override
    public void onLastNameListRead(List<Name> result) {
        listNameObj = result;
    }

    @Override
    public void onFirstNameSorted(List<String> sortedList) {
        adapter = new ArrayAdapter(this, R.layout.activity_listview, sortedList);
        adapter.notifyDataSetChanged();
        listFile.setAdapter(adapter);
    }

    @Override
    public void onLastNameSorted(List<String> sortedList) {
        adapter = new ArrayAdapter(this, R.layout.activity_listview, sortedList);
        adapter.notifyDataSetChanged();
        listFile.setAdapter(adapter);
    }
}
