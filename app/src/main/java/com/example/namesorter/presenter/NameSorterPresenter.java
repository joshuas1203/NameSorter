package com.example.namesorter.presenter;

import com.example.namesorter.models.Name;
import com.example.namesorter.view.INameSorterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameSorterPresenter implements INameSorterPresenter {

    private INameSorterView nameSorterView;

    public NameSorterPresenter(INameSorterView nameSorterView) {
        this.nameSorterView = nameSorterView;
    }

    @Override
    public void sortFirstName(List<String> unsortedList) {
        Collections.sort(unsortedList);
        nameSorterView.onFirstNameSorted(unsortedList);
    }

    @Override
    public void sortLastName(List<Name> unsortedList) {
        List<String> sortedListString = new ArrayList<>();
        Collections.sort(unsortedList);
        for(Name name : unsortedList) {
            sortedListString.add(name.getFirstName() + "" + name.getLastName());
        }
        nameSorterView.onLastNameSorted(sortedListString);
    }
}
