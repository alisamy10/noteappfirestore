package com.example.noteappfirestore.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteappfirestore.R;
import com.example.noteappfirestore.util.DataUtil;
import com.firebase.ui.auth.AuthUI;

import java.util.List;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    public static final int AUTHUI_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();

    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                goToLoginActivity();
                // TODO 19/12/01
                break;
            default:
                break;
        }
    }

    private void goToLoginActivity() {
     List<AuthUI.IdpConfig>provider=  Arrays.asList(
             new AuthUI.IdpConfig.EmailBuilder().build(),
             new AuthUI.IdpConfig.GoogleBuilder().build(),

          new AuthUI.IdpConfig.PhoneBuilder().build()
     );
     Intent intent =AuthUI.getInstance().createSignInIntentBuilder()
             .setAvailableProviders(provider).setTosAndPrivacyPolicyUrls("","")
             .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
             .setAlwaysShowSignInMethodScreen(true)
             .setLogo(R.drawable.notes).build();
     startActivityForResult(intent,AUTHUI_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if(requestCode==AUTHUI_REQUEST_CODE){
          if(resultCode==RESULT_OK){
              //we have signed in the user or we have a new user
              FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
              if(user.getMetadata().getCreationTimestamp()==user.getMetadata().getLastSignInTimestamp())
              {

                  Toast.makeText(this, "welcome new user", Toast.LENGTH_SHORT).show();
                 // DataUtil.currentUser=user;

              }
              else{

                  Toast.makeText(this, "welcome back again", Toast.LENGTH_SHORT).show();
              }

              startActivity(new Intent(this,HomeActivity.class));
              this.finish();
          }
          else{
              //sigining failed
              IdpResponse response =IdpResponse.fromResultIntent(data);
              if(response==null)
                Toast.makeText(this,"the user has cancel sign in request ", Toast.LENGTH_SHORT).show();
              else
                  Toast.makeText(this, response.getError().toString(), Toast.LENGTH_SHORT).show();
          }
      }
    }
}
