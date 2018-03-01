package com.example.rasyona_egitim.PRTG.manipulation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rasyona_egitim.PRTG.Manipulation;
import com.example.rasyona_egitim.rasyonatool.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RaporaNesneEkleme extends AppCompatActivity {

    TextView txt1,txt2;
    EditText et1;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapora_sensor_ekleme);

        txt1 = (TextView) findViewById(R.id.textView1);
        txt2 = (TextView) findViewById(R.id.textView2);
        et1 = (EditText) findViewById(R.id.editText1);
        spinner = (Spinner)findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ReportAddSensor().execute();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent(RaporaNesneEkleme.this,Manipulation.class);
        startActivity(intent1);

    }

    private class ReportAddSensor extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
        String objId,reportId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(RaporaNesneEkleme.this);
            progressDialog.setMessage("İşlem Gerçekleştiriliyor.Lütfen Bekleyiniz...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            objId = et1.getText().toString().trim();
            final int selected = (int) spinner.getSelectedItemPosition();

            switch (selected) {

                case 0:

                    break;

                case 1:

                    reportId = String.valueOf(400);

                    break;

                case 2:

                    reportId = String.valueOf(415);

                    break;

                case 3:

                    reportId = String.valueOf(417);

                    break;

                case 4:

                    reportId = String.valueOf(413);

                    break;

                case 5:

                    reportId = String.valueOf(416);

                    break;

                case 6:

                    reportId = String.valueOf(414);

                    break;

                case 7:

                    reportId = String.valueOf(418);

                    break;

                case 8:

                    reportId = String.valueOf(412);

                    break;

            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String jsonStr = null;

            try {

                URL url = new URL("http://192.168.8.203:8080/api/reportaddsensor.htm?id="+reportId+"&addid="+objId+"&username=prtgadmin&password=prtgadmin");


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
