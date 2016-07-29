package in.dineshsunny.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import in.dineshsunny.coolfirebase.MainActivity;
import in.dineshsunny.coolfirebase.R;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText passwd;
    EditText confirmPasswd;
    Button signUpBtn;
    TextView alreadyHaveAnAccount;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getAllIdS();
        checkSignUP();


    }

    private void checkSignUP() {

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText =
                        email.getText()
                         .toString()
                          .toLowerCase().trim();

                String passwdText =
                        passwd.getText()
                                .toString();

                String confirmPasswdText =
                        confirmPasswd.getText()
                                .toString();

                if(TextUtils.isEmpty(emailText)){
                    MainActivity.showToast(SignUpActivity.this, "Enter your Email Address");
                    return;
                }else if(!emailText.contains("@")){
                    MainActivity.showToast(SignUpActivity.this, "Enter Valid Email Address");
                    return;
                }else if(TextUtils.isEmpty(passwdText)){
                    MainActivity.showToast(SignUpActivity.this, "Enter your Password");
                    return;
                }else if(TextUtils.isEmpty(confirmPasswdText)){
                    MainActivity.showToast(SignUpActivity.this, "Enter your Confirm Password");
                    return;
                }else if(passwdText.length() < 4){
                    MainActivity.showToast(SignUpActivity.this, "Password should be atleast 4 Characters");
                    return;
                }else if(!passwdText.equals(confirmPasswdText)){
                    MainActivity.showToast(SignUpActivity.this, "Both Passwords should Match!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth
                        .createUserWithEmailAndPassword(
                                emailText, passwdText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        MainActivity.showToast(SignUpActivity.this, "User Successfully Created!");
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);

                        if(!task.isSuccessful()){
                            MainActivity.showToast(SignUpActivity.this, "Something Error Occurred");
                        }
                    }
                });




            }
        });

    }

    private void getAllIdS() {

        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.emailId);
        passwd = (EditText) findViewById(R.id.passwd);
        confirmPasswd = (EditText) findViewById(R.id.confirmPasswd);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        alreadyHaveAnAccount= (TextView) findViewById(R.id.loginTextInSignup);
        progressBar = (ProgressBar) findViewById(R.id.signUpProgressBar);
    }


}
