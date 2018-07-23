package com.whut.glxiang.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.glxiang.adapter.MyRecyclerAdapter;
import com.whut.glxiang.adapter.SpinerAdapter;
import com.whut.glxiang.R;
//import com.whut.glxiang.api.PushItemBeans;
import com.whut.glxiang.model.PushMessage;
import com.whut.glxiang.util.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements  SpinerAdapter.IOnItemSelectListener{
//    private RecyclerView recyclerView;
//    private List<String> mTitles;
//    private List<String> mContents;
    private MyRecyclerAdapter recycleAdapter;
   // private PushItemBeans pushItem;
    private PushMessage pushMessage;

    private List<String> ListType = new ArrayList<String>();  //类型列表
    private SpinerAdapter Adapter;
    private SpinerPopWindow SpinerPopWindow;
    private int width;
    private int type = 1 ;//用来识别安全简报中的1:国际民航和2:外军航空
    private int flag = 0;//用来判断点击是弹框中的哪一项
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;


    @BindView(R.id.tab_btn_1)
    RadioButton radioButton1;
    @BindView(R.id.tab_btn_2)
    RadioButton radioButton2;
    @BindView(R.id.tab_btn_3)
    RadioButton radioButton3;
    @BindView(R.id.tab_btn_4)
    RadioButton radioButton4;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.top_title)
    TextView hTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ButterKnife.bind(this);
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //屏幕宽度检测
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth()/4;
        initData(type);//更新recyclerview
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.whut.glxiang.adapter.MyRecyclerAdapter.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);//注册本地广播监听器
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
    private void initData(int recyclerType) {
        if (recyclerType==1){//国际民航
            recycleAdapter=new MyRecyclerAdapter(MainActivity.this,1);
        }else if (recyclerType==2){//外军航空
            recycleAdapter=new MyRecyclerAdapter(MainActivity.this,2);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
//		layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        //recyclerView.setItemAnimator(new DefaultItemAnimator());



        recycleAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this,"onLongClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this,"onClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
                pushMessage = recycleAdapter.getListItem(type,position);
                Intent intent1 = new Intent(MainActivity.this, DisplayActivity.class);
                intent1.putExtra("title", pushMessage.getTitle());
                intent1.putExtra("content", pushMessage.getContent());
                intent1.putExtra("messageType",type);
                startActivity(intent1);
//                pushItem = recycleAdapter.getList(type,position);
//                Intent intent1 = new Intent(MainActivity.this, DisplayActivity.class);
//                intent1.putExtra("title", pushItem.getTitle());
//                intent1.putExtra("content", pushItem.getContent());
//                intent1.putExtra("messageType",type);
//                startActivity(intent1);
            }
        });
    }
//    //设置PopWindow
//    private void showSpinWindow(RadioButton radioButton,int location) {
//        //设置mSpinerPopWindow显示的宽度
//        mSpinerPopWindow.setWidth(radioButton.getWidth());
//        //设置显示的位置在哪个控件的下方
//        switch (location){
//            case 0:
//                mSpinerPopWindow.showAtLocation(radioButton, Gravity.LEFT|Gravity.BOTTOM, 0,radioButton.getHeight());
//                break;
//            case 1:
//                mSpinerPopWindow.showAtLocation(radioButton, Gravity.LEFT|Gravity.BOTTOM, radioButton1.getWidth(),radioButton.getHeight());
//        }
//
//    }
    /**
     * SpinerPopWindow中的条目点击监听
     * @param pos
     */
    public void onItemClick(int pos) {
        switch (pos){
            case 0:
                if (flag==1) {
                    type = 1;
                    Toast.makeText(getApplicationContext(), "国际民航", Toast.LENGTH_SHORT).show();
                    hTitle.setText("国际民航");
                    initData(type);
                }else if (flag == 2){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全法规", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全法规");
                }else if (flag == 3){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "理论考试", Toast.LENGTH_SHORT).show();
                    hTitle.setText("理论考试");
                }else if (flag == 4){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全论坛");
                }
                break;
            case 1:
                if (flag==1) {
                    type = 2;
                    Toast.makeText(getApplicationContext(), "外军航空", Toast.LENGTH_SHORT).show();
                    hTitle.setText("外军航空");
                    initData(type);
                }else if (flag == 2){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全理论", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全理论");
                }else if (flag == 3){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "在线问答", Toast.LENGTH_SHORT).show();
                    hTitle.setText("在线问答");
                }else if (flag == 4){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全论坛");
                }
                break;
            case 2:
                if (flag==1) {
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全简报", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全简报");
                }else if (flag == 2){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全事故", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全事故");
                }else if (flag == 3){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全测试", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全测试");
                }else if (flag == 4){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全论坛");
                }
                break;
            case 3:
                if (flag==1) {
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全简报", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全简报");
                }else if (flag == 2){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全文化", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全文化");
                }else if (flag == 3){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全测试", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全测试");
                }else if (flag == 4){
                    type = 0;
                    Toast.makeText(getApplicationContext(), "安全论坛", Toast.LENGTH_SHORT).show();
                    hTitle.setText("安全论坛");
                }
                break;
                default:
                    break;
        }

    }

    @OnClick({R.id.tab_btn_1,R.id.tab_btn_2,R.id.tab_btn_3,R.id.tab_btn_4})
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
                default:
                    break;
        }
    }

    //广播接收器。负责推送消息的同步更新
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initData(type);
            Toast.makeText(getApplicationContext(), "有新的推送消息！", Toast.LENGTH_SHORT).show();
        }
    }

}
