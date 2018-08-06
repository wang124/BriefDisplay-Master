package com.whut.glxiang.adapter;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.glxiang.R;
import com.whut.glxiang.application.MyApplication;
import com.whut.glxiang.model.PushMessage;
import com.whut.glxiang.model.PushMessageDao;

import java.util.List;


//import com.whut.glxiang.api.PushItemBeans;
//import com.whut.glxiang.util.PushDatabaseHelper;

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
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            imageView = (ImageView)itemView.findViewById(R.id.icon);
        }
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {
        final PushMessage pushMessage = pushMessages.get(position);
        holder.title.setText(pushMessage.getTitle());
        holder.content.setText(pushMessage.getContent());
        switch (pushMessage.getMessageType()){
            case 1:
                holder.imageView.setImageResource(R.mipmap.min);
                break;
            case 2:
                holder.imageView.setImageResource(R.mipmap.jun);
                break;
                default:
                    holder.imageView.setImageResource(R.mipmap.hang);
                    break;
        }

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
        pushMessageDao = MyApplication.getInstance().getmDaoSession().getPushMessageDao();
        //orderDesc按时间降序排列
        pushMessages = pushMessageDao.queryBuilder().where(PushMessageDao.Properties.MessageType.eq(dbType)).orderDesc(PushMessageDao.Properties.Receive_time).list();
    }

      public PushMessage getListItem(int listType, int position){
          PushMessageDao pushMessageDao = MyApplication.getInstance().getmDaoSession().getPushMessageDao();
          //orderDesc按时间降序排列
          List<PushMessage> pushMessages = pushMessageDao.queryBuilder().where(PushMessageDao.Properties.MessageType.eq(dbType)).orderDesc(PushMessageDao.Properties.Receive_time).list();
          return pushMessages.get(position);

      }


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