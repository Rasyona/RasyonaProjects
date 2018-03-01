package com.example.rasyona_egitim.PRTG.manipulation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rasyona_egitim.PRTG.Manipulation;
import com.example.rasyona_egitim.PRTG.manipulation.durdurma_devam_ettirme.BelirliSureDurdur;
import com.example.rasyona_egitim.PRTG.manipulation.durdurma_devam_ettirme.NesneDevamEttir;
import com.example.rasyona_egitim.PRTG.manipulation.durdurma_devam_ettirme.NesneDurdur;
import com.example.rasyona_egitim.PRTG.manipulation.durdurma_devam_ettirme.NesneHataSimule;
import com.example.rasyona_egitim.rasyonatool.R;

public class DurdurmaDevamEttirme extends AppCompatActivity {

    TextView txt;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durdurma_devam_ettirme);

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

                        Intent intent1 = new Intent(DurdurmaDevamEttirme.this, NesneDurdur.class);
                        startActivity(intent1);

                        break;

                    case 2:

                        Intent intent2 = new Intent(DurdurmaDevamEttirme.this, BelirliSureDurdur.class);
                        startActivity(intent2);

                        break;

                    case 3:

                        Intent intent3 = new Intent(DurdurmaDevamEttirme.this, NesneHataSimule.class);
                        startActivity(intent3);

                        break;

                    case 4:

                        Intent intent4 = new Intent(DurdurmaDevamEttirme.this, NesneDevamEttir.class);
                        startActivity(intent4);

                        break;

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent(DurdurmaDevamEttirme.this,Manipulation.class);
        startActivity(intent1);

    }

}
