package com.myardina.buckeyes.myardina.Sevice;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.PicklistDTO;

import java.util.List;

/**
 * @author Tyler Lacks on 11/19/2016.
 */
public interface PicklistService extends BaseService {

    List<PicklistDTO> getPicklist(DataSnapshot snapshot, String picklistKey);

}
