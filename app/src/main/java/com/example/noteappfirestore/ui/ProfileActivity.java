package com.example.noteappfirestore.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteappfirestore.R;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profileImageView;
    private TextInputEditText displayNameEditText;
    private Button updateProfileButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();

    }

    private void initView() {
        profileImageView = (CircleImageView) findViewById(R.id.profileImageView);
        displayNameEditText = (TextInputEditText) findViewById(R.id.displayNameEditText);
        updateProfileButton = (Button) findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateProfileButton:
                // TODO 19/12/01
                break;
            default:
                break;
        }
    }
}
