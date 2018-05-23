package bpk.light.app_14_disks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class EightActivity extends AppCompatActivity {
ImageView carPhoto;
Bitmap btm;
Button btn_img_ok, btn_img_cancel;
String selImgPath;
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        selImgPath = intent.getStringExtra("selImgPath");
        setContentView(R.layout.activity_eight);
        btn_img_ok = findViewById(R.id.btn_img_ok);
        btn_img_cancel = findViewById(R.id.btn_img_cancel);
        carPhoto = findViewById(R.id.CarPhoto);
        if(selImgPath.equals("")) {
            try {
                btm = BitmapFactory.decodeFile(cardiskPath + "/" + "CarPhoto.jpg");
                carPhoto.setImageBitmap(btm);
                carPhoto.invalidate();
            } catch (Exception e) {
                Log.d("LightLog", "Err decodin");
            }
        }else{
            try{
                btm = BitmapFactory.decodeFile(selImgPath+".jpg");
            }catch (Exception e){
                try {
                    btm = BitmapFactory.decodeFile(selImgPath+".png");
                }catch (Exception e1){

                }
            }
            btm = BitmapFactory.decodeFile(selImgPath);
            Log.d("LightLog", selImgPath);
            carPhoto.setImageBitmap(btm);
            carPhoto.invalidate();
        }
        btn_img_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EightActivity.this, SevenActivity.class);
                intent.putExtra("id", 77);
                intent.putExtra("selImgPath","");
                startActivity(intent);
            }
        });
    }
}
