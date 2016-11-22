package com.myardina.buckeyes.myardina.Sevice.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.DoctorDAO;
import com.myardina.buckeyes.myardina.DAO.Impl.DoctorDAOImpl;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class DoctorServiceImpl extends UserServiceImpl implements DoctorService {

    private static final String LOG_TAG = "DOCTOR_SERVICE_LOG";

    private DoctorDAO mDoctorDAO;
    private DoctorDTO mDoctorDTO;

    public DoctorServiceImpl() {
        super();
        mDoctorDAO = new DoctorDAOImpl();
    }

    @Override
    public DoctorDTO retrieveFromId(String id) {
        Log.d(LOG_TAG, "Entering retrieveFromId...");
        mDoctorDTO = null;
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDoctorDTO = (DoctorDTO) mDoctorDAO.retrieveUser(dataSnapshot, false);
            }

            @Override public void onCancelled(DatabaseError databaseError) { }
        };
        applyListener(listener, CommonConstants.DOCTORS_TABLE);
        Log.d(LOG_TAG, "Exiting retrieveFromId...");
        return mDoctorDTO;
    }
}
