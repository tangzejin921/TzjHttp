package com.tzj.http.callback;


import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.response.IResponse;
import com.tzj.http.util.UtilToast;

import okhttp3.Call;

/**
 * ICallBack 的一个实现
 */
public abstract class LibCallBack<T> extends OkCallBack<T> implements ICallBack<T>{

    public LibCallBack(IPlatformHandler handler) {
        super(handler);
    }

    @Override
    public void onErr(Call call, IResponse<T> response) {
        UtilToast.showToast(response.msg());
    }

    @Override
    public void onPermission(Call call, IResponse<T> response) {

    }

    @Override
    public void onOKResponse(Call call, IResponse<T> response) {
        if (response.isOk()){
            onSuccess(call,response);
        }else{
            onErr(call,response);
        }
    }

    @Override
    public void onFinish() {

    }
}
