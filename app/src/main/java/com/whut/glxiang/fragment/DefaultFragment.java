package com.whut.glxiang.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whut.glxiang.R;
import com.whut.glxiang.activity.DisplayActivity;
import com.whut.glxiang.adapter.MyRecyclerAdapter;
import com.whut.glxiang.model.PushMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;


public class DefaultFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Unbinder unbinder;
    private Context context;
    private MyRecyclerAdapter recycleAdapter;
    private PushMessage pushMessage;
    private int type = 1 ;//用来识别安全简报中的1:国际民航和2:外军航空
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler, null);
        context = getActivity();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
        unbinder = ButterKnife.bind(this,view);
        swipeRefreshLayout.setOnRefreshListener(this);
        initData(type);//更新recyclerview
        localBroadcastManager = LocalBroadcastManager.getInstance(context);//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.whut.glxiang.adapter.MyRecyclerAdapter.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);//注册本地广播监听器
        return view;
    }
    void initData(int recyclerType) {
        if (recyclerType==1){//国际民航
            recycleAdapter=new MyRecyclerAdapter(context,1);
        }else if (recyclerType==2){//外军航空
            recycleAdapter=new MyRecyclerAdapter(context,2);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
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
                Toast.makeText(context,"onLongClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(int position) {
                Toast.makeText(context,"onClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
                pushMessage = recycleAdapter.getListItem(type,position);
                Intent intent1 = new Intent(context, DisplayActivity.class);
                intent1.putExtra("title", pushMessage.getTitle());
                intent1.putExtra("content", pushMessage.getContent());
                intent1.putExtra("messageType",type);
                startActivity(intent1);

            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);

            }
        },500);
    }

    //广播接收器。负责推送消息的同步更新
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initData(type);
            Toast.makeText(context, "有新的推送消息！", Toast.LENGTH_SHORT).show();
        }
    }
//    /**
//     * onDestroyView中进行解绑操作
//     */
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
}
