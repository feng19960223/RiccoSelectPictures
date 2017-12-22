package com.ricco.photo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import lib.ricco.photo.PhotoPicker;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String[] mPath; // 记录被选中的照片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1张
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.selectPic(MainActivity.this, new PhotoPicker.PicCallBack() {
                    @Override
                    public void onPicSelected(String[] path) {
                        Log.i(TAG, "onPicSelected: " + path[0]);
                    }
                });
            }
        });
        // 1张，剪裁
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.selectPic(MainActivity.this, 600, new PhotoPicker.PicCallBack() {
                    @Override
                    public void onPicSelected(String[] path) {
                        Log.i(TAG, "onPicSelected: " + path[0]);
                    }
                });
            }
        });
        // 指定张
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.selectPics(MainActivity.this, 9, new PhotoPicker.PicCallBack() {
                    @Override
                    public void onPicSelected(String[] path) {
                        Log.i(TAG, "onPicSelected: " + path[0]);
                    }
                });
            }
        });
        // 指定张，去重
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.selectPics(MainActivity.this, 9, mPath, new PhotoPicker.PicCallBack() {
                    @Override
                    public void onPicSelected(String[] path) {
                        mPath = path;
                        Log.i(TAG, "onPicSelected: " + path[0]);
                    }
                });
            }
        });
        // 无限张
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.selectPics(MainActivity.this, new PhotoPicker.PicCallBack() {
                    @Override
                    public void onPicSelected(String[] path) {
                        Log.i(TAG, "onPicSelected: " + path[0]);
                    }
                });
            }
        });
        // 无限张，去重
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.selectPics(MainActivity.this, mPath, new PhotoPicker.PicCallBack() {
                    @Override
                    public void onPicSelected(String[] path) {
                        Log.i(TAG, "onPicSelected: " + path[0]);
                    }
                });
            }
        });
    }
}
