package com.myardina.buckeyes.myardina.Sevice;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface BaseService {

    /**
     * Return a reference of the table specified from the Database.
     */
    DatabaseReference getDatabaseReference(String tableName);

    /**
     * Return a snapshot of the table specified from the Database.
     */
    DataSnapshot getTableSnapshot(DataSnapshot snapshot, String tableName);
}
