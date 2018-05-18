package bpk.light.app_14_disks;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ThinDownloadManager downloadManager;
    DownloadRequest downloadRequest;
    Uri DownloadUri, DestinatoinUri;
    Button btn_next_two;
    Button btn_reload;
    String[] carBase;
    String[] diskBase;
    String fileName;
    File cardiskPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carBase = getResources().getStringArray(R.array.baseLinks);
        diskBase = getResources().getStringArray(R.array.diskBase);

        for(int i=0; i< carBase.length;i++){
            Log.d(getString(R.string.LL), "Link "+i+ " "+carBase[i]);
        }
        btn_next_two = findViewById(R.id.btn_next_two);
        btn_reload = findViewById(R.id.btn_reload);
        btn_next_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                startActivity(intent);
            }
        });
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardiskPath.delete();
                for(int i = 0 ; i < carBase.length;i++ ){
                    loadNext(i,carBase);
                }
                for(int j = 0 ; j < diskBase.length;j++ ){
                    loadNext(j,diskBase);
                }
                //downloadManager.release();
                //reload base code
            }
        });
    }
    public void loadNext(int n, String baseLinks[]){
        downloadManager = new ThinDownloadManager(5);
        DownloadUri = Uri.parse(baseLinks[n]);
        if(baseLinks[n].endsWith("config.txt")) {
            fileName = "/cardisk/conf"+baseLinks[n].substring(baseLinks[n].length()-4,baseLinks[n].length());
        }
        else if(baseLinks[n].endsWith("Disk.txt")){
             fileName = "/cardisk/confDisk"+baseLinks[n].substring(baseLinks[n].length()-4,baseLinks[n].length());
        }
        else if((baseLinks[n].endsWith(".png"))){
            fileName = "/cardisk/m"+n+baseLinks[n].substring(baseLinks[n].length()-4,baseLinks[n].length());
        }
        else {
            fileName = "/cardisk/img"+n+baseLinks[n].substring(baseLinks[n].length()-4,baseLinks[n].length());
        }

        Log.d(getString(R.string.LL),fileName);
        DestinatoinUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + fileName);
        try {
            downloadRequest = new DownloadRequest(DownloadUri).setDestinationURI(DestinatoinUri);
            downloadManager.add(downloadRequest);
        }catch (Exception e){
            Log.d(getString(R.string.LL), "DownloadErr");
        }
        downloadRequest.setStatusListener(new DownloadStatusListenerV1() {
            @Override
            public void onDownloadComplete(DownloadRequest downloadRequest) {
                Log.d(getString(R.string.LL), "load complite");
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.tBaseReload), Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            @Override
            public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                Log.d(getString(R.string.LL), "load fail code - "+errorCode +" Err: "+errorMessage);
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.tBaseUpFail), Toast.LENGTH_SHORT);
                toast.show();
                Log.d(getString(R.string.LL)," Reload leter");
                return;
            }
            @Override
            public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

            }
        });
    }
}
