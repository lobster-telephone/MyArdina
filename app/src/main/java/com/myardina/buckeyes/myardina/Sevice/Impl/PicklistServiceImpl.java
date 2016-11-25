package com.myardina.buckeyes.myardina.Sevice.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DAO.Impl.PicklistDAOImpl;
import com.myardina.buckeyes.myardina.DAO.PicklistDAO;
import com.myardina.buckeyes.myardina.DTO.PicklistDTO;
import com.myardina.buckeyes.myardina.Sevice.PicklistService;

import java.util.List;

/**
 * @author Tyler Lacks on 11/19/2016.
 */
public class PicklistServiceImpl extends BaseServiceImpl implements PicklistService {

    private static final String LOG_TAG = "BASE_SERVICE";

    private PicklistDAO mPicklistDAO;

    public PicklistServiceImpl() {
        super();
        mPicklistDAO = new PicklistDAOImpl();
    }

    @Override
    public List<PicklistDTO> getPicklist(DataSnapshot snapshot, String picklistKey) {
        Log.d(LOG_TAG, "Entering getPicklist...");
        List<PicklistDTO> picklist = mPicklistDAO.retrievePicklist(snapshot, picklistKey);
        Log.d(LOG_TAG, "Exiting getPicklist...");
        return picklist;
    }

}
