package module;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tzj.http.demo.R;
import com.tzj.http.util.IToast;
import com.tzj.http.util.UtilToast;


public class ModuleActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        UtilToast.init(new IToast() {
            @Override
            public void showToast(String str) {
                Toast.makeText(ModuleActivity.this, str, Toast.LENGTH_LONG).show();
            }

            @Override
            public void showToast(int res) {
                Toast.makeText(ModuleActivity.this, res + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,ModuleActivity2.class));
    }

}

