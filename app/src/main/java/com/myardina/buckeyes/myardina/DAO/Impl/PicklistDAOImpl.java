package com.myardina.buckeyes.myardina.DAO.Impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DAO.PicklistDAO;
import com.myardina.buckeyes.myardina.DTO.PicklistDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Lacks on 11/19/2016.
 */
public class PicklistDAOImpl extends BaseDAOImpl implements PicklistDAO {

    private static final String LOG_TAG = "PICKLIST_DAO";

    @Override
    public List<PicklistDTO> retrievePicklist(DataSnapshot dataSnapshot, String picklistKey) {
        Log.d(LOG_TAG, "Entering retrievePicklist...");
        List<PicklistDTO> picklist = new ArrayList<>();

        for (DataSnapshot pick : dataSnapshot.getChildren()) {
            if (TextUtils.equals(pick.getKey(), picklistKey)) {
                for (DataSnapshot picklistItem : pick.getChildren()) {
                    PicklistDTO picklistDTO = (PicklistDTO) retrieve(picklistItem, PicklistDTO.class);
                    picklist.add(picklistDTO);
                }
                break;
            }
        }

        Log.d(LOG_TAG, "Exiting retrievePicklist...");
        return picklist;
    }
}
