package com.rumofuture.nemo.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;

/**
 * Created by WangZhenqi on 2017/9/21.
 */

public class NemoStartActivity extends NemoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_start);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NemoMainActivity.actionStart(NemoStartActivity.this, false);
                finish();
            }
        }, 1250);
    }
}
