package com.robpercival.demoapp.rest.service;

/**
 * Created by User on 5/19/2018.
 */

public interface ServiceCallback<T> {

    void onSuccess(T body);

    void onError(T body);
}
