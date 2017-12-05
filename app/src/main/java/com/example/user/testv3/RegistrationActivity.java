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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        ETemail = (EditText) findViewById(R.id.et_email_reg);
        ETpassword = (EditText) findViewById(R.id.et_password_reg);


        findViewById(R.id.registration_reg).setOnClickListener(this);
        findViewById(R.id.go_authent_reg).setOnClickListener(this);
    }

    public void reg(String email , String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        /*Log.d(TAG, "Email успешно отправлен");*/
                                    }
                                }
                            });

                    /*Toast.makeText(RegistrationActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();*/
                } else
                    Toast.makeText(RegistrationActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_reg:
                if (ETemail.getText().length() != 0 && ETpassword.getText().length() != 0) {

                    reg(ETemail.getText().toString(), ETpassword.getText().toString());

                } else {
                    Toast.makeText(RegistrationActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_authent_reg:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
