package com.myardina.buckeyes.myardina.Sevice.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DAO.Impl.PatientDAOImpl;
import com.myardina.buckeyes.myardina.DAO.PatientDAO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.Sevice.PatientService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class PatientServiceImpl extends UserServiceImpl implements PatientService {

    private static final String LOG_TAG = "PATIENT_SERVICE_LOG";

    private PatientDAO mPatientDAO;

    public PatientServiceImpl() {
        super();
        mPatientDAO = new PatientDAOImpl();
    }

    @Override
    public PatientDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild) {
        Log.d(LOG_TAG, "Entering retrieveUser...");
        PatientDTO patientDTO = (PatientDTO) mPatientDAO.retrieveUser(dataSnapshot, findChild);
        Log.d(LOG_TAG, "Exiting retrieveUser...");
        return patientDTO;
    }

    @Override
    public void saveRegisterInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveRegisterInformation...");
        mPatientDAO.saveRegisterInformation(userDTO);
        Log.d(LOG_TAG, "Exiting saveRegisterInformation...");
    }

    @Override
    public PatientDTO retrieveFromId(DataSnapshot snapshot, String id) {
        Log.d(LOG_TAG, "Entering retrieveFromId...");
        PatientDTO result = (PatientDTO) mPatientDAO.retrieveUser(snapshot.child(id), false);
        Log.d(LOG_TAG, "Exiting retrieveFromId...");
        return result;
    }

    @Override
    public void saveAdditionalInformation(UserDTO userDTO) {
        Log.d(LOG_TAG, "Entering saveAdditionalInformation...");
        PatientDTO patientDTO = (PatientDTO) userDTO;
        mPatientDAO.saveAdditionalInformation(patientDTO);
        Log.d(LOG_TAG, "Exiting saveAdditionalInformation...");
    }
}

