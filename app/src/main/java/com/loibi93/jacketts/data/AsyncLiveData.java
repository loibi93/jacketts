package com.loibi93.jacketts.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.loibi93.jacketts.data.http.HttpException;

public abstract class AsyncLiveData<I, O> implements Runnable {
    protected I asyncFunctionInput;
    private MutableLiveData<O> liveData;
    private MutableLiveData<HttpException> errorLiveData;
    private MutableLiveData<Boolean> isLoadingData;

    public AsyncLiveData() {
        this.asyncFunctionInput = null;
        this.liveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();
        this.isLoadingData = new MutableLiveData<>();
    }

    public LiveData<O> getData() {
        return liveData;
    }

    public LiveData<HttpException> getErrorData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }

    protected abstract O loadData(I input) throws HttpException;

    public void update() {
        update(null);
    }

    public void update(I asyncFunctionInput) {
        this.asyncFunctionInput = asyncFunctionInput;
        Thread backgroundThread = new Thread(this);
        backgroundThread.start();
        isLoadingData.postValue(true);
    }

    @Override
    public void run() {
        try {
            O result = loadData(asyncFunctionInput);
            liveData.postValue(result);
            errorLiveData.postValue(null);
        } catch (HttpException e) {
            liveData.postValue(null);
            errorLiveData.postValue(e);
        }
        isLoadingData.postValue(false);
    }
}
