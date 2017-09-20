package com.rumofuture.nemo;

import android.util.Log;

import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.model.source.remote.UserRemoteDataSource;

import org.junit.Test;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}