package com.whut.glxiang.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.whut.glxiang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.whut.glxiang.util.PushDatabaseHelper;
public class DisplayActivity extends Activity implements View.OnTouchListener {
    private String title;
    private String content;
    private String url=null;
    private GestureDetector mGestureDetector;
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
    @BindView(R.id.progressbar)
    ProgressBar progressBar;


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
        url = intent.getStringExtra("linkAds");
        switch (type) {
            case 1:
                if(url==null){
                    nTitle.setText(title);
                    nContent.setVisibility(View.VISIBLE);
                    nContent.setText(content);
                }else {
                    nContent.setVisibility(View.GONE);
                    nTitle.setText(title);
                    webView.setVisibility(View.VISIBLE);

                    webView.setWebViewClient(webViewClient);
                    WebSettings settings = webView.getSettings();
                    webView.getSettings().setJavaScriptEnabled(true);
                    String ua = webView.getSettings().getUserAgentString();
                    System.out.print("+++++++++"+ua);
                    webView.getSettings().setUserAgentString(ua);
                   // webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                    webView.loadUrl(url);//加载url
                    mGestureDetector=new GestureDetector(new gestureListener());
                    webView.setOnTouchListener(this);
                }

                break;
            case 2:
                if(url==null){
                    nTitle.setText(title);
                    nContent.setVisibility(View.VISIBLE);
                    nContent.setText(content);
                }else {
                    nContent.setVisibility(View.GONE);
                    nTitle.setText(title);
                    webView.setVisibility(View.VISIBLE);
                    WebSettings settings = webView.getSettings();
                    String ua = webView.getSettings().getUserAgentString();
                    System.out.print("+++++++++"+ua);
                    settings.setUserAgentString(ua);
                    webView.setWebViewClient(webViewClient);
                    webView.setWebChromeClient(webChromeClient);
                    webView.loadUrl(url);//加载url
                    mGestureDetector=new GestureDetector(new gestureListener());
                    webView.setOnTouchListener(this);
                }
            case 3:

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
//WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient=new WebChromeClient(){
    //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
    @Override
    public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
//        AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
//        localBuilder.setMessage(message).setPositiveButton("确定",null);
//        localBuilder.setCancelable(false);
//        localBuilder.create().show();

        //注意:
        //必须要这一句代码:result.confirm()表示:
        //处理结果为确定状态同时唤醒WebCore线程
        //否则不能继续点击按钮
        result.confirm();
        return true;
    }

    //获取网页标题
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Log.i("ansen","网页标题:"+title);
    }

    //加载进度回调
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        progressBar.setProgress(newProgress);
    }
};
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class gestureListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
