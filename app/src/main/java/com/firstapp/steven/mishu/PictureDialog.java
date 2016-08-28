package com.firstapp.steven.mishu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by steven on 2016/8/24.
 */

public class PictureDialog extends Dialog {
    private TextView take_picture;
    private TextView pick_picture;
    private Context context;
    private File file;
    public PictureDialog(Context context,File file) {
        this(context,0);
        this.file=file;
    }



    protected PictureDialog(Context context, int themeResId) {
        super(context, R.style.color_dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.dialog_pick_photo,null);
      setContentView(view);
        take_picture= (TextView) view.findViewById(R.id.take_picture);
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                ((Activity)context).startActivityForResult(openCameraIntent,12345);
                dismiss();
            }
        });
        pick_picture= (TextView) view.findViewById(R.id.pick_picture);
        pick_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crop.pickImage((Activity) context);
                dismiss();
            }
        });

    }
}
