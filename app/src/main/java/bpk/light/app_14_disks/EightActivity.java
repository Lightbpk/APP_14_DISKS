package bpk.light.app_14_disks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class EightActivity extends AppCompatActivity {
ImageView carPhoto;
Bitmap btm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);
        carPhoto = findViewById(R.id.CarPhoto);
        try{
            btm = BitmapFactory.decodeFile("/mnt/sdcard/pictures/CarPhoto.jpg");
            carPhoto.setImageBitmap(btm);
            carPhoto.invalidate();
        }catch (Exception e){
            Log.d("LightLog", "Err decodin");
        }
    }
}
