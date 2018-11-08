package module;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tzj.http.callback.OkCallBack;
import com.tzj.http.demo.R;
import com.tzj.http.platform.AndroidHandler;
import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.response.IResponse;

import okhttp3.Call;


public class ModuleActivity2 extends Activity implements View.OnClickListener {

    private TextView textView;
    private IPlatformHandler handler = new AndroidHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        textView = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        new TestHttp()
                .post(new OkCallBack<String>(handler) {
                    @Override
                    public void onOKResponse(Call call, IResponse<String> response) {
                        textView.setText(response.body());
                    }
                    @Override
                    public void onFinish() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.close(true);
    }
}
