package com.firstapp.steven.mishu.data;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.Calendar.LunarCalendar;
import com.firstapp.steven.mishu.EditDay.BirthdayActivity;
import com.firstapp.steven.mishu.EditDay.MyButton;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.UserIcon;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.soundcloud.android.crop.Crop;

import java.util.ArrayList;
import java.util.Calendar;

import cn.bmob.v3.b.I;

/**
 * Created by steven on 2016/8/21.
 */

public class BirthdayAda extends RecyclerView.Adapter<BirthdayAda.BirthdayViewHolder> {
    private ArrayList<Birthday> list;
    private Context context;
    private boolean isGone=true;
    private  ImageLoader loader;
    private ImportantAda.OnRecycleViewClick onRecycleViewClick;
private OnRecyclevireLongClick onRecyclevireLongClick;

public BirthdayAda(Context context,ArrayList<Birthday>list)
{
     loader= ImageLoader.getInstance();
    loader.init(ImageLoaderConfiguration.createDefault(context));
    this.context=context;
    this.list=list;
}
    @Override
    public BirthdayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.birthday,parent,false);
        BirthdayViewHolder viewHolder=new BirthdayViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BirthdayViewHolder holder, int position) {
final Birthday birthday=list.get(holder.getAdapterPosition());
        if(!birthday.getIcon_add().equals(""))
loader.displayImage(birthday.getIcon_add(),holder.icon);
        else if(birthday.getPhoto_id()!=-1)
            holder.icon.setImageDrawable(context.getResources().getDrawable(UserIcon.photos[birthday.getPhoto_id()]));
        holder.day_num.setText(birthday.getDay_num()+"");
        holder.birthday_name.setText(birthday.getName());
        holder.frameLayout.setVisibility(isGone?View.GONE:View.VISIBLE);
        holder.birthday_date.setText(birthday.getDate().get(Calendar.YEAR)+"年"+(birthday.getDate().get(Calendar.MONTH)+1)+"月"+birthday.getDate().get(Calendar.DAY_OF_MONTH)+"日");
if(birthday.getDay_num()==0) holder.birthday_content.setText("今天过生日哦");
else   holder.birthday_content.setText("后过生日");
holder.delete_button.setAnimEnd(new MyButton.OnMyButtomAnimEnd() {
    @Override
    public void onEnd() {
        if(holder.delete_button.isChecked()&&BirthdayActivity.removeList!=null)
        {

            if(!BirthdayActivity.removeList.contains(birthday))
                BirthdayActivity.removeList.add(birthday);

        }
        else if(!holder.delete_button.isChecked()){
           // Toast.makeText(context, "没有选中", Toast.LENGTH_SHORT).show();
            if(BirthdayActivity.removeList.contains(birthday))
                BirthdayActivity.removeList.remove(birthday);
        }
    }
});

holder.view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onRecycleViewClick.onClick(holder.getAdapterPosition());

    }
});
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onRecyclevireLongClick.onClick(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class BirthdayViewHolder extends RecyclerView.ViewHolder
    {
UserIcon icon;
        FrameLayout frameLayout;
        TextView birthday_date;
        TextView birthday_name;
        TextView day_num;
        TextView birthday_content;
        MyButton delete_button;
        View view;
        public BirthdayViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            icon= (UserIcon) itemView.findViewById(R.id.birthday_icon);
            birthday_date= (TextView) itemView.findViewById(R.id.birthday_date);
            birthday_name= (TextView) itemView.findViewById(R.id.birthday_name);
            day_num= (TextView) itemView.findViewById(R.id.day_num);
            birthday_content= (TextView) itemView.findViewById(R.id.birthday_content);
delete_button= (MyButton) itemView.findViewById(R.id.birthday_delete_button);
            frameLayout= (FrameLayout) itemView.findViewById(R.id.birthday_frame);
        }
    }

    public void setOnRecycleViewClick(ImportantAda.OnRecycleViewClick onRecycleViewClick) {
        this.onRecycleViewClick = onRecycleViewClick;
    }
    public interface OnRecyclevireLongClick{
        void onClick(int item);
    }

    public void setOnRecyclevireLongClick(OnRecyclevireLongClick onRecyclevireLongClick) {
        this.onRecyclevireLongClick = onRecyclevireLongClick;
    }

    public void setGone(boolean gone) {
        isGone = gone;
        notifyDataSetChanged();
    }
    public void save()
    {

    }
}
