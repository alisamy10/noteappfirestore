package com.example.noteappfirestore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappfirestore.R;
import com.example.noteappfirestore.adapter.NoteAdapter;
import com.example.noteappfirestore.database.MyDataBase;
import com.example.noteappfirestore.database.model.NoteModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.ArrayListMultimap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, View.OnClickListener {
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    List<NoteModel> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initRec();


    }

    public void startStartActivty() {
        startActivity(new Intent(this, StartActivity.class));
        finish();

    }

    private void initRec() {


        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(null);
        noteAdapter.setOnClick(new NoteAdapter.OnClick() {
            @Override
            public void onItemClick(int pos, NoteModel noteModel) {

                Intent intent = new Intent(HomeActivity.this, AddNoteActivity.class);
                intent.putExtra("note", noteModel);
                intent.putExtra("flag","update");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(noteAdapter);
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.action_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
        MyDataBase.getAllByListner(MyDataBase.NOTES_REF, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null && e == null){
                    progressBar.setVisibility(View.GONE);
                    notes=new ArrayList<>();
                    for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                    {
                        notes.add(snapshot.toObject(NoteModel.class));
                    }
                    noteAdapter.setList(notes);
                }
            }
        });
        
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if (firebaseAuth.getCurrentUser() == null) {
            //not login
            startStartActivty();
            return;
        } /*else {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    Toast.makeText(HomeActivity.this, getTokenResult.getToken(), Toast.LENGTH_SHORT).show();
                    Log.e("a", "onSuccess: " + getTokenResult.getToken());
                }
            });
        }
        */

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(new Intent(HomeActivity.this, AddNoteActivity.class));

                // TODO 19/12/02
                break;
            case R.id.progress_bar:// TODO 19/12/02
                break;
            default:
                break;
        }
    }
}
