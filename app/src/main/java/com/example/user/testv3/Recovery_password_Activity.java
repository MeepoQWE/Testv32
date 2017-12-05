package com.example.user.testv3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Recovery_password_Activity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText ETRecovery_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password_);

        mAuth = FirebaseAuth.getInstance();

        ETRecovery_password = (EditText) findViewById(R.id.et_recovery_password);
        findViewById(R.id.but_recovery_password).setOnClickListener(this);
    }

    public void recovery(String recovery_password){     //возможный баг проверка прошел ли верификацию прежде чем востановить пароль

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mAuth.sendPasswordResetEmail(recovery_password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "Email отправлен");
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_recovery_password:
                if (ETRecovery_password.getText().length() != 0) {

                    recovery(ETRecovery_password.getText().toString());

                } else {
                    Toast.makeText(Recovery_password_Activity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
