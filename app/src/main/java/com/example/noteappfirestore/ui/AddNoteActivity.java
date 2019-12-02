package com.example.noteappfirestore.ui;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noteappfirestore.R;
import com.example.noteappfirestore.database.MyDataBase;
import com.example.noteappfirestore.database.NoteDao;
import com.example.noteappfirestore.database.model.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private CircleImageView imageProfile;
    private EditText descEdit, tittleEdit;
    private TextView date1Txt, time1Txt;
    private Button addNote;
    private NoteModel note;


    private int mHour, mYear, mMonth, mMinute, mDay;
    private String sTime, sDate;
String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
        Intent intent2 = getIntent();

        Bundle bundle = intent2.getExtras();


        if(bundle!=null){
            if("update".equals(bundle.get("flag"))){
                note = (NoteModel) getIntent().getSerializableExtra("note");

                date1Txt.setText(note.formatDate());
                time1Txt.setText(note.formatTime());
                descEdit.setText(note.getDes());
                id   =    note.getId();
             //   note.getImage_url();
               tittleEdit.setText(note.getTitle());

               addNote.setText("UPDate");
               addNote.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       NoteDao.updateNote(id, tittleEdit.getText().toString(), descEdit.getText().toString(), new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful())
                               {
                                   startActivity(new Intent(AddNoteActivity.this, HomeActivity.class));

                               }
                           }
                       });
                   }
               });



            }
        }


else {

            descEdit.setText(null);
            tittleEdit.setText(null);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sDate = sdf.format(new Date());
            date1Txt.setText(sDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss ");
            sTime = sdf2.format(new Date());
            time1Txt.setText(sTime);



        }



    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        sDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        Toast.makeText(this, sDate, Toast.LENGTH_SHORT).show();
        date1Txt.setText(sDate);
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10)
            sTime = hourOfDay + ":" + "0" + minute;
        else
            sTime = hourOfDay + ":" + minute;

        time1Txt.setText(sTime);
    }

    private void initView() {
        imageProfile = findViewById(R.id.profile_image);
        descEdit = findViewById(R.id.edit_desc);
        tittleEdit = findViewById(R.id.edit_tittle);
        date1Txt = findViewById(R.id.txt_date1);
        date1Txt.setOnClickListener(this);

        time1Txt = findViewById(R.id.txt_time1);
        time1Txt.setOnClickListener(this);
        addNote = findViewById(R.id.sign_up_btn);
        addNote.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_btn:
                String des = descEdit.getText().toString().trim();
                String title = tittleEdit.getText().toString().trim();
                String date = date1Txt.getText().toString();
                String time = time1Txt.getText().toString();

                if (checkValidation(des, title))
                    saveNote(des, title, time, date);
                break;
            case R.id.txt_date1:// TODO 19/12/01
                setDate();

                break;
            case R.id.txt_time1:// TODO 19/12/01
               setTime();
                break;
            default:
                break;
        }
    }

    private void saveNote(String des, String title, String sTime, String sDate) {
        NoteModel noteModel = new NoteModel();
        noteModel.setTitle(title);
        noteModel.setDes(des);

        Date date = null;
        Date time = null;

        DateFormat dateFormat = new SimpleDateFormat("E, dd/MMMM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a ");
        try {
            date = dateFormat.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            time = timeFormat.parse(sTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        noteModel.setTime(time);
        noteModel.setDate(date);


        NoteDao.addNote(noteModel, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    startActivity(new Intent(AddNoteActivity.this, HomeActivity.class));
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private boolean checkValidation(String des, String title) {
        boolean isValid = true;
        if (des.isEmpty()) {
            descEdit.setError("required");
            isValid = false;

        } else {
            descEdit.setError(null);
            isValid = true;
        }
        if (title.isEmpty()) {
            tittleEdit.setError("required");
            isValid = false;

        } else {
            tittleEdit.setError(null);
            isValid = true;
        }
        return isValid;

    }


}
