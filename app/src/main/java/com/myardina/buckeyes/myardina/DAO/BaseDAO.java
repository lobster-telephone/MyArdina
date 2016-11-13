package com.myardina.buckeyes.myardina.DAO;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;

import java.util.Map;

/**
 * @author Tyler on 10/26/2016.
 */
public class BaseDAO {
    private FirebaseDatabase mRef;
    private FirebaseAuth auth;

    public BaseDAO() {
        mRef = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    /**
     ******************
     *  PUBLIC LOGIC  *
     ******************
     */

    protected void insert(Map<String, Object> insertMap, String table, String childId) {
        insertUpdateInfo(insertMap, table, childId);
    }

    protected void update(Map<String, Object> insertMap, String table, String childId) {
        insertUpdateInfo(insertMap, table, childId);
    }

    protected DoctorDTO retrieveDoctor(DataSnapshot dataSnapshot, boolean findChild) {
        DoctorDTO doctorDTO = new DoctorDTO();
        if (findChild) {
            for (DataSnapshot user : dataSnapshot.getChildren()) {
                String userInfoId = user.child(CommonConstants.USER_ID).getValue().toString();
                if (TextUtils.equals(userInfoId, auth.getCurrentUser().getUid())) {
                    mapDoctorDTO(user, doctorDTO);
                    break;
                }
            }
        } else {
            mapDoctorDTO(dataSnapshot, doctorDTO);
        }
        return doctorDTO;
    }

    protected PatientDTO retrievePatient(DataSnapshot dataSnapshot, boolean findChild) {
        PatientDTO patientDTO = new PatientDTO();
        if (findChild) {
            for (DataSnapshot user : dataSnapshot.getChildren()) {
                String userInfoId = user.child(CommonConstants.USER_ID).getValue().toString();
                if (TextUtils.equals(userInfoId, auth.getCurrentUser().getUid())) {
                    mapPatientDTO(user, patientDTO);
                    break;
                }
            }
        } else {
            mapPatientDTO(dataSnapshot, patientDTO);
        }
        return patientDTO;
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    private void insertUpdateInfo(Map<String, Object> insertMap, String table, String childId) {
        DatabaseReference tableRef = mRef.getReference(table).child(childId);
        for (String key : insertMap.keySet()) {
            tableRef.child(key).setValue(insertMap.get(key));
        }
    }

    private void mapPatientDTO(DataSnapshot user, PatientDTO patientDTO) {
        patientDTO.setUserKey(user.getKey());
        patientDTO.setUserId(getUserAttribute(user, CommonConstants.USER_ID));
        patientDTO.setFirstName(getUserAttribute(user, CommonConstants.FIRST_NAME_COL));
        patientDTO.setLastName(getUserAttribute(user, CommonConstants.LAST_NAME_COL));
        patientDTO.setPhoneNumber(getUserAttribute(user, CommonConstants.PHONE_NUMBER_COL));
        patientDTO.setEmail(getUserAttribute(user, CommonConstants.EMAIL_COL));
    }

    private void mapDoctorDTO(DataSnapshot user, DoctorDTO doctorDTO) {
        doctorDTO.setUserKey(user.getKey());
        doctorDTO.setUserId(getUserAttribute(user, CommonConstants.USER_ID));
        doctorDTO.setFirstName(getUserAttribute(user, CommonConstants.FIRST_NAME_COL));
        doctorDTO.setLastName(getUserAttribute(user, CommonConstants.LAST_NAME_COL));
        doctorDTO.setEmail(getUserAttribute(user, CommonConstants.EMAIL_COL));
        doctorDTO.setLocation(getUserAttribute(user, CommonConstants.LOCATION_COL));
        doctorDTO.setRequesterPhoneNumber(getUserAttribute(user, CommonConstants.REQUESTER_PHONE_NUMBER_COL));
        doctorDTO.setAvailable(Boolean.valueOf(getUserAttribute(user, CommonConstants.AVAILABLE_COL)));
        doctorDTO.setVerifiedDoctor(Boolean.valueOf(getUserAttribute(user, CommonConstants.VERIFIED_DOCTOR_COL)));
        doctorDTO.setRequested(Boolean.valueOf(getUserAttribute(user, CommonConstants.REQUESTED_COL)));
    }

    private String getUserAttribute(DataSnapshot snapshot, String attribute) {
        Object value = snapshot.child(attribute).getValue();
        return value == null ? null : value.toString();
    }
}
