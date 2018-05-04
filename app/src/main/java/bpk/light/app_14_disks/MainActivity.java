package bpk.light.app_14_disks;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

public class MainActivity extends AppCompatActivity {
    ThinDownloadManager downloadManager;
    DownloadRequest downloadRequest;
    Uri DownloadUri, DestinatoinUri;
    Button btn_next_two;
    Button btn_reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_next_two = findViewById(R.id.btn_next_two);
        btn_reload = findViewById(R.id.btn_reload);
        btn_next_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                startActivity(intent);
            }
        });
        DestinatoinUri = Uri.parse("http://www.picshare.ru/uploads/180504/Z51M4a52at.jpg");
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager = new ThinDownloadManager(5);
                DestinatoinUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cardisk/img.png");
                downloadRequest = new DownloadRequest(DownloadUri).setDestinationURI(DestinatoinUri);
                downloadManager.add(downloadRequest);
                downloadRequest.setStatusListener(new DownloadStatusListenerV1() {
                    @Override
                    public void onDownloadComplete(DownloadRequest downloadRequest) {
                        Log.d(getString(R.string.LL), "load complite");
                    }

                    @Override
                    public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                        Log.d(getString(R.string.LL), "load fail");
                    }

                    @Override
                    public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

                    }
                });
                //reload base code
            }
        });
    }
}
