package com.mujahidrasool.firebase;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class EnterCode extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
  EditText code;
  String codeSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        firebaseAuth = FirebaseAuth.getInstance();
        code = findViewById(R.id.codereceived);

        codeSent=getIntent().getStringExtra("code");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifySinnInCode();
            }
        });

    }


    private void verifySinnInCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code.getText().toString());

        signInWithPhoneAuthCredential(credential);
    }

    ////////////////////

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(EnterCode.this, "Signin Successful", Toast.LENGTH_LONG).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid


                                Toast.makeText(EnterCode.this, "Signin Failed invalid code", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
