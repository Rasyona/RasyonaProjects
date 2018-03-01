package com.example.rasyona_egitim.PRTG.manipulation.nesne_ekleme_silme.nesne_ekleme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rasyona_egitim.PRTG.manipulation.nesne_ekleme_silme.NesneEkleme;
import com.example.rasyona_egitim.rasyonatool.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CihazKopyala extends AppCompatActivity {

    TextView txt1,txt2,txt3,txt4;
    EditText et1,et2,et3,et4;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cihaz_kopyala);

        txt1 = (TextView) findViewById(R.id.textView1);
        txt2 = (TextView) findViewById(R.id.textView2);
        txt3 = (TextView) findViewById(R.id.textView3);
        txt4 = (TextView) findViewById(R.id.textView4);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DuplicateDevice().execute();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent(CihazKopyala.this,NesneEkleme.class);
        startActivity(intent1);

    }

    private class DuplicateDevice extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
        String deviceId,name,host,targetGroup;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(CihazKopyala.this);
            progressDialog.setMessage("İşlem Gerçekleştiriliyor.Lütfen Bekleyiniz...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            deviceId = et1.getText().toString().trim();
            name = et2.getText().toString().trim();
            host = et3.getText().toString().trim();
            targetGroup = et4.getText().toString().trim();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String jsonStr = null;

            try {

                URL url = new URL("http://192.168.8.203:8080/api/duplicateobject.htm?id="+deviceId+"&name="+name+"&host="+host+"&targetid="+targetGroup+"&username=prtgadmin&password=prtgadmin");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                jsonStr = buffer.toString();

                return null;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"İşlem Tamamlandı.",Toast.LENGTH_LONG).show();

        }
    }
}
