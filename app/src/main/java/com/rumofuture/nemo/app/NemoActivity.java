package com.rumofuture.nemo.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rumofuture.nemo.app.manager.NemoActivityManager;

/**
 * Created by WangZhenqi on 2017/2/12.
 */

public class NemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NemoActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NemoActivityManager.removeActivity(this);
    }
}
