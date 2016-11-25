package com.myardina.buckeyes.myardina.DAO.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.DoctorDAO;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler Lacks on 10/28/2016.
 */
public class DoctorDAOImpl extends UserDAOImpl implements DoctorDAO {

    private static final String LOG_TAG = "DOCTOR_DAO_LOG";

    public DoctorDAOImpl() { super(); }

    @Override
    public DoctorDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering retrieveUser...");
        DoctorDTO doctorDTO = (DoctorDTO) retrieveUser(dataSnapshot, findChild, DoctorDTO.class);
        Log.d(LOG_TAG, "Exiting retrieveUser...");
        return doctorDTO;
    }

    @Override
    public void saveAdditionalInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        Map<String, Object> updateMap = new HashMap<>();
        DoctorDTO doctorDTO = (DoctorDTO) userDTO;
        updateMap.put(CommonConstants.FIRST_NAME_COL, doctorDTO.getFirstName());
        updateMap.put(CommonConstants.LAST_NAME_COL, doctorDTO.getLastName());
        updateMap.put(CommonConstants.LOCATION_COL, doctorDTO.getLocation());
        update(updateMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getTableKey());
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
    }

    @Override
    public void saveRegisterInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformation...");
        DoctorDTO doctorDTO = (DoctorDTO) userDTO;
        doctorDTO.setRequesterPhoneNumber(CommonConstants.DEFAULT_REQUESTER_NUMBER);
        doctorDTO.setAvailable(false);
        doctorDTO.setVerifiedDoctor(false);
        doctorDTO.setRequested(false);
        insert(doctorDTO, CommonConstants.DOCTORS_TABLE);
        Log.d(LOG_TAG, "Exiting saveRegisterInformation...");
    }

    @Override
    public void updateDoctorToNotAvailable(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorToNotAvailable...");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(CommonConstants.REQUESTED_COL, true);
        updateMap.put(CommonConstants.AVAILABLE_COL, false);
        updateMap.put(CommonConstants.REQUESTER_PHONE_NUMBER_COL, doctorDTO.getRequesterPhoneNumber());
        update(updateMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getTableKey());
        Log.d(LOG_TAG, "Exiting updateDoctorToNotAvailable...");
    }

    @Override
    public void updateDoctorAvailability(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorAvailability...");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(CommonConstants.AVAILABLE_COL, doctorDTO.isAvailable());
        update(updateMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getTableKey());
        Log.d(LOG_TAG, "Exiting updateDoctorAvailability...");
    }

    @Override
    public List<DoctorDTO> retrieveAvailableDoctors(DataSnapshot snapshot) {
        Log.d(LOG_TAG, "Entering retrieveAvailableDoctors...");
        List<DoctorDTO> availableDoctors = new ArrayList<>();
        for (DataSnapshot user : snapshot.getChildren()) {
            DoctorDTO doctor = this.retrieveUser(user, false);
            if (doctor.isAvailable() && doctor.isVerifiedDoctor()) {
                availableDoctors.add(doctor);
            }
        }
        Log.d(LOG_TAG, "Exiting retrieveAvailableDoctors...");
        return availableDoctors;
    }
}
