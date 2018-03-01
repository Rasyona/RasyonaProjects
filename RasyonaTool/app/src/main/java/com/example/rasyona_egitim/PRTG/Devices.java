package com.example.rasyona_egitim.PRTG;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rasyona_egitim.PRTG.devices.Device1;
import com.example.rasyona_egitim.rasyonatool.MainActivity;
import com.example.rasyona_egitim.rasyonatool.R;

import java.util.ArrayList;

public class Devices extends AppCompatActivity {

    ArrayList<String> devices = new ArrayList<>();
    ListView deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        Intent intent = getIntent();
        devices = intent.getStringArrayListExtra("devices");

        deviceList = (ListView) findViewById(R.id.deviceList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Devices.this, android.R.layout.simple_list_item_1, android.R.id.text1, devices);
        deviceList.setAdapter(arrayAdapter);

        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int selected = (int) deviceList.getSelectedItemPosition();

                switch (selected) {

                    case 0:

                        Intent intent = new Intent(Devices.this, Device1.class);
                        startActivity(intent);

                        break;

                    case 1:



                        break;

                    case 2:



                        break;

                    case 3:



                        break;

                    case 4:



                        break;

                    case 5:



                        break;

                    case 6:



                        break;

                    case 7:



                        break;

                    case 8:



                        break;

                    case 9:



                        break;

                }

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_devices, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.backToMainPage) {

            Intent intent = new Intent(Devices.this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}






