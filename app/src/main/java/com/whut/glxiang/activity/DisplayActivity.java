package com.whut.glxiang.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whut.glxiang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.whut.glxiang.util.PushDatabaseHelper;
public class DisplayActivity extends Activity {
    private String title;
    private String content;
    private ProgressBar progressBar;
    //private PushDatabaseHelper pdbHelper;
    @BindView(R.id.top_title)
    TextView nTitle;
    @BindView(R.id.content)
    TextView nContent;
    @BindView(R.id.urlview)
    WebView webView;
    @BindView(R.id.back)
    ImageView backView;
    @BindView(R.id.setting)
    TextView setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_display);
        ButterKnife.bind(this);
        backView.setVisibility(View.VISIBLE);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        setting.setVisibility(View.GONE);
        backView.setOnClickListener(onClickListener);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
       // webView = (WebView) findViewById(R.id.urlview);
       // progressBar = (ProgressBar) findViewById(R.id.progressbar);//进度条
        int type = intent.getIntExtra("messageType", 0);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        switch (type) {
            case 1:
                nTitle.setText(title);
                nContent.setVisibility(View.VISIBLE);
                nContent.setText(content);
                break;
            case 2:
                nTitle.setText(title);
                nContent.setVisibility(View.VISIBLE);
                nContent.setText(content);
                break;
            case 3:
                String title2 = intent.getStringExtra("title");
                nTitle.setText(title2);
                webView.setVisibility(View.VISIBLE);
                String url = intent.getStringExtra("linkAds");
                webView.setWebViewClient(webViewClient);
                webView.loadUrl(url);//加载url
                break;
            default:
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //loadAPPMark();
    }

//    private void loadAPPMark() {
//        int unRead_Num = 0;
//        pdbHelper = new PushDatabaseHelper(this, "message_record.db", null, 1);
//        SQLiteDatabase db = pdbHelper.getWritableDatabase();
//        Cursor cursor = db.query("push_message", null, "isRead=?", new String[]{"0"}, null, null, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                unRead_Num++;
//            }
//            cursor.close();
//            ShortcutBadger.applyCount(this, unRead_Num); //更新APP图标消息数量
//        }
//        db.close();
//    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressBar.setVisibility(View.GONE);
            nContent.setVisibility(View.GONE);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressBar.setVisibility(View.VISIBLE);
            nContent.setText("加载中...");
            nContent.setVisibility(View.VISIBLE);
        }
    };
}
