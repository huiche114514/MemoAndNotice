package com.yukiho.cn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.IOException;

public class LargeIconHelper {

    private static final int REQUEST_CODE_PICK_IMAGE = 1; // 图片选择请求码

    private final Context context;
    private final TextView picText;
    private Bitmap largeIconBitmap;

    public LargeIconHelper(Context context, TextView picText) {
        this.context = context;
        this.picText = picText;
    }

    public void handleImageSelection() {
        if (largeIconBitmap != null) {
            largeIconBitmap = null;
            picText.setText(R.string.pic_xz);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((MainActivity) context).startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    }

    public void handleImageResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == MainActivity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                largeIconBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
                picText.setText(R.string.pic_qc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getLargeIconBitmap() {
        return largeIconBitmap;
    }
}