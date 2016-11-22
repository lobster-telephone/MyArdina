package com.myardina.buckeyes.myardina.Sevice;

import com.myardina.buckeyes.myardina.DTO.UserDTO;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface UserService extends BaseService {

    /**
     * Retrieve a user based off of the id of the user from its respective table from the database.
     */
    UserDTO retrieveFromId(String id);
}
