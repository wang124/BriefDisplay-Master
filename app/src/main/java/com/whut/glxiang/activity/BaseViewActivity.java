package com.whut.glxiang.activity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.glxiang.R;
import com.whut.glxiang.adapter.SpinerAdapter;
import com.whut.glxiang.fragment.BriefFragment;
import com.whut.glxiang.fragment.BriefFragment2;
import com.whut.glxiang.fragment.DocumentFragment;
import com.whut.glxiang.util.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseViewActivity extends Activity implements  SpinerAdapter.IOnItemSelectListener {

    public LinearLayout llContent;
    private List<String> ListType = new ArrayList<String>();  //类型列表
    private SpinerAdapter Adapter;
    private SpinerPopWindow SpinerPopWindow;
    private int width;
    public boolean isExit = false;
    private int type = 1 ;//用来识别安全简报中的1:国际民航和2:外军航空
    private int flag = 0;//用来判断点击是弹框中的哪一项
    protected ViewPager mPager;
    protected RadioGroup pagerTab;
    protected List<Fragment> mTabs;
    FragmentManager fm;
    FragmentTransaction ft;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    @BindView(R.id.setting)
    TextView settingButton;
    @BindView(R.id.tab_btn_1)
    RadioButton radioButton1;
    @BindView(R.id.tab_btn_2)
    RadioButton radioButton2;
    @BindView(R.id.tab_btn_3)
    RadioButton radioButton3;
    @BindView(R.id.tab_btn_4)
    RadioButton radioButton4;
    @BindView(R.id.top_title)
    TextView hTitle;
//    @BindView(R.id.view_content)
//    LinearLayout linearLayout;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private ExitReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseview);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, new BriefFragment());
        ft.commit();
        //屏幕宽度检测
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth()/4;
        ButterKnife.bind(this);
        getPermission();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.whut.glxiang.activity.BaseViewActivity.LOCAL_BROADCAST");
        localReceiver = new ExitReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);//注册本地广播监听器
    }
    private void getPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(BaseViewActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(BaseViewActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(BaseViewActivity.this,
                        PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }

            ActivityCompat.requestPermissions(BaseViewActivity.this,
                    PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

        while ((ContextCompat.checkSelfPermission(BaseViewActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
        }
    }
    private void initPopWindow(List<String> ListTypei){

        //初始化上拉框数据
        Adapter = new SpinerAdapter(this, ListTypei);
        Adapter.refreshData(ListTypei, 0);
        SpinerPopWindow = new SpinerPopWindow(this);
        //初始化PopWindow
        SpinerPopWindow.setAdatper(Adapter);
        SpinerPopWindow.setItemListener(this);
        //设置mSpinerPopWindow显示的宽度
        SpinerPopWindow.setWidth(width);
    }
    /**
     * SpinerPopWindow中的条目点击监听
     * @param pos
     */
    public void onItemClick(int pos) {
        switch (pos){
            case 0:
                if (flag==1) {
                    type = 1;
                    //Toast.makeText(getApplicationContext(), "国际民航", Toast.LENGTH_SHORT).show();
                    hTitle.setText("国际民航");

                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new BriefFragment());
                    ft.commit();
                }else if (flag == 2){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "安全法规", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全法规");
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new DocumentFragment("法规-民用航空安全信息管理规定(2016年).pdf"));
                    ft.commit();

                }else if (flag == 3){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "理论考试", Toast.LENGTH_SHORT).show();
                    hTitle.setText("理论考试");
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new DocumentFragment("考试-航空安全管理手考试题.pdf"));
                    ft.commit();
                }else if (flag == 4){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全论坛");
                }
                break;
            case 1:
                if (flag==1) {
                    type = 2;
                    //Toast.makeText(getApplicationContext(), "外军航空", Toast.LENGTH_SHORT).show();
                    hTitle.setText("外军航空");
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new BriefFragment2());
                    ft.commit();
                }else if (flag == 2){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "安全理论", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全理论");
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new DocumentFragment("理论-全面质量管理TQM在民航安全管理体系SMS中的应用研究.pdf"));
                    ft.commit();

                }else if (flag == 3){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "在线问答", Toast.LENGTH_SHORT).show();
                    hTitle.setText("在线问答");
                }else if (flag == 4){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
                    //hTitle.setText("安全论坛");
                }
                break;
            case 2:
                if (flag==1) {
                    type = 0;
//                    Toast.makeText(getApplicationContext(), "安全简报", Toast.LENGTH_SHORT).show();
//                    hTitle.setText("安全简报");
                }else if (flag == 2){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "安全事故", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全事故");
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new DocumentFragment("新闻-B777爆胎后偏出跑道 泰航TG321事件简析.pdf"));
                    ft.commit();
                }else if (flag == 3){
//                    type = 0;
//                    Toast.makeText(getApplicationContext(), "安全测试", Toast.LENGTH_SHORT).show();
//                    hTitle.setText("安全测试");
                }else if (flag == 4){
                    type = 0;
//                    Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
//                    hTitle.setText("安全论坛");
                }
                break;
            case 3:
                if (flag==1) {
                    type = 0;
//                    Toast.makeText(getApplicationContext(), "安全简报", Toast.LENGTH_SHORT).show();
//                    hTitle.setText("安全简报");
                }else if (flag == 2){
                    type = 0;
                    //Toast.makeText(getApplicationContext(), "安全文化", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全文化");
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, new DocumentFragment("培训-SMS培训课件.pdf"));
                    ft.commit();
                }else if (flag == 3){
                    type = 0;
//                    Toast.makeText(getApplicationContext(), "安全测试", Toast.LENGTH_SHORT).show();
//                    hTitle.setText("安全测试");
                }else if (flag == 4){
                    type = 0;
//                    Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
//                    hTitle.setText("安全论坛");
                }
                break;
            default:
                break;
        }
    }
    @OnClick({R.id.tab_btn_1,R.id.tab_btn_2,R.id.tab_btn_3,R.id.tab_btn_4,R.id.setting})
    public void onClick(View view) {
        //下拉框初始化
        initPopWindow(ListType);
        switch (view.getId()) {
            case R.id.tab_btn_1:
                //安全简报
                flag = 1;
                ListType.clear();
                ListType.add("国际民航");
                ListType.add("外军航空");
                //ListType.add("我军航空");

                SpinerPopWindow.showAtLocation(radioButton1, Gravity.LEFT|Gravity.BOTTOM, 0,radioButton1.getHeight());
                break;
            case R.id.tab_btn_2:
                //安全教育
                flag = 2;
                ListType.clear();
                ListType.add("安全法规");
                ListType.add("安全理论");
                ListType.add("安全事故");
                ListType.add("安全文化");

                SpinerPopWindow.showAtLocation(radioButton2, Gravity.LEFT|Gravity.BOTTOM, width,radioButton2.getHeight());
                break;
            case R.id.tab_btn_3:
                //安全测试
                flag = 3;
                ListType.clear();
                ListType.add("理论考试");
                ListType.add("在线问答");

                SpinerPopWindow.showAtLocation(radioButton3, Gravity.LEFT|Gravity.BOTTOM, width*2,radioButton3.getHeight());
                break;
            case R.id.tab_btn_4:
                //安全论坛
                flag = 4;
                ListType.clear();
                ListType.add("    ");

                SpinerPopWindow.showAtLocation(radioButton4, Gravity.LEFT|Gravity.BOTTOM, width*3,radioButton4.getHeight());
                break;
            case R.id.setting:
                Intent intent = new Intent(BaseViewActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    //广播接收器。退出广播接收
    class ExitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BaseViewActivity.this.finish();
        }
    }

    //重写onKeyDown方法,
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            //调用exit()方法
            exit();
        }
        return false;
    }

    //被调用的exit()方法
    private void exit() {
        Timer timer;//声明一个定时器
        if (!isExit) {  //如果isExit为false,执行下面代码
            isExit = true;  //改变值为true
            Toast.makeText(BaseViewActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  //弹出提示
            timer = new Timer();  //得到定时器对象
            //执行定时任务,两秒内如果没有再次按下,把isExit值恢复为false,再次按下返回键时依然会进入if这段代码
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {//如果两秒内再次按下了返回键,这时isExit的值已经在第一次按下时赋值为true了,因此不会进入if后的代码,直接执行下面的代码
            finish();
        }
    }
}
