package cn.edu.gdmec.android.mobileguard.m3communicationguard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.entity.BlackContactInfo;

/**
 * Created by zly11 on 2017/10/30.
 */

public class BlackContactAdapter extends BaseAdapter {

    private List<BlackContactInfo> contactInfos;
    private Context context;
    private BlackNumberDao dao;
    private BlackConactCallBack callBack;

    class ViewHolder{
        TextView mNameTV;
        TextView mModeTV;
        View mContactImgv;
        View mDeleteView;

    }

    public BlackContactAdapter(List<BlackContactInfo> s,Context context){
        super();
        this.contactInfos = s;
        this.context = context;
        dao = new BlackNumberDao(context);
    }


    public interface BlackConactCallBack{
        void DataSizeChanged();
    }

    public void setCallBack(BlackConactCallBack callBack){
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
       ViewHolder holder = null;
        if (view == null){
            view = View.inflate(context, R.layout.item_list_blackcontact,null);
            holder = new ViewHolder();
            holder.mNameTV = (TextView)view.findViewById(R.id.tv_black_name);
            holder.mModeTV = (TextView)view.findViewById(R.id.tv_black_mode);
            holder.mDeleteView = (View)view.findViewById(R.id.view_black_delete);
            holder.mContactImgv = (View)view.findViewById(R.id.view_black_icon);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }
        //填充视图数据
        holder.mNameTV.setText(contactInfos.get(position).contactName+"<"+contactInfos.get(position).phoneNumber+">");
        holder.mModeTV.setText(contactInfos.get(position).getContactName(contactInfos.get(position).mode));
        holder.mNameTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
        holder.mModeTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
        holder.mContactImgv.setBackgroundResource(R.drawable.brightpurple_contact_icon);
        holder.mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用函数删除数据库中该条记录
                boolean delete = dao.delete(contactInfos.get(position));
                if (delete){
                    //删除arraylist中的改条记录
                    contactInfos.remove(contactInfos.get(position));
                    BlackContactAdapter.this.notifyDataSetChanged();
                    //如果全部记录都删空了
                    if (dao.getTotalNumber() == 0){
                        callBack.DataSizeChanged();
                    }else{
                        Toast.makeText(context,"删除失败",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }
}
