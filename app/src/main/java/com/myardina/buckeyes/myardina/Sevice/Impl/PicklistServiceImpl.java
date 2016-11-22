package com.myardina.buckeyes.myardina.Sevice.Impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.Impl.PicklistDAOImpl;
import com.myardina.buckeyes.myardina.DAO.PicklistDAO;
import com.myardina.buckeyes.myardina.DTO.PicklistDTO;
import com.myardina.buckeyes.myardina.Sevice.PicklistService;

import java.util.List;

/**
 * @author Tyler Lacks on 11/19/2016.
 */
public class PicklistServiceImpl extends BaseServiceImpl implements PicklistService {

    private PicklistDAO mPicklistDAO;

    public PicklistServiceImpl() {
        super();
        mPicklistDAO = new PicklistDAOImpl();
    }

    @Override
    public List<PicklistDTO> getPicklist(String picklistKey) {
        PicklistHelper picklistHelper = new PicklistHelper(picklistKey);
        List<PicklistDTO> picklist = picklistHelper.getPicklist();
        return picklist;
    }

    public class PicklistHelper {
        private List<PicklistDTO> picklist;
        private DatabaseReference mRef;

        public PicklistHelper(final String picklistKey) {
            picklist = null;
            mRef = getDatabaseReference(CommonConstants.PICKLIST_TABLE);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    picklist = mPicklistDAO.retrievePicklist(dataSnapshot, picklistKey);
                    mRef.removeEventListener(this);
                }
                @Override public void onCancelled(DatabaseError databaseError) { }
            });
        }

        public List<PicklistDTO> getPicklist() { return picklist; }
    }
}
