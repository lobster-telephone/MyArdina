package com.myardina.buckeyes.myardina.Sevice.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DAO.DoctorDAO;
import com.myardina.buckeyes.myardina.DAO.Impl.DoctorDAOImpl;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;

import java.util.List;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class DoctorServiceImpl extends UserServiceImpl implements DoctorService {

    private static final String LOG_TAG = "DOCTOR_SERVICE_LOG";

    private DoctorDAO mDoctorDAO;

    public DoctorServiceImpl() {
        super();
        mDoctorDAO = new DoctorDAOImpl();
    }

    @Override
    public DoctorDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering retrieveUser...");
        DoctorDTO doctorDTO = (DoctorDTO) mDoctorDAO.retrieveUser(dataSnapshot, findChild);
        Log.d(LOG_TAG, "Exiting retrieveUser...");
        return doctorDTO;
    }

    @Override
    public DoctorDTO retrieveFromId(DataSnapshot snapshot, String id) {
        Log.d(LOG_TAG, "Entering retrieveFromId...");
        DoctorDTO result = (DoctorDTO) mDoctorDAO.retrieveUser(snapshot.child(id), false);
        Log.d(LOG_TAG, "Exiting retrieveFromId...");
        return result;
    }

    @Override
    public void saveRegisterInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformation...");
        mDoctorDAO.saveRegisterInformation(userDTO);
        Log.d(LOG_TAG, "Exiting saveRegisterInformation...");
    }

    @Override
    public void saveAdditionalInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        DoctorDTO doctorDTO = (DoctorDTO) userDTO;
        mDoctorDAO.saveAdditionalInformation(doctorDTO);
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
    }

    @Override
    public void updateDoctorToNotAvailable(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorToNotAvailable...");
        mDoctorDAO.updateDoctorToNotAvailable(doctorDTO);
        Log.d(LOG_TAG, "Exiting updateDoctorToNotAvailable...");
    }

    @Override
    public void updateDoctorAvailability(DoctorDTO doctorDTO) {
        Log.d(LOG_TAG, "Entering updateDoctorAvailability...");
        mDoctorDAO.updateDoctorAvailability(doctorDTO);
        Log.d(LOG_TAG, "Exiting updateDoctorAvailability...");
    }

    @Override
    public List<DoctorDTO> retrieveAvailableDoctors(DataSnapshot snapshot) {
        Log.d(LOG_TAG, "Entering retrieveAvailableDoctors...");
        List<DoctorDTO> availableDoctors = mDoctorDAO.retrieveAvailableDoctors(snapshot);
        Log.d(LOG_TAG, "Exiting retrieveAvailableDoctors...");
        return availableDoctors;
    }
}
