package com.whut.glxiang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.whut.glxiang.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {
    private LocalBroadcastManager localBroadcastManager;
    @BindView(R.id.username_layout)
    LinearLayout usernameLayout;
    @BindView(R.id.password_layout)
    LinearLayout passwordLayout;
    @BindView(R.id.et_login_username)
    EditText username;
    @BindView(R.id.et_login_password)
    EditText userpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //设置登录框透明度,0~255
        usernameLayout.getBackground().setAlpha(150);
        passwordLayout.getBackground().setAlpha(150);
        username.setText("admin");
        userpassword.setText("123456");
    }
    @OnClick({R.id.btn_login_user_login,R.id.btn_login_reset,R.id.btn_login_user_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_user_login:
                login();
                break;
            case R.id.btn_login_reset:
                username.setText("");
                userpassword.setText("");
                break;
            case R.id.btn_login_user_exit:
                //发送广播形式关闭Activity

                localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
                Intent intent1 = new Intent("com.whut.glxiang.activity.BaseViewActivity.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent1);
                LoginActivity.this.finish();
                break;
                default:
                    break;
        }
    }
    private void login(){
        String name = username.getText().toString();
        String password = userpassword.getText().toString();
        if ((name.equals("admin"))&(password.equals("123456"))){
            Intent intent = new Intent(LoginActivity.this,BaseViewActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "帐号或密码错误 !", Toast.LENGTH_SHORT).show();
        }

    }
}
