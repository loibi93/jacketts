package com.loibi93.jacketts.data;

import androidx.lifecycle.LiveData;

public abstract class AsyncLiveData<I, O> extends LiveData<O> implements AsyncCallback<O>, Runnable {
    private I asyncFunctionInput;
    private Exception error;
    private boolean isLoading;
    private Thread backgroundThread;

    public AsyncLiveData() {
        this(null);
    }

    public AsyncLiveData(I asyncFunctionInput) {
        this.asyncFunctionInput = asyncFunctionInput;
        this.error = null;
        this.isLoading = false;
        this.backgroundThread = null;
    }

    protected abstract O getData(I input);

    protected void onError(Exception error) {
        this.error = error;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public Exception getError() {
        return error;
    }

    @Override
    protected void onActive() {
        isLoading = true;
        backgroundThread = new Thread(this);
        backgroundThread.start();
    }

    @Override
    public void onFinish(O result) {
        setValue(result);
        isLoading = false;
    }

    @Override
    public void run() {
        O result = getData(asyncFunctionInput);
        onFinish(result);
    }
}
