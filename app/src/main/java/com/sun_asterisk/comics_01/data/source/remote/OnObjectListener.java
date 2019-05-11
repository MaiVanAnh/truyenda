package com.sun_asterisk.comics_01.data.source.remote;

import com.sun_asterisk.comics_01.data.model.User;

/**
 * Created by Mai Van Anh on 03/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface OnObjectListener<T> {
    void onSuccess(T data);

    void onFailure(Exception exception);
}
