package com.whut.glxiang.adapter;
import com.whut.glxiang.R;
//import com.whut.glxiang.api.PushItemBeans;
import com.whut.glxiang.application.MyApplication;
import com.whut.glxiang.model.PushMessage;
import com.whut.glxiang.model.PushMessageDao;
//import com.whut.glxiang.util.PushDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
//    private List<String> mtitles;
//    private List<String> mcontents;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;
    private int dbType = 0;

    //private List<PushItemBeans> pushItemBeansList = new ArrayList<>();
  //  private PushDatabaseHelper pdbHelper;
    //private List<PushItemBeans> dataList = new ArrayList<>();
 //   private List<PushItemBeans> selectList = new ArrayList<>();
    private IntentFilter intentFilter;
  //  private LocalBroadcastManager localBroadcastManager;
//    private LocalReceiver localReceiver;
    List<PushMessage> pushMessages;
    PushMessageDao pushMessageDao = null;

    /**
     * 推送标签参数
     */
    int id, iconCode, messageType;
    String title, content, linkAds, receive_time;
    boolean isRead;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener= (OnItemClickListener) onItemClickListener;
    }

    public MyRecyclerAdapter(Context context , int type){
        this.dbType = type;
        this.mContext=context;
        inflater=LayoutInflater.from(mContext);
        //界面停留在安全简报时才会刷新界面
        if (dbType != 0){
            initDBData();
        }
//        localBroadcastManager = LocalBroadcastManager.getInstance(context);//获取实例
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.whut.glxiang.adapter.MyRecyclerAdapter.LOCAL_BROADCAST");
//        localReceiver = new LocalReceiver();
//        localBroadcastManager.registerReceiver(localReceiver, intentFilter);//注册本地广播监听器
    }


    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_home,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends ViewHolder {
        TextView title;
        TextView content;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {
        final PushMessage pushMessage = pushMessages.get(position);
        holder.title.setText(pushMessage.getTitle());
        holder.content.setText(pushMessage.getContent());

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.itemView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return pushMessages.size();
    }

    /**
     * 查询数据库，并显示数据库中的数据
     */
    private void initDBData() {
//        pdbHelper = new PushDatabaseHelper(mContext, "message_record.db", null, 1);
//        SQLiteDatabase db = pdbHelper.getWritableDatabase();
//        Cursor cursor = null;
//        if (dbType==1){
//            //查询push_message表中的isRead为0数据
//            cursor = db.query("push_message", null, null, null,
//                    null, null, "receive_time desc");
//        }else if(dbType == 2) {
//            //查询push_message表中的isRead为0数据
//            cursor = db.query("push_message2", null, null, null,
//                    null, null, "receive_time desc");
//        }
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                //遍历cursor对象
//                id = cursor.getInt(cursor.getColumnIndex("id"));
//                iconCode = cursor.getInt(cursor.getColumnIndex("iconCode"));
//                title = cursor.getString(cursor.getColumnIndex("title"));
//                content = cursor.getString(cursor.getColumnIndex("content"));
//                pushItemBeansList.add(new PushItemBeans(id, iconCode, title, content, messageType, receive_time, linkAds, isRead, false, false));
//            }
//            cursor.close();
//        }
        pushMessageDao = MyApplication.getInstance().getmDaoSession().getPushMessageDao();
        //orderDesc按时间降序排列
        pushMessages = pushMessageDao.queryBuilder().where(PushMessageDao.Properties.MessageType.eq(dbType)).orderDesc(PushMessageDao.Properties.Receive_time).list();
    }

//    //广播接收器。负责推送消息的同步更新
//    class LocalReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            pushItemBeansList.clear();
//            initDBData();
//            Toast.makeText(mContext, "有新的推送消息！", Toast.LENGTH_SHORT).show();
//        }
//    }
      public PushMessage getListItem(int listType, int position){
          PushMessageDao pushMessageDao = MyApplication.getInstance().getmDaoSession().getPushMessageDao();
          //orderDesc按时间降序排列
          List<PushMessage> pushMessages = pushMessageDao.queryBuilder().where(PushMessageDao.Properties.MessageType.eq(dbType)).orderDesc(PushMessageDao.Properties.Receive_time).list();
          return pushMessages.get(position);

      }

//    public PushItemBeans getList(int listType,int position){
//        pdbHelper = new PushDatabaseHelper(mContext, "message_record.db", null, 1);
//        SQLiteDatabase db = pdbHelper.getWritableDatabase();
//        Cursor cursor = null;
//        if (listType==1){
//            cursor = db.query("push_message", null, null, null,
//                    null, null, "receive_time desc");
//        }else if(listType == 2) {
//            cursor = db.query("push_message2", null, null, null,
//                    null, null, "receive_time desc");
//        }
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                //遍历cursor对象
//                id = cursor.getInt(cursor.getColumnIndex("id"));
//                iconCode = cursor.getInt(cursor.getColumnIndex("iconCode"));
//                title = cursor.getString(cursor.getColumnIndex("title"));
//                content = cursor.getString(cursor.getColumnIndex("content"));
//                pushItemBeansList.add(new PushItemBeans(id, iconCode, title, content, messageType, receive_time, linkAds, isRead, false, false));
//            }
//            cursor.close();
//        }
//        return pushItemBeansList.get(position);
//
//    }
    public void addData(int position) {
//        mDatas.add(position, "Insert item");
//        notifyItemInserted(position);
//        notifyItemRangeChanged(position,mDatas.size());
    }

    public void removeData(int position) {
//        mDatas.remove(position);
//        notifyDataSetChanged();
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,mDatas.size());
    }
}