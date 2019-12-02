package com.example.noteappfirestore.database;

import com.example.noteappfirestore.database.model.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NoteDao {
    public static void addNote(NoteModel note, OnCompleteListener<Void> onCompletionListener){
        DocumentReference document =MyDataBase.getNoteReference().document();
        note.setId(document.getId());
        document.set(note).addOnCompleteListener(onCompletionListener);
    }


       public static void deleteRoom(NoteModel note, OnCompleteListener<Void> onCompleteListener){
        MyDataBase.getNoteReference().document(note.getId()).delete().addOnCompleteListener(onCompleteListener);
    }

    public static void updateNote(String id,Object des , Object title,OnCompleteListener<Void> onCompleteListener){
        MyDataBase.getNoteReference().document(id).update("title",title,"des",des).addOnCompleteListener(onCompleteListener);
    }

}
