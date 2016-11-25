package com.myardina.buckeyes.myardina.Sevice.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myardina.buckeyes.myardina.Sevice.BaseService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public abstract class BaseServiceImpl implements BaseService {

    private static final String LOG_TAG = "BASE_SERVICE";

    FirebaseDatabase mRef;

    public BaseServiceImpl() {
        mRef = FirebaseDatabase.getInstance();
    }

    @Override
    public DatabaseReference getDatabaseReference(String tableName) {
        Log.d(LOG_TAG, "Entering getDatabaseReference...");
        Log.d(LOG_TAG, "Exiting getDatabaseReference...");
        return mRef.getReference().child(tableName);
    }

    @Override
    public DataSnapshot getTableSnapshot(DataSnapshot snapshot, String tableName) {
        Log.d(LOG_TAG, "Entering retrievePicklist...");
        Log.d(LOG_TAG, "Exiting getDatabaseReference...");
        return snapshot.child(tableName);
    }

}
