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
    public void saveRegisterInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformation...");
        PatientDTO patientDTO = (PatientDTO) userDTO;
        insert(patientDTO, CommonConstants.PATIENTS_TABLE);
        Log.d(LOG_TAG, "Exiting saveRegisterInformation...");
    }

    @Override
    public void saveAdditionalInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        Map<String, Object> updateMap = new HashMap<>();
        PatientDTO patientDTO = (PatientDTO) userDTO;
        updateMap.put(CommonConstants.FIRST_NAME_COL, patientDTO.getFirstName());
        updateMap.put(CommonConstants.LAST_NAME_COL, patientDTO.getLastName());
        updateMap.put(CommonConstants.PHONE_NUMBER_COL, patientDTO.getPhoneNumber());
        update(updateMap, CommonConstants.PATIENTS_TABLE, patientDTO.getTableKey());
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
    }
}
