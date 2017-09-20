package com.rumofuture.nemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.model.source.remote.UserRemoteDataSource;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.rumofuture.nemo", appContext.getPackageName());
    }
}
