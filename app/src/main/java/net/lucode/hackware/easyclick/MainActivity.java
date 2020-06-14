package net.lucode.hackware.easyclick;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EasyClick.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtils.toast(this, "你发挥是佛的三分");
        ToastUtils.toast(this, "你发挥是佛的三分");
    }

    public void showToast(View view) {
        Log.d("haha", "点击");
    }
}
