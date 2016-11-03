package com.myardina.buckeyes.myardina.DAO;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by Tyler on 10/26/2016.
 */
public class UserDAO extends BaseDAO {

    private static final String LOG_TAG = "USER_DAO_LOG";

    public UserDAO() { super(); }

    /**
     * Save information collected on the Register Activity
     */
    public void saveRegisterInformationPage(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformationPage...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.DOCTOR_COL, userDTO.isDoctor());
        insertMap.put(CommonConstants.REQUESTER_PHONE_NUMBER_COL, userDTO.getRequesterPhoneNumber());
        insertMap.put(CommonConstants.AVAILABLE_COL, userDTO.isAvailable());
        insertMap.put(CommonConstants.VERIFIED_DOCTOR_COL, userDTO.isVerifiedDoctor());
        insertMap.put(CommonConstants.REQUESTED_COL, userDTO.isRequested());
        insertMap.put(CommonConstants.EMAIL_COL, userDTO.getEmail());
        insert(insertMap, CommonConstants.USERS_TABLE, userDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveRegisterInformationPage...");
    }

    /**
     * Save information collected on the Additional Information Activity
     */
    public void saveAdditionalInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.FIRST_NAME_COL, userDTO.getFirstName());
        insertMap.put(CommonConstants.LAST_NAME_COL, userDTO.getLastName());
        insertMap.put(CommonConstants.LOCATION_COL, userDTO.getLocation());
        insertMap.put(CommonConstants.PHONE_NUMBER_COL, userDTO.getPhoneNumber());
        insert(insertMap, CommonConstants.USERS_TABLE, userDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
    }

    /**
     * Retrieve all user information from the database given a snapshot from an event
     * listener and specify whether the child needs found from the Users table first.
     */
    public UserDTO retrieveUserFromDataSnapshot(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        UserDTO userDTO = retrieve(dataSnapshot, findChild);
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
        return userDTO;
    }

    /**
     * Update whether or not doctor is available
     */
    public void updateDoctorAvailability(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorAvailability...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.AVAILABLE_COL, userDTO.isAvailable());
        insert(insertMap, CommonConstants.USERS_TABLE, userDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting updateDoctorAvailability...");
    }
}
