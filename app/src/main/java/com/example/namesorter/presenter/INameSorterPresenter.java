package com.example.namesorter.presenter;

import com.example.namesorter.models.Name;

import java.util.List;

public interface INameSorterPresenter {
    void sortFirstName(List<String> unsortedList);
    void sortLastName(List<Name> unsortedList);
}
