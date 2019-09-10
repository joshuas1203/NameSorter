package com.example.namesorter.view;

import com.example.namesorter.models.Name;

import java.util.List;

public interface IFileReaderView {
    void onPermissionGranted();
    void onNameListRead(List<String> result);
    void onLastNameListRead(List<Name> result);
}
