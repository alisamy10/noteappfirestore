package com.example.noteappfirestore.database;

import android.view.View;

import androidx.annotation.Nullable;

import com.example.noteappfirestore.database.model.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyDataBase {
    private static FirebaseFirestore myDataBase;
    public static final String USER_REF="users";
    public static final String NOTES_REF="notes";


    public static FirebaseFirestore getInsatnce(){

        if(myDataBase==null)
            myDataBase=FirebaseFirestore.getInstance();
        return  myDataBase;

    }
    public static CollectionReference getUserReference(){
        return getInsatnce().collection(USER_REF);
    }
    public static CollectionReference getNoteReference(){
        return getInsatnce().collection(NOTES_REF);
    }

    public static void getByCondition(String collectionPath, String key , Object value  , OnCompleteListener<QuerySnapshot> onCompleteListener){
        getInsatnce().collection(collectionPath).whereEqualTo(key,value).get().addOnCompleteListener(onCompleteListener);
    }

    public static void getAll(String collectionPath  , OnCompleteListener<QuerySnapshot> onCompleteListener){
        //CollectionReference documentReference = FirebaseFirestore.getInstance().collection(collectionPath);
        getInsatnce().collection(collectionPath).get().addOnCompleteListener(onCompleteListener);
    }
    public static void getAllByListner(String collectionPath  , EventListener<QuerySnapshot> onCompleteListener){
        //CollectionReference documentReference = FirebaseFirestore.getInstance().collection(collectionPath);
        getInsatnce().collection(collectionPath).addSnapshotListener(onCompleteListener);
    }

    public static void getByDocument(String collectionPath  ,String document , OnCompleteListener <DocumentSnapshot> onCompleteListener){

        getInsatnce().collection(collectionPath).document(document).get().addOnCompleteListener(onCompleteListener);
    }

//get all with out listner
    public static void getAllOrderBy(String collectionPath  ,String orderBy ,  OnCompleteListener<QuerySnapshot> onCompleteListener){
        //CollectionReference documentReference = FirebaseFirestore.getInstance().collection(collectionPath);
        getInsatnce().collection(collectionPath).orderBy(orderBy).get().addOnCompleteListener(onCompleteListener);
    }






}
