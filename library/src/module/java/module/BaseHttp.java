package module;

import com.tzj.http.http.ICacheHttp;
import com.tzj.http.callback.IHttpCallBack;

public class BaseHttp implements ICacheHttp {

    @Override
    public BaseHttp cache() {
        return this;
    }


    @Override
    public void post(IHttpCallBack callBack) {

    }
}
