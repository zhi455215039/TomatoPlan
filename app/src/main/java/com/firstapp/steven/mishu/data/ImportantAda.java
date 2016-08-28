package com.firstapp.steven.mishu.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firstapp.steven.mishu.EditDay.ImportantActivity;
import com.firstapp.steven.mishu.EditDay.MyButton;
import com.firstapp.steven.mishu.R;
import com.firstapp.steven.mishu.UserIcon;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by steven on 2016/8/23.
 */

public class ImportantAda extends RecyclerView.Adapter<ImportantAda.ImportantViewHolder> {
private ArrayList<ImportantDay> list;
    private Context context;
    private boolean isGone=true;
    private OnRecycleViewClick onRecycleViewClick;
    private ImageLoader loader;
    private BirthdayAda.OnRecyclevireLongClick longClick;
public ImportantAda(Context context,ArrayList<ImportantDay>list)
{
    this.context=context;
    this.list=list;
    loader= ImageLoader.getInstance();
    loader.init(ImageLoaderConfiguration.createDefault(context));
}
    @Override
    public ImportantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.important_day,parent,false);
        ImportantViewHolder holder=new ImportantViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImportantViewHolder holder, int position) {
final ImportantDay day=list.get(holder.getLayoutPosition());
        holder.day_num.setText(day.getDay_num()+"");
        holder.name.setText(day.getName()+"");
if(!day.getIcon_add().equals(""))
        loader.displayImage(day.getIcon_add(),holder.icon);
else if(day.getPhoto_id()!=-1)
    holder.icon.setImageDrawable(context.getResources().getDrawable(UserIcon.photos[day.getPhoto_id()]));

        holder.frameLayout.setVisibility(isGone?View.GONE:View.VISIBLE);
        holder.date.setText(day.getDate().get(Calendar.YEAR)+"年"+(day.getDate().get(Calendar.MONTH)+1)+"月"+day.getDate().get(Calendar.DAY_OF_MONTH)+"日");
        holder.content.setText("后过"+(day.getYearCount())+"周年纪念");
        holder.day_count.setText("第"+day.getDayCount()+"天");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecycleViewClick.onClick(holder.getAdapterPosition());
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClick.onClick(holder.getAdapterPosition());
                return true;
            }
        });
        holder.myButton.setAnimEnd(new MyButton.OnMyButtomAnimEnd() {
            @Override
            public void onEnd() {
                if(holder.myButton.isChecked()&&ImportantActivity.removeList!=null)
                {
                    if(!ImportantActivity.removeList.contains(day))
                        ImportantActivity.removeList.add(day);
                }
                else if(ImportantActivity.removeList!=null)
                {
                    if(ImportantActivity.removeList.contains(day))
                        ImportantActivity.removeList.remove(day);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  ImportantViewHolder extends RecyclerView.ViewHolder{
UserIcon icon;
        FrameLayout frameLayout;
        MyButton myButton;
        TextView name;
        TextView date;
        TextView day_num;
        TextView content;
        TextView day_count;
        View view;
        public ImportantViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            myButton= (MyButton) itemView.findViewById(R.id.impor_delete_button);
            frameLayout= (FrameLayout) itemView.findViewById(R.id.impor_frame);
            icon= (UserIcon) itemView.findViewById(R.id._icon);
            name= (TextView) itemView.findViewById(R.id.important_name);
            date= (TextView) itemView.findViewById(R.id.important_date);
            day_num= (TextView) itemView.findViewById(R.id.importantday_num);
            content= (TextView) itemView.findViewById(R.id.importantday_content);
            day_count= (TextView) itemView.findViewById(R.id.impor_day_num);
        }
    }
    public interface OnRecycleViewClick
    {
        void onClick(int item);
    }

    public void setOnRecycleViewClick(OnRecycleViewClick onRecycleViewClick) {
        this.onRecycleViewClick = onRecycleViewClick;
    }

    public void setGone(boolean gone) {
        isGone = gone;
        notifyDataSetChanged();
    }

    public void setLongClick(BirthdayAda.OnRecyclevireLongClick longClick) {
        this.longClick = longClick;
    }
}
