package com.codebysl.greenlanka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Collector_Login extends AppCompatActivity {

    TextView newUser;
    EditText cid, password;
    Button login;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collector_login);

        newUser = (TextView)findViewById(R.id.lnkRegisterC);
        cid = (EditText)findViewById(R.id.txtCIDCL);
        password = (EditText)findViewById(R.id.txtPasswordCL);
        login = (Button)findViewById(R.id.btnLoginCL);
        pd = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cid.getText().toString().isEmpty() || password.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Please fill all fields!",Toast.LENGTH_SHORT).show();
                }else{

                    final String icid = cid.getText().toString().trim();
                    final String pWord = password.getText().toString().trim();

                    checkLogin(icid, pWord);
                }
            }
        });

        newUser.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Collector_Registration.class);
            startActivity(i);
        });
    }
    public void checkLogin(String cid, String pWord){

        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Collector");
        pd.show();
        pd.setMessage("Wait...");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(cid).exists()){
                    String uName = snapshot.child(cid).child("Collector_ID").getValue().toString();
                    String uPassword = snapshot.child(cid).child("Password").getValue().toString();


                    if (uName.equals(cid)){
                        if(uPassword.equals(pWord)){
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Welcome Back!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Collector_Home.class);
                            i.putExtra("cid", cid);
                            startActivity(i);

                        }else{
                            Toast.makeText(getApplicationContext(), "Invalid Password!",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Username!",Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No user for this Username!",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
