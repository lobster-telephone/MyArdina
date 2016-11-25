package com.myardina.buckeyes.myardina.Sevice;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface UserService extends BaseService {

    /**
     * Retrieve a user from a given snapshot
     */
    UserDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild);

    /**
     * Retrieve a user based off of the id of the user from its respective table from the database
     * given a snapshot of the table.
     */
    UserDTO retrieveFromId(DataSnapshot snapshot, String id);

    /**
     * Save information entered on the Register page.
     */
    void saveRegisterInformation(UserDTO userDTO);

    /**
     * Save information entered on the Additional Information page.
     */
    void saveAdditionalInformation(UserDTO userDTO);
}
