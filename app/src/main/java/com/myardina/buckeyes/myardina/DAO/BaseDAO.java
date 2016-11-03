package com.myardina.buckeyes.myardina.DAO;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.Map;

/**
 * @author Tyler on 10/26/2016.
 */
public class BaseDAO {
    private FirebaseDatabase mRef;
    private DatabaseReference mTable;
    private FirebaseAuth auth;

    public BaseDAO() {
        mRef = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    protected void insert(Map<String, Object> insertMap, String table, String childId) {
        insertUpdateInfo(insertMap, table, childId);
    }

    protected void update(Map<String, Object> insertMap, String table, String childId) {
        insertUpdateInfo(insertMap, table, childId);
    }

    protected UserDTO retrieve(DataSnapshot dataSnapshot, boolean findChild) {
        UserDTO userDTO = new UserDTO();
        if (findChild) {
            for (DataSnapshot user : dataSnapshot.getChildren()) {
                String userInfoId = user.child(CommonConstants.USER_ID).getValue().toString();
                if (TextUtils.equals(userInfoId, auth.getCurrentUser().getUid())) {
                    mapUserDTO(user, userDTO);
                    break;
                }
            }
        } else {
            mapUserDTO(dataSnapshot, userDTO);
        }
        return userDTO;
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    private void insertUpdateInfo(Map<String, Object> insertMap, String table, String childId) {
        mTable = mRef.getReference(table).child(childId);
        for (String key : insertMap.keySet()) {
            mTable.child(key).setValue(insertMap.get(key));
        }
    }

    private void mapUserDTO(DataSnapshot user, UserDTO userDTO) {
        userDTO.setUserKey(user.getKey());
        userDTO.setUserId(getUserAttribute(user, CommonConstants.USER_ID));
        userDTO.setFirstName(getUserAttribute(user, CommonConstants.FIRST_NAME_COL));
        userDTO.setLastName(getUserAttribute(user, CommonConstants.LAST_NAME_COL));
        userDTO.setPhoneNumber(getUserAttribute(user, CommonConstants.PHONE_NUMBER_COL));
        userDTO.setEmail(getUserAttribute(user, CommonConstants.EMAIL_COL));
        userDTO.setLocation(getUserAttribute(user, CommonConstants.LOCATION_COL));
        userDTO.setRequesterPhoneNumber(getUserAttribute(user, CommonConstants.REQUESTER_PHONE_NUMBER_COL));
        userDTO.setAvailable(Boolean.valueOf(getUserAttribute(user, CommonConstants.AVAILABLE_COL)));
        userDTO.setDoctor(Boolean.valueOf(getUserAttribute(user, CommonConstants.DOCTOR_COL)));
        userDTO.setVerifiedDoctor(Boolean.valueOf(getUserAttribute(user, CommonConstants.VERIFIED_DOCTOR_COL)));
        userDTO.setRequested(Boolean.valueOf(getUserAttribute(user, CommonConstants.REQUESTED_COL)));
    }

    private String getUserAttribute(DataSnapshot snapshot, String attribute) {
        Object value = snapshot.child(attribute).getValue();
        return value == null ? null : value.toString();
    }
}
