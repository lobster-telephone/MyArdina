package com.myardina.buckeyes.myardina.DAO.Impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.BaseDAO;
import com.myardina.buckeyes.myardina.DTO.BaseDTO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.Map;

/**
 * @author Tyler Lacks on 10/26/2016.
 */
public abstract class BaseDAOImpl implements BaseDAO {

    private static final String LOG_TAG = "BASE_DAO";

    private FirebaseDatabase mRef;
    private FirebaseAuth auth;

    public BaseDAOImpl() {
        mRef = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    /**
     ******************
     *  PUBLIC LOGIC  *
     ******************
     */

    @Override
    public void insert(BaseDTO baseDTO, String tableName) {
        Log.d(LOG_TAG, "Entering insert...");
        DatabaseReference table = mRef.getReference(tableName);
        DatabaseReference childRef = table.push();
        baseDTO.setTableKey(childRef.getKey());
        childRef.setValue(baseDTO);
        Log.d(LOG_TAG, "Exiting insert...");
    }

    @Override
    public void update(Map<String, Object> insertMap, String table, String childId) {
        Log.d(LOG_TAG, "Entering update...");
        insertUpdateInfo(insertMap, table, childId);
        Log.d(LOG_TAG, "Exiting update...");
    }

    @Override
    public Object retrieve(DataSnapshot dataSnapshot, Class clazz) {
        Log.d(LOG_TAG, "Entering retrieve...");
        Log.d(LOG_TAG, "Exiting retrieve...");
        return dataSnapshot.getValue(clazz);
    }

    @Override
    public UserDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild, Class<? extends UserDTO> clazz) {
        Log.d(LOG_TAG, "Entering retrieveUser...");
        UserDTO userDTO = null;
        if (findChild) {
            for (DataSnapshot user : dataSnapshot.getChildren()) {
                String userInfoId = user.child(CommonConstants.USER_ACCOUNT_ID).getValue().toString();
                if (auth.getCurrentUser() != null && TextUtils.equals(userInfoId, auth.getCurrentUser().getUid())) {
                    userDTO = user.getValue(clazz);
                    userDTO.setTableKey(user.getKey());
                    break;
                }
            }
        } else {
            userDTO = dataSnapshot.getValue(clazz);
            userDTO.setTableKey(dataSnapshot.getKey());
        }
        Log.d(LOG_TAG, "Exiting retrieveUser...");
        return userDTO;
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    private void insertUpdateInfo(Map<String, Object> insertMap, String table, String childId) {
        Log.d(LOG_TAG, "Entering insertUpdateInfo...");
        DatabaseReference tableRef = mRef.getReference(table).child(childId);
        for (String key : insertMap.keySet()) {
            tableRef.child(key).setValue(insertMap.get(key));
        }
        Log.d(LOG_TAG, "Exiting insertUpdateInfo...");
    }
}
