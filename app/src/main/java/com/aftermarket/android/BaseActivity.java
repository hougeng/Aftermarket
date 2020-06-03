package com.aftermarket.android;

/*所有活动的父类*/
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aftermarket.android.user.LoginActivity;

public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver receiver; //在此父类注册一个广播器用于强制下线

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

/*注册广播接收器，保证处于栈顶*/
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.aftermarket.android.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver, intentFilter);

    }

    /*注销广播接收器，保证处于栈顶*/

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver !=null) {
            unregisterReceiver(receiver);
            receiver=null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            String message = intent.getStringExtra("test");
            Log.i("MyBroadCastReciver"," -- 接收到的消息 -- = "+message);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);//在当前上下文建立对话框
            builder.setTitle("警告");
            builder.setMessage("你已经被强制下线，请重新登录");
            builder.setCancelable(false);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll(); //销毁所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent); //重新启动LoginActivity
                }
            });
            builder.show();

        }
    }
}
