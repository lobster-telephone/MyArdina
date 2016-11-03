package com.myardina.buckeyes.myardina.DAO;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler on 10/28/2016.
 */
public class DoctorDAO extends BaseDAO {

    private static final String LOG_TAG = "DOCTOR_DAO_LOG";

    public DoctorDAO() { super(); }

    /**
     * Update requested doctor information
     */
    public void updateDoctorToNotAvailable(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorToNotAvailable...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.REQUESTED_COL, true);
        insertMap.put(CommonConstants.AVAILABLE_COL, false);
        insertMap.put(CommonConstants.REQUESTER_PHONE_NUMBER_COL, userDTO.getRequesterPhoneNumber());
        update(insertMap, CommonConstants.USERS_TABLE, userDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting updateDoctorToNotAvailable...");
    }

    public List<UserDTO> retrieveAvailableDoctors(DataSnapshot snapshot) {
        List<UserDTO> availableDoctors = new ArrayList<>();

        for (DataSnapshot user : snapshot.getChildren()) {
            UserDTO doctor = retrieve(user, false);
            if (doctor.isAvailable() && doctor.isVerifiedDoctor()) {
                availableDoctors.add(doctor);
            }
        }

        return availableDoctors;
    }

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
}
