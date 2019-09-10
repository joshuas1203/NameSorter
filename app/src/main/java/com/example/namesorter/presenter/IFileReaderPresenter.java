package com.example.namesorter.presenter;

import java.util.List;

public interface IFileReaderPresenter {
    void grantPermission();
    void readNameList(String input);
    void readLastNameList(String input);
}
