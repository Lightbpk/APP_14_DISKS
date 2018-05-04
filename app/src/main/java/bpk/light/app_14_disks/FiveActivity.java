package bpk.light.app_14_disks;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.ThinDownloadManager;

public class FiveActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        Intent intent = getIntent();
        textView = findViewById(R.id.textView);
        String carName = intent.getStringExtra("carName");
        Log.d(getString(R.string.LL), carName);
        textView.setText(carName);
    }
}
