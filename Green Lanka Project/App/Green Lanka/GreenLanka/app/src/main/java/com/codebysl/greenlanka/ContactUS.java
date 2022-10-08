package com.codebysl.greenlanka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ContactUS extends AppCompatActivity {

    EditText email, desc, phone;
    Button send;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Intent i = getIntent();
        user=i.getStringExtra("user");

        email = (EditText)findViewById(R.id.txtemailCnU);
        desc = (EditText)findViewById(R.id.txtDescCnU);
        phone = (EditText)findViewById(R.id.txtContactCnU);
        send = (Button)findViewById(R.id.btnSendCnu);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() || desc.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Please fill all fields!",Toast.LENGTH_SHORT).show();

                }
                else{

                    final String in_email = email.getText().toString().trim();
                    final String in_desc = desc.getText().toString().trim();
                    final String in_contact = phone.getText().toString().trim();


                    HashMap<String,String> feedback=new HashMap<>();

                    feedback.put("Email",in_email);
                    feedback.put("Description", in_desc);
                    feedback.put("Contact", in_contact);

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference tasksRef = rootRef.child("Feedbacks").child(user);
                    tasksRef.setValue(feedback);

                    email.setText(null);
                    email.setHint("Enter Address");
                    desc.setText(null);
                    desc.setHint("Enter Address");
                    phone.setText(null);
                    phone.setHint("Enter Address");

                    Toast.makeText(getApplicationContext(), "Send successfully!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Boolean validateEmail() {
        String val = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;

        } else {
            email.setError(null);
            return true;
        }
    }
}