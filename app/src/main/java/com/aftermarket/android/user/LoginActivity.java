package com.aftermarket.android.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.aftermarket.android.BaseActivity;
import com.aftermarket.android.MainActivity;
import com.aftermarket.android.R;
import com.aftermarket.android.db.User;
import com.aftermarket.android.util.LoginService;
import com.google.gson.JsonObject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity {
    private Button login;
    private Button register;

    private EditText et1;//账号输入框
    private EditText et2;//密码

    private CheckBox remember_key;//记住密码
    private CheckBox automatic_login;//自动登陆
    private SharedPreferences prefs;

    private String userNameValue;
    private String passwordValue;

    private Context mContext = this;

    private Boolean rem_isCheck = false;
    private Boolean atuo_isCheck = false;
    private User user;

    private IBinder mBinder;
    private Boolean isInit;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*//打开Preferences，名称为userInfo，如果存在则打开它，否则创建新的Preferences
        //Context.MODE_PRIVATE：指定该SharedPreferences数据只能被本应用程序读、写//Context.MODE_WORLD_READABLE：指定该SharedPreferences数据能被其他应用程序读，但不能写
        //        //Context.MODE_WORLD_WRITEABLE：指定该SharedPreferences数据能被其他应用程序读写。*/
        prefs = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        //实例化
        login = (Button) findViewById(R.id.login_button);
        remember_key = (CheckBox) findViewById(R.id.remember_key);
        automatic_login = (CheckBox) findViewById(R.id.automatic_login);
        et1 = (EditText) findViewById(R.id.login_user);
        et2 = (EditText) findViewById(R.id.login_password);

        rem_isCheck = remember_key.isChecked();
        atuo_isCheck = automatic_login.isChecked();
        remember_key.setChecked(true); //设置初始记住密码

        //判断记住密码多选框的状态
        //key：检索key
        //defValue：如果存在这个值就返回值,不存在就返回defvalue
        if (prefs.getBoolean("rem_isCheck", false)) {
            remember_key.setChecked(true);
            et1.setText(prefs.getString("USER_NAME", ""));
            et2.setText(prefs.getString("PASSWORD", ""));
            Log.e("记住密码框", ",\"自动恢复保存的账号密码\" ");


            /*自动登陆多选框*/
            if (prefs.getBoolean("auto_isCheck", false)) {
                automatic_login.setChecked(true);
                //跳转(后面加上服务器判断）
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                Toast toast1 = Toast.makeText(getApplicationContext(), "已自动登录", Toast.LENGTH_SHORT);
                Log.e("登录框", "onCreate: 自动登录");
            }
        }

        /*登录按钮监听*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = et1.getText().toString();
                passwordValue = et2.getText().toString();
                //假设账号密码
//                if (userNameValue.equals("1234") && passwordValue.equals("123")) {
//                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                login();
                /*选中框就保存信息*/
                if (rem_isCheck = remember_key.isChecked()) {
                    atuo_isCheck = automatic_login.isChecked();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("USER_NAME", userNameValue);
                    editor.putString("PASSWORD", passwordValue);
                    editor.putBoolean("rem_isCheck", rem_isCheck);
                    editor.putBoolean("auto_isCheck", atuo_isCheck);
                    editor.commit();

                    Log.e("选中保存密码", "账号" + userNameValue
                            + "\n密码：" + passwordValue + "\n是否记住密码" + rem_isCheck);
                    editor.commit();
                }
//                    //跳转
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    LoginActivity.this.startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新登录",Toast.LENGTH_LONG).show();
//                }
            }
        });

    }

    private void login() {
        String username = et1.getText().toString();
        String password = et2.getText().toString();
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        new LoginService(this).userLogin(new Observer<JsonObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(JsonObject jsonObject) {
                if (jsonObject.get("welcome_msg").getAsBoolean()) {   //服务器返回welcome_msg和uid
                    user.setUid(jsonObject.get("uid").getAsString());
                    Log.e("chenggolng", "welcome_msg" + jsonObject.get("welcom_msg"));

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    //testSession


                }else {
                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }


            }

            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }

        }, user);

    }
}
