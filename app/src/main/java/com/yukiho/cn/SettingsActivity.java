package com.yukiho.cn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);

        ImmersiveUtils.setImmersiveMode(this);

        findViewById(R.id.tgChat).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://t.me/+tP3wEh5cNsUzODI1"));
            startActivity(intent);
        });

        findViewById(R.id.Egg).setOnClickListener(view ->
                Toast.makeText(SettingsActivity.this, R.string.egg,Toast.LENGTH_SHORT).show());
    }
}