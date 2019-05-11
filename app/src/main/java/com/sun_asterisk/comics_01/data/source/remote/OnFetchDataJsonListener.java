package com.sun_asterisk.comics_01.data.source.remote;

import java.util.List;

public interface OnFetchDataJsonListener<T> {

    void onSuccess(List<T> data);

    void onError(Exception e);
}
