package com.example.namesorter;

import android.content.Context;

import com.example.namesorter.models.Name;
import com.example.namesorter.presenter.FileReaderPresenter;
import com.example.namesorter.presenter.IFileReaderPresenter;
import com.example.namesorter.presenter.INameSorterPresenter;
import com.example.namesorter.presenter.NameSorterPresenter;
import com.example.namesorter.view.IFileReaderView;
import com.example.namesorter.view.INameSorterView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NameSorterTest {

    INameSorterPresenter presenter;

    @Mock
    INameSorterView view;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new NameSorterPresenter(view);
    }

    @Test
    public void sortbyFirstName() {
        List<String> listName = new ArrayList<>();
        listName.add("Eladia Robeson");
        listName.add("Adam Smith");
        listName.add("Sheryl Tsang");
        listName.add("Leroy Cassella");

        List<String> sortedListName = new ArrayList<>();
        sortedListName.add("Adam Smith");
        sortedListName.add("Eladia Robeson");
        sortedListName.add("Leroy Cassella");
        sortedListName.add("Sheryl Tsang");

        presenter.sortFirstName(listName);
        verify(view).onFirstNameSorted(listName);
        Assert.assertTrue(listName.equals(sortedListName));
    }

    @Test
    public void sortbyLastName() {
        List<Name> listName = new ArrayList<>();
        listName.add(new Name("Eladia", "Robeson"));
        listName.add(new Name("Adam", "Smith"));
        listName.add(new Name("Sheryl", "Tsang"));
        listName.add(new Name("Leroy", "Cassella"));

        List<String> listNameString = new ArrayList<>();
        listNameString.add("LeroyCassella");
        listNameString.add("EladiaRobeson");
        listNameString.add("AdamSmith");
        listNameString.add("SherylTsang");

        presenter.sortLastName(listName);
        verify(view).onLastNameSorted(listNameString);
    }
}
