package bpk.light.app_14_disks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeActivity extends AppCompatActivity {
    ListView listCars;
    EditText editText_search;
    Button button;
    ArrayAdapter<String> adapter, adapter2;
    final String[] carNames = new String[] { "bmw", "priora", "chaser"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        listCars = findViewById(R.id.ListCars);
        editText_search = findViewById(R.id.editText_search);
        button = findViewById(R.id.button);
        final ArrayList<String> carSearch = new ArrayList<>();
        final ArrayList<String> NullSearch = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, carSearch);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NullSearch);
        listCars.setAdapter(adapter);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                for(int i =0 ;i<carNames.length; i++) {
                    Log.d(getString(R.string.LL),"i ="+i);
                    if (((editText_search.getText()).toString()).equalsIgnoreCase(carNames[i])) {
                            if(!(carSearch.contains(editText_search.getText().toString()))) {
                                carSearch.add(0, editText_search.getText().toString());
                                Log.d(getString(R.string.LL), "add");
                                break;
                            }
                    }else{
                        carSearch.clear();
                        //listCars.setAdapter(adapter2);
                        Log.d(getString(R.string.LL), "cleared");
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        listCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                String carName = textView.getText().toString();
                Intent intent = new Intent(ThreeActivity.this, FiveActivity.class);
                intent.putExtra("carName",carName);
                startActivity(intent);
            }
        });
    }
}
