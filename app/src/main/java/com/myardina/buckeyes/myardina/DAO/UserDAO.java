package com.myardina.buckeyes.myardina.DAO;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by Tyler on 10/26/2016.
 */
public class UserDAO extends BaseDAO {

    private static final String LOG_TAG = "USER_DAO_LOG";

    public UserDAO() { super(); }

    /**
     * Save information collected on the Register Activity for doctors
     */
    public void saveRegisterInformationDoctor(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformationDoctor...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.REQUESTER_PHONE_NUMBER_COL, doctorDTO.getRequesterPhoneNumber());
        insertMap.put(CommonConstants.AVAILABLE_COL, doctorDTO.isAvailable());
        insertMap.put(CommonConstants.VERIFIED_DOCTOR_COL, doctorDTO.isVerifiedDoctor());
        insertMap.put(CommonConstants.REQUESTED_COL, doctorDTO.isRequested());
        insertMap.put(CommonConstants.EMAIL_COL, doctorDTO.getEmail());
        insertMap.put(CommonConstants.USER_ID, doctorDTO.getUserId());
        insert(insertMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveRegisterInformationDoctor...");
    }

    /**
     * Save information collected on the Register Activity for patients
     */
    public void saveRegisterInformationPatient(PatientDTO patientDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformationPatient...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.EMAIL_COL, patientDTO.getEmail());
        insertMap.put(CommonConstants.USER_ID, patientDTO.getUserId());
        insert(insertMap, CommonConstants.PATIENTS_TABLE, patientDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveRegisterInformationPatient...");
    }

    /**
     * Save information collected on the Additional Information Activity for Patients
     */
    public void saveAdditionalInformationPatient(PatientDTO patientDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformationPatient...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.FIRST_NAME_COL, patientDTO.getFirstName());
        insertMap.put(CommonConstants.LAST_NAME_COL, patientDTO.getLastName());
        insertMap.put(CommonConstants.PHONE_NUMBER_COL, patientDTO.getPhoneNumber());
        insert(insertMap, CommonConstants.PATIENTS_TABLE, patientDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveAdditionalInformationPatient...");
    }

    /**
     * Save information collected on the Additional Information Activity for Doctors
     */
    public void saveAdditionalInformationDoctor(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformationDoctor...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.FIRST_NAME_COL, doctorDTO.getFirstName());
        insertMap.put(CommonConstants.LAST_NAME_COL, doctorDTO.getLastName());
        insertMap.put(CommonConstants.LOCATION_COL, doctorDTO.getLocation());
        insert(insertMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveAdditionalInformationDoctor...");
    }

    /**
     * Retrieve all user information from the database given a snapshot from an event
     * listener and specify whether the child needs found from the Users table first.
     */
    public DoctorDTO retrieveUserFromDataSnapshotDoctor(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering retrieveUserFromDataSnapshotDoctor...");
        DoctorDTO doctorDTO = retrieveDoctor(dataSnapshot, findChild);
        Log.d(LOG_TAG, "Exiting retrieveUserFromDataSnapshotDoctor...");
        return doctorDTO;
    }

    /**
     * Retrieve all user information from the database given a snapshot from an event
     * listener and specify whether the child needs found from the Users table first.
     */
    public PatientDTO retrieveUserFromDataSnapshotPatient(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering retrieveUserFromDataSnapshotPatient...");
        PatientDTO patientDTO = retrievePatient(dataSnapshot, findChild);
        Log.d(LOG_TAG, "Exiting retrieveUserFromDataSnapshotPatient...");
        return patientDTO;
    }

    /**
     * Update whether or not doctor is available
     */
    public void updateDoctorAvailability(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorAvailability...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.AVAILABLE_COL, doctorDTO.isAvailable());
        insert(insertMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting updateDoctorAvailability...");
    }
}
