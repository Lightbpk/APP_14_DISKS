package bpk.light.app_14_disks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class FourActivity extends AppCompatActivity {
Button btn_selectImg, btn_camera;
String TAG = "LightLog";
File file = new File("/mnt/sdcard/pictures/CarPhoto.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        btn_selectImg = findViewById(R.id.btn_selectImg);
        btn_camera = findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                            Intent intent1 = new Intent(FourActivity.this, EightActivity.class);
                            startActivity(intent1);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }
    }
}
