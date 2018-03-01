package com.example.rasyona_egitim.PRTG;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rasyona_egitim.PRTG.manipulation.BildirimiTestEtme;
import com.example.rasyona_egitim.PRTG.manipulation.DurdurmaDevamEttirme;
import com.example.rasyona_egitim.PRTG.manipulation.HatayiKabulEtme;
import com.example.rasyona_egitim.PRTG.manipulation.LokasyonuAyarlama;
import com.example.rasyona_egitim.PRTG.manipulation.NesneAyarlariniDegistirme;
import com.example.rasyona_egitim.PRTG.manipulation.NesneEklemeSilme;
import com.example.rasyona_egitim.PRTG.manipulation.NesneleriYenidenKonumlandirma;
import com.example.rasyona_egitim.PRTG.manipulation.OtoKesfetmeyiTetikleme;
import com.example.rasyona_egitim.PRTG.manipulation.RaporaNesneEkleme;
import com.example.rasyona_egitim.rasyonatool.MainActivity;
import com.example.rasyona_egitim.rasyonatool.R;

public class Manipulation extends AppCompatActivity {

    TextView txt;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulation);

        txt = (TextView)findViewById(R.id.textView1);
        spinner = (Spinner)findViewById(R.id.spinner);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int selected = (int) spinner.getSelectedItemPosition();

                switch (selected) {

                    case 0:

                        break;

                    case 1:

                        Intent intent1 = new Intent(Manipulation.this, NesneAyarlariniDegistirme.class);
                        startActivity(intent1);

                        break;

                    case 2:

                        Intent intent2 = new Intent(Manipulation.this, DurdurmaDevamEttirme.class);
                        startActivity(intent2);

                        break;

                    case 3:

                        Intent intent3 = new Intent(Manipulation.this, HatayiKabulEtme.class);
                        startActivity(intent3);

                        break;

                    case 4:

                        Intent intent4 = new Intent(Manipulation.this, OtoKesfetmeyiTetikleme.class);
                        startActivity(intent4);

                        break;

                    case 5:

                        Intent intent5 = new Intent(Manipulation.this, NesneleriYenidenKonumlandirma.class);
                        startActivity(intent5);

                        break;

                    case 6:

                        Intent intent6 = new Intent(Manipulation.this, RaporaNesneEkleme.class);
                        startActivity(intent6);

                        break;

                    case 7:

                        Intent intent7 = new Intent(Manipulation.this, BildirimiTestEtme.class);
                        startActivity(intent7);

                        break;

                    case 8:

                        Intent intent8 = new Intent(Manipulation.this, NesneEklemeSilme.class);
                        startActivity(intent8);

                        break;

                    case 9:

                        Intent intent9 = new Intent(Manipulation.this, LokasyonuAyarlama.class);
                        startActivity(intent9);

                        break;

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent(Manipulation.this, MainActivity.class);
        startActivity(intent1);

    }
}