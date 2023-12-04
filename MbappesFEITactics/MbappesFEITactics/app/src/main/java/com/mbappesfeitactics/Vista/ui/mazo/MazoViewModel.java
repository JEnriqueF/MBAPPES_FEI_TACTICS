package com.mbappesfeitactics.Vista.ui.mazo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MazoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MazoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}