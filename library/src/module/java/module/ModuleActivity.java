package module;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tzj.http.response.HoldResponse;
import com.tzj.http.ICall;
import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.demo.R;


public class ModuleActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

    }

    @Override
    public void onClick(View v) {
        new TestHttp()
                .cache()
                .post(new IHttpCallBack() {

                    @Override
                    public void onResponse(ICall call, HoldResponse response) {
                        Object response1 = response.getResponse();
                    }

                    @Override
                    public void onFailure(ICall call, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
