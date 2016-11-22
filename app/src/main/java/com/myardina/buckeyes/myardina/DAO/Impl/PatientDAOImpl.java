package com.myardina.buckeyes.myardina.DAO.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.PatientDAO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tyler Lacks on 11/15/2016.
 */
public class PatientDAOImpl extends UserDAOImpl implements PatientDAO {

    private static final String LOG_TAG = "PATIENT_DAO_LOG";

    public PatientDAOImpl() { super(); }

    @Override
    public PatientDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering retrieveUser...");
        PatientDTO patientDTO = (PatientDTO) retrieveUser(dataSnapshot, findChild, PatientDTO.class);
        Log.d(LOG_TAG, "Exiting retrieveUser...");
        return patientDTO;
    }

    @Override
    public void saveAdditionalInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        Map<String, Object> insertMap = new HashMap<>();
        PatientDTO patientDTO = (PatientDTO) userDTO;
        insertMap.put(CommonConstants.FIRST_NAME_COL, patientDTO.getFirstName());
        insertMap.put(CommonConstants.LAST_NAME_COL, patientDTO.getLastName());
        insertMap.put(CommonConstants.PHONE_NUMBER_COL, patientDTO.getPhoneNumber());
        insert(insertMap, CommonConstants.PATIENTS_TABLE, patientDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
    }

    @Override
    public void saveRegisterInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformation...");
        Map<String, Object> insertMap = new HashMap<>();
        PatientDTO patientDTO = (PatientDTO) userDTO;
        insertMap.put(CommonConstants.EMAIL_COL, patientDTO.getEmail());
        insertMap.put(CommonConstants.USER_ACCOUNT_ID, patientDTO.getUserAccountId());
        insert(insertMap, CommonConstants.PATIENTS_TABLE, patientDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveRegisterInformation...");
    }
}
