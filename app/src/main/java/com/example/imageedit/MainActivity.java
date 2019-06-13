package com.example.imageedit;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import static com.example.imageedit.MyEditText.RC_CHOOSE_PHOTO;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button btn1;
    MyEditText editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.myButton);
        btn1 = findViewById(R.id.myButton1);
        editor = findViewById(R.id.myEdit);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                editor.insertDrawable(R.drawable.xs);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetImage.class);
                startActivity(intent);
            }
        });
    }
    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }

}
