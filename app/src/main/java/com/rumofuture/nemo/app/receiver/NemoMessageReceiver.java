package com.rumofuture.nemo.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NemoMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
//            //获取NotificationManager实例
//            NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            //实例化NotificationCompat.Builde并设置相关属性
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                    //设置小图标
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    //设置通知标题
//                    .setContentTitle("Nemo")
//                    //设置通知内容
//                    .setContentText(intent.getStringExtra("msg"));
//            //设置通知时间，默认为系统发出通知的时间，通常不用设置
//            //.setWhen(System.currentTimeMillis());
//            //通过builder.build()方法生成Notification对象,并发送通知,id=1
//            notifyManager.notify(1, builder.build());
//        }
    }
}
