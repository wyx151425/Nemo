package com.rumofuture.nemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.model.entity.User;

import cn.bmob.v3.BmobUser;

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
