package com.codebysl.greenlanka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Collector_Registration extends AppCompatActivity {
    TextView alreadyUser;
    EditText bname, bid, cid, email, pword, cpword;
    Button signin;
    String alreadyExist;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collector_registration);

        alreadyUser = (TextView)findViewById(R.id.lnkLoginC);
        bname = (EditText)findViewById(R.id.txtBusinessNameCR);
        bid = (EditText)findViewById(R.id.txtBusinessIdCR);
        cid = (EditText)findViewById(R.id.txtIDCR);
        email = (EditText)findViewById(R.id.txtEmailCR);
        pword = (EditText)findViewById(R.id.txtPwdCR);
        cpword = (EditText)findViewById(R.id.txtCpwdCR);
        signin = (Button)findViewById(R.id.btnLoginCR);

        pd = new ProgressDialog(this);

        alreadyUser.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Collector_Login.class);
            startActivity(i);
        });

        signin.setOnClickListener(v -> {

            if(bname.getText().toString().isEmpty() || bid.getText().toString().isEmpty() || cid.getText().toString().isEmpty() || !validateEmail() || pword.getText().toString().isEmpty() || cpword.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please fill all fields!",Toast.LENGTH_SHORT).show();
            }
            else{

                final String ubname = bname.getText().toString().trim();
                final String ubid = bid.getText().toString().trim();
                final String ucid = cid.getText().toString().trim();
                final String uemail = email.getText().toString().trim();
                final String password = pword.getText().toString().trim();
                final String cpassword = cpword.getText().toString().trim();

                if (password.equals(cpassword)){
                    pd.show();
                    pd.setMessage("Wait...");
                    insert2database(ubname, ubid, ucid, uemail, password);
                    pd.dismiss();;

                }else{
                    Toast.makeText(getApplicationContext(), "Password Miss Match!",Toast.LENGTH_SHORT).show();
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

    private Boolean validateUname(String val){

        alreadyExist = "No";
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Users").child("Collector");
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

    public void insert2database(String bname, String bid, String cidx, String email, String pword){

        if (validateUname(cidx)){
            cid.setError("Please use another Collector ID!");
            Toast.makeText(getApplicationContext(), "This Collector ID already exist!",Toast.LENGTH_SHORT).show();
        } else {
            cid.setError(null);
            HashMap<String,String> collector=new HashMap<>();

            collector.put("Business_Name",bname);
            collector.put("Business_ID", bid);
            collector.put("Collector_ID",cidx);
            collector.put("Email", email);
            collector.put("Password", pword);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference tasksRef = rootRef.child("Collector").child(cidx);
            tasksRef.setValue(collector);

            Toast.makeText(getApplicationContext(), "Registration successfully!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Collector_Home.class);
            startActivity(i);
        }
    }
}
