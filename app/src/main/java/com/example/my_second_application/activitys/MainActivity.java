package com.example.my_second_application.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;

import com.example.my_second_application.R;
import com.example.my_second_application.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();

    }
public void login(View v){

    String email = ((EditText) findViewById(R.id.log_email)).getText().toString();
    String password = ((EditText) findViewById(R.id.log_pass)).getText().toString();


    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this , "login ok" , Toast.LENGTH_LONG).show();
                        Navigation.findNavController(v).navigate(R.id.action_fragment_login_to_app);

                    } else {
                        Toast.makeText(MainActivity.this , "Email or password incorrect" , Toast.LENGTH_LONG).show();
                    }
                }
            });
}

public void register(View v){

    String email = ((EditText) findViewById(R.id.reg_email)).getText().toString();
    String password = ((EditText) findViewById(R.id.reg_pass)).getText().toString();
    String phone = ((EditText) findViewById(R.id.reg_phone)).getText().toString();
    String name = ((EditText) findViewById(R.id.reg_Name)).getText().toString();


    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Register completed", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.parseColor("#FFFFFF"));
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.setAction("Dismiss", v -> {
                        });
                        snackbar.show();
                        addData(email,phone,name);
                        Navigation.findNavController(v).navigate(R.id.action_register_to_login);

                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Email already exists", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.parseColor("#FFFFFF"));
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.setAction("Dismiss", v -> {
                        });
                        snackbar.show();
                    }
                }
            });
}


public void addData(String email,String phone,String name){

        String sanitizedEmail = email.replace(".", "_");

// Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Root").child("Users").child(sanitizedEmail);
    User s =new User(email,phone,name);
    myRef.setValue(s);

}

}