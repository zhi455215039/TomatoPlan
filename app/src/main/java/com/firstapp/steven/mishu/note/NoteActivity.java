package com.firstapp.steven.mishu.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firstapp.steven.mishu.R;

/**
 * Created by steven on 2016/8/28.
 */

public class NoteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        StatusBarCompat.compat(this, 0xACCD00AB);
    }
}
