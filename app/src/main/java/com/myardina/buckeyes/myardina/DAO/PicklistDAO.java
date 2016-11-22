package com.myardina.buckeyes.myardina.DAO;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.PicklistDTO;

import java.util.List;

/**
 * @author Tyler Lacks on 11/19/2016.
 */
public interface PicklistDAO extends BaseDAO {

    List<PicklistDTO> retrievePicklist(DataSnapshot dataSnapshot, String picklistKey);

}
