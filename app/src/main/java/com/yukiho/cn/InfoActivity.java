package com.yukiho.cn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 关于界面
public class InfoActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);

        ImmersiveUtils.setImmersiveMode(this);

        findViewById(R.id.tgChat).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://pd.qq.com/s/91nwu8560?shareSource=5&businessType=9"));
            startActivity(intent);
        });

        findViewById(R.id.Github).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/huiche114514/MemoAndNotice"));
            startActivity(intent);
        });

        findViewById(R.id.ty).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://afdian.com/a/yixiaziquancile"));
            startActivity(intent);
        });
        
        findViewById(R.id.provision).setOnClickListener(view ->
                Toast.makeText(InfoActivity.this,R.string.egg2,Toast.LENGTH_SHORT).show());

        findViewById(R.id.Egg).setOnClickListener(view ->
                Toast.makeText(InfoActivity.this, R.string.egg1,Toast.LENGTH_SHORT).show());
    }
}