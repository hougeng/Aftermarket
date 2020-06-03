package com.aftermarket.android.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.aftermarket.android.ActivityCollector;
import com.aftermarket.android.BaseActivity;
import com.aftermarket.android.R;
import com.aftermarket.android.user.LoginActivity;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

public class DialogLogout extends AbsBaseCircleDialog implements View.OnClickListener {

    public static DialogLogout getInstance() {
        DialogLogout dialogFragment = new DialogLogout();
        dialogFragment.setCancelable(false);
        dialogFragment.setCanceledOnTouchOutside(false);
        dialogFragment.bottomFull();
        return dialogFragment;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.dialog_logout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        view.findViewById(R.id.but_cancle).setOnClickListener(this);
        view.findViewById(R.id.logout_ok).setOnClickListener(this);
        view.findViewById(R.id.logout_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout_ok) {
            //注销逻辑
//            Intent intent = new Intent("com.aftermarket.android.FORCE_OFFLINE");
//            intent.setComponent(new ComponentName("com.ftermarket.android","com.aftermarket.android.BaseActivity"));
//            //在fragment中使用broadcast
//            intent.putExtra("test","测试数据");
//            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            Toast.makeText(getContext(),"发送广播",Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//在当前上下文建立对话框
            builder.setTitle("警告");
            builder.setMessage("你已经被强制下线，请重新登录");
            builder.setCancelable(false);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll(); //销毁所有活动
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent); //重新启动LoginActivity
                }
            });
            builder.show();
        } else {
            dismiss();
        }
    }

}
