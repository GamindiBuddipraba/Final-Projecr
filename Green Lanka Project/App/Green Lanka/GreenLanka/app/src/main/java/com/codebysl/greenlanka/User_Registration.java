package com.codebysl.greenlanka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class User_Registration extends AppCompatActivity {

    TextView alreadyUser;
    EditText uname, email, pword, cpword;
    Button login;
    String alreadyExist;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        alreadyUser = (TextView)findViewById(R.id.lnkLogin);
        uname = (EditText)findViewById(R.id.txtNameUR);
        email = (EditText)findViewById(R.id.txtEmailUR);
        pword = (EditText)findViewById(R.id.txtPwdUR);
        cpword = (EditText)findViewById(R.id.txtCPWUR);
        login = (Button)findViewById(R.id.btnLoginUR);

        pd = new ProgressDialog(this);

        alreadyUser.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), User_Login.class);
            startActivity(i);
        });

        login.setOnClickListener(v -> {

            if(uname.getText().toString().isEmpty() || !validateEmail() || pword.getText().toString().isEmpty() || cpword.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please fill all fields!",Toast.LENGTH_SHORT).show();
            }
            else{

                final String username = uname.getText().toString().trim();
                final String uemail = email.getText().toString().trim();
                final String password = pword.getText().toString().trim();
                final String cpassword = cpword.getText().toString().trim();

                if (password.equals(cpassword)){
                    pd.show();
                    pd.setMessage("Wait...");
                    insert2database(username, uemail, password);
                    pd.dismiss();;

                }else{
                    Toast.makeText(getApplicationContext(), "Password Miss Match!",Toast.LENGTH_SHORT).show();
                }

            }

        });



    }
    public void insert2database(String unamex, String email, String pword){

        if (validateUname(unamex)){
            uname.setError("Please use another Username!");
            Toast.makeText(getApplicationContext(), "This user name already exist!",Toast.LENGTH_SHORT).show();
        } else {
            uname.setError(null);
            HashMap<String,String> user=new HashMap<>();

            user.put("Username",unamex);
            user.put("Email", email);
            user.put("Password", pword);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference tasksRef = rootRef.child("Users").child("User").child(unamex);
            tasksRef.setValue(user);

            Toast.makeText(getApplicationContext(), "Registration successfully!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), User_Home.class);
            startActivity(i);
        }
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

    private Boolean validateUname(String val){

        alreadyExist = "No";
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Users").child("User");
        pd.show();
        pd.setMessage("Wait...");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(val).exists()){
                    alreadyExist = "Yes";
                }else{
                    //pd.dismiss();
                    alreadyExist = "No";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pd.dismiss();
        if (alreadyExist=="Yes"){
            //Toast.makeText(getApplicationContext(), "XXX",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            //Toast.makeText(getApplicationContext(), "YYY",Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
