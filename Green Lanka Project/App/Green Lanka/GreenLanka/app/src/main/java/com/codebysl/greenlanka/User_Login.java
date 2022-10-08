package com.codebysl.greenlanka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class User_Login extends AppCompatActivity {

    TextView newUser;
    EditText username, password;
    Button login;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        username = (EditText)findViewById(R.id.txtUNameLU);
        password = (EditText)findViewById(R.id.txtPasswordLU);
        newUser = (TextView)findViewById(R.id.lnkRegister);
        login = (Button)findViewById(R.id.btnLoginLU);
        pd = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Please fill all fields!",Toast.LENGTH_SHORT).show();
                }else{

                    final String uname = username.getText().toString().trim();
                    final String pWord = password.getText().toString().trim();

                    checkLogin(uname, pWord);
                }
            }
        });

        newUser.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), User_Registration.class);
            startActivity(i);
        });
    }

    public void checkLogin(String uname, String pWord){

        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Users").child("User");
        pd.show();
        pd.setMessage("Wait...");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(uname).exists()){
                    String uName = snapshot.child(uname).child("Username").getValue().toString();
                    String uPassword = snapshot.child(uname).child("Password").getValue().toString();


                    if (uName.equals(uname)){
                        if(uPassword.equals(pWord)){
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Welcome Back!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), User_Home.class);
                            i.putExtra("uname", uname);
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
