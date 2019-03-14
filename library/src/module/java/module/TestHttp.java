package module;

import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.request.BaseLibHttp;
import com.tzj.http.callback.OkCallBack;
import com.tzj.http.response.IResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class TestHttp extends BaseLibHttp {

    public String name="小花";
    public String age = "10";
    public TestItem item = new TestItem();
    public List<TestItem> list = new ArrayList<>();

    public TestHttp() {
        list.add(new TestItem());
        list.add(new TestItem());
    }

//    @Override
//    public RequestBody okBody() {
//        String s = "productId=001&pltId=02&version=3.00.00&sversion=1.00.01&paramMethod=getIconsList&paramContent={\"type\":\"1\"}";
//        MediaType parse = MediaType.parse(contentType());
//        return RequestBody.create(parse, s);
//    }

    @Override
    public String url() {
        return "https://cmsapi.jiankang51.cn/cms/mobile?&deepth=1&sessionId=1c0e3577c6a8446b9f6ad1e581c7d8aa&actionType=cmsInfoFacade&actionCode=getColumnNodeInfo&nodeId=532";
//        return "http://common.jiankang51.cn/common-interface/bannerIcon/getIconsList";
//        return "http://common.jiankang51.cn/common-interface/famousdoctor/getFamousListV2";
    }

//    @Override
//    public CacheType cacheType() {
//        return CacheType.USER_CACHE;
//    }

    public static void main(String[] args){
        OkCallBack callBack = new OkCallBack<String>(PlatformHandler.getInstance()) {
            @Override
            public void onOKResponse(Call call, IResponse<String> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call call, Exception e) {
                System.out.println("onFailure");
            }

            @Override
            public void onFinish() {
                System.out.println("onFinish");
            }
        };
        new TestHttp()
                .post(callBack);
    }
}
