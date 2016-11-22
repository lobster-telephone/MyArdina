package com.myardina.buckeyes.myardina.Sevice.Impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Sevice.BaseService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public abstract class BaseServiceImpl implements BaseService {

    FirebaseDatabase mRef;

    public BaseServiceImpl() {
        mRef = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getDatabaseReference(String tableName) {
        return mRef.getReference().child(tableName);
    }

    protected void applyListener(ValueEventListener listener, String tableName) {
        DatabaseReference ref = getDatabaseReference(tableName);
        ref.addListenerForSingleValueEvent(listener);
    }

}
