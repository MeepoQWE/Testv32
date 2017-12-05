package com.example.user.testv3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;

    boolean EmailVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETemail = (EditText) findViewById(R.id.et_email_authent);
        ETpassword = (EditText) findViewById(R.id.et_password_authent);

        findViewById(R.id.sign_in_authent).setOnClickListener(this);
        findViewById(R.id.go_registration_authent).setOnClickListener(this);
        findViewById(R.id.but_go_recovery_password).setOnClickListener(this); // нормальные id дать для кнопок с началом but

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            Intent intent = new Intent(this, ContentActivity.class);
            startActivity(intent);
        }
    }

    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    EmailVerified  = user.isEmailVerified();

                    if (EmailVerified){
                        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(MainActivity.this, "Не пройдена верификация", Toast.LENGTH_SHORT).show();

                    }


                    /*Toast.makeText(LoginActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();*/
                }else
                    Toast.makeText(MainActivity.this, "Не правильно введен Логин или Пароль", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_authent:
                if (ETemail.getText().length() != 0 && ETpassword.getText().length() != 0){

                    signin(ETemail.getText().toString(),ETpassword.getText().toString());

                } else {
                    Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_registration_authent:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.but_go_recovery_password:
                Intent intent1 = new Intent(this, Recovery_password_Activity.class);
                startActivity(intent1);
                break;
        }

    }
}
