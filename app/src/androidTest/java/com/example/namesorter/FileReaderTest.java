package com.example.namesorter;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.namesorter.presenter.FileReaderPresenter;
import com.example.namesorter.presenter.IFileReaderPresenter;
import com.example.namesorter.presenter.INameSorterPresenter;
import com.example.namesorter.view.IFileReaderView;
import com.example.namesorter.view.INameSorterView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class FileReaderTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    IFileReaderPresenter presenter;
    IFileReaderView view;

    @Before
    public void before() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        presenter = new FileReaderPresenter(mActivityRule.getActivity(), context);
    }


    @Test
    public void grantPermission() {
        presenter.grantPermission();
    }
}
