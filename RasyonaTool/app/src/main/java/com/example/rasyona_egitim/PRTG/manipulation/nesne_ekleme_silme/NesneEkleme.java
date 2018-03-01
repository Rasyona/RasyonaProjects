package com.example.rasyona_egitim.PRTG.manipulation.nesne_ekleme_silme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rasyona_egitim.PRTG.manipulation.NesneEklemeSilme;
import com.example.rasyona_egitim.PRTG.manipulation.nesne_ekleme_silme.nesne_ekleme.CihazKopyala;
import com.example.rasyona_egitim.PRTG.manipulation.nesne_ekleme_silme.nesne_ekleme.GrupKopyala;
import com.example.rasyona_egitim.PRTG.manipulation.nesne_ekleme_silme.nesne_ekleme.SensorKopyala;
import com.example.rasyona_egitim.rasyonatool.R;

public class NesneEkleme extends AppCompatActivity {

    TextView txt1;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nesne_ekleme);

        txt1 = (TextView) findViewById(R.id.textView1);
        spinner = (Spinner)findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int selected = (int) spinner.getSelectedItemPosition();

                switch (selected) {

                    case 0:

                        break;

                    case 1:

                        Intent intent1 = new Intent(NesneEkleme.this, GrupKopyala.class);
                        startActivity(intent1);

                        break;

                    case 2:

                        Intent intent2 = new Intent(NesneEkleme.this, CihazKopyala.class);
                        startActivity(intent2);

                        break;

                    case 3:

                        Intent intent3 = new Intent(NesneEkleme.this, SensorKopyala.class);
                        startActivity(intent3);

                        break;

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent(NesneEkleme.this, NesneEklemeSilme.class);
        startActivity(intent1);

    }
}
