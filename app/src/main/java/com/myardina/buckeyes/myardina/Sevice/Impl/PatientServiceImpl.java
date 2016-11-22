package com.myardina.buckeyes.myardina.Sevice.Impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.PatientDAO;
import com.myardina.buckeyes.myardina.DAO.Impl.PatientDAOImpl;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.Sevice.PatientService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class PatientServiceImpl extends UserServiceImpl implements PatientService {

    private PatientDAO mPatientDAO;
    private PatientDTO mPatientDTO;

    public PatientServiceImpl() {
        super();
        mPatientDAO = new PatientDAOImpl();
    }

    @Override
    public PatientDTO retrieveFromId(String id) {
        mPatientDTO = null;
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPatientDTO = (PatientDTO) mPatientDAO.retrieveUser(dataSnapshot, false);
            }

            @Override public void onCancelled(DatabaseError databaseError) { }
        };
        applyListener(listener, CommonConstants.PATIENTS_TABLE);
        return mPatientDTO;
    }
}

