package com.example.namesorter.view;


import java.util.List;

public interface INameSorterView {
    void onFirstNameSorted(List<String> sortedList);
    void onLastNameSorted(List<String> sortedList);
}
