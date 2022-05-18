package com.example.translation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class TextExtractUtil {
    public static String getText(Context context, ImageView imageView) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        Frame imageFrame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();
        StringBuilder imageText = new StringBuilder();
        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            imageText.append(textBlock.getValue());
        }
        return String.valueOf(imageText);
    }

    public static String getText(Context context, Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Frame imageFrame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();
        StringBuilder imageText = new StringBuilder();
        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            imageText.append(textBlock.getValue()).append("\n");
        }
        return String.valueOf(imageText);
    }
}
