package com.rumofuture.nemo.app.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.rumofuture.nemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import cn.bmob.push.PushConstants;

public class NemoNotifyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("msg");
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(message);
            if (Objects.equals(intent.getAction(), PushConstants.ACTION_MESSAGE)) {
                //获取NotificationManager实例
                NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                //实例化NotificationCompat.Builde并设置相关属性
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        //设置小图标
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //设置通知标题
                        .setContentTitle("Nemo")
                        //设置通知内容
                        .setContentText(jsonObject.getString("message"));
                //设置通知时间，默认为系统发出通知的时间，通常不用设置
                //.setWhen(System.currentTimeMillis());
                //通过builder.build()方法生成Notification对象,并发送通知,id=1
                if (null != notifyManager) {
                    notifyManager.notify(1, builder.build());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
