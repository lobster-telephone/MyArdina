package com.myardina.buckeyes.myardina.DAO;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

/**
 * @author Tyler Lacks on 11/17/2016.
 */
public interface UserDAO {

    /**
     * Retrieve all user information from the database given a snapshot from an event
     * listener and specify whether the child needs found from the Users table first.
     */
    UserDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild);

    /**
     * Save information collected on the Additional Information Activity for Doctors
     */
    void saveAdditionalInformation(UserDTO userDTO);

    /**
     * Save information collected on the Register Activity for patients
     */
    void saveRegisterInformation(UserDTO userDTO);
}
