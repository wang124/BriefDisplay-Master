package com.whut.glxiang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.glxiang.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends Activity {
    @BindView(R.id.exit)
    Button exitButton;
    @BindView(R.id.setting)
    TextView settingButton;
    @BindView(R.id.back)
    ImageView backButton;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        settingButton.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
    }
    @OnClick({R.id.exit,R.id.back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exit:
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
                localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
                Intent intent1 = new Intent("com.whut.glxiang.activity.BaseViewActivity.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent1);
                SettingActivity.this.finish();
                break;
            case R.id.back:
                SettingActivity.this.finish();
                break;
                default:
                    break;
        }

    }
}
