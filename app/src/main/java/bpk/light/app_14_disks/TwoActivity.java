package bpk.light.app_14_disks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TwoActivity extends AppCompatActivity {
    Button btn_next_three, btn_next_four;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        btn_next_three = findViewById(R.id.btn_next_three);
        btn_next_four = findViewById(R.id.btn_next_four);
        btn_next_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoActivity.this, ThreeActivity.class);
                startActivity(intent);
            }
        });
        btn_next_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoActivity.this, FourActivity.class);
                startActivity(intent);
            }
        });
    }
}
