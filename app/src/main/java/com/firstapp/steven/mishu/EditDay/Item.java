package com.firstapp.steven.mishu.EditDay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.steven.mishu.R;

import java.util.Calendar;

/**
 * Created by steven on 2016/8/19.
 */

public class Item extends LinearLayout{
    private ImageView imageView;
    private TextView textView;
    private int id;
    private OnItemClickedListener listener;
    public Item(Context context) {
        this(context,null);
    }

    public Item(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Item(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageView=new ImageView(context);
        textView=new TextView(context);
        setOrientation(VERTICAL);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.Item);
        imageView.setImageDrawable(getResources().getDrawable(array.getResourceId(R.styleable.Item_image_src,R.mipmap.ic_launcher)));
        textView.setText(array.getString(R.styleable.Item_textview_text));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        textView.setGravity(Gravity.CENTER);
        id=array.getInt(R.styleable.Item_item_id,-1);
        addView(imageView);
        addView(textView);
        array.recycle();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) listener.onClick(Item.this);
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LayoutParams params= (LayoutParams) imageView.getLayoutParams();
        params.topMargin= (int) (10*getResources().getDisplayMetrics().density);

    }

    @Override
    public int getId() {
        return id;
    }
    public interface OnItemClickedListener{
        void onClick(Item item);
    }

    public void setListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
