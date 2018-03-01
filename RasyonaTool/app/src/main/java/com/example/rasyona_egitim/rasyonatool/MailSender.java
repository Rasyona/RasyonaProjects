package com.example.rasyona_egitim.rasyonatool;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MailSender extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    private EditText etMail;
    private EditText etSubject;
    private EditText etMessage;

    private TextView tv1, tv2, tv3;

    //Send button
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sender);

        tv1 = (TextView)findViewById(R.id.txt1);
        tv2 = (TextView)findViewById(R.id.txt2);
        tv3 = (TextView)findViewById(R.id.txt3);

        //Initializing the views
        etMail = (EditText) findViewById(R.id.editTextEmail);
        etSubject = (EditText) findViewById(R.id.editTextSubject);
        etMessage = (EditText) findViewById(R.id.editTextMessage);

        btnSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener
        btnSend.setOnClickListener(this);
    }


    private void sendEmail() {
        //Getting content for email
        String email = etMail.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}