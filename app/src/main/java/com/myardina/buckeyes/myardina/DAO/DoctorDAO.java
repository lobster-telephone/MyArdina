package com.myardina.buckeyes.myardina.DAO;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;

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
    public void updateDoctorToNotAvailable(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorToNotAvailable...");
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put(CommonConstants.REQUESTED_COL, true);
        insertMap.put(CommonConstants.AVAILABLE_COL, false);
        insertMap.put(CommonConstants.REQUESTER_PHONE_NUMBER_COL, doctorDTO.getRequesterPhoneNumber());
        update(insertMap, CommonConstants.DOCTORS_TABLE, doctorDTO.getUserKey());
        Log.d(LOG_TAG, "Exiting updateDoctorToNotAvailable...");
    }

    public List<DoctorDTO> retrieveAvailableDoctors(DataSnapshot snapshot) {
        List<DoctorDTO> availableDoctors = new ArrayList<>();

        for (DataSnapshot user : snapshot.getChildren()) {
            DoctorDTO doctor = retrieveDoctor(user, false);
            if (doctor.isAvailable() && doctor.isVerifiedDoctor()) {
                availableDoctors.add(doctor);
            }
        }

        return availableDoctors;
    }
}
