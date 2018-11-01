package module;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tzj.http.callback.CallBack;
import com.tzj.http.callback.OkCallBack;
import com.tzj.http.demo.R;
import com.tzj.http.response.IResponse;
import com.tzj.http.util.IToast;
import com.tzj.http.util.UtilToast;

import okhttp3.Call;


public class ModuleActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        UtilToast.init(new IToast() {
            @Override
            public void showToast(String str) {
                Toast.makeText(ModuleActivity.this,str,Toast.LENGTH_LONG).show();
            }

            @Override
            public void showToast(int res) {
                Toast.makeText(ModuleActivity.this,res+"",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        new TestHttp()
                .post(new OkCallBack<String>() {
                    @Override
                    public void onOKResponse(Call call, IResponse<String> response) {
                        Toast.makeText(ModuleActivity.this,response.body(),Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFinish() {

                    }
                });
    }
}
