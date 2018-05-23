package bpk.light.app_14_disks;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
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
String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
File file = new File(cardiskPath+"/CarPhoto.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        btn_selectImg = findViewById(R.id.btn_selectImg);
        btn_camera = findViewById(R.id.btn_camera);
        btn_selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            }
        });
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
                intent1.putExtra("selImgPath", "");
                            startActivity(intent1);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }else if(requestCode == 2){
            Uri chosenImageUri = intent.getData();
            Log.d(TAG, "Uri "+chosenImageUri);
            Log.d(TAG, "Uri encoded "+chosenImageUri.getEncodedPath());
            final String filePath = chosenImageUri.getEncodedPath();
            Log.d(TAG, "filePath "+filePath);
            Intent intent1 = new Intent(FourActivity.this, EightActivity.class);
            intent1.putExtra("selImgPath", filePath);
            startActivity(intent1);
        }
    }
}
