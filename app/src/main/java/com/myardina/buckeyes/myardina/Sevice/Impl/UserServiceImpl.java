package com.myardina.buckeyes.myardina.Sevice.Impl;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.Sevice.UserService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public abstract class UserServiceImpl extends BaseServiceImpl implements UserService {

    public abstract UserDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild);

    public abstract UserDTO retrieveFromId(DataSnapshot snapshot, String id);

    public abstract void saveRegisterInformation(UserDTO userDTO);

    public abstract void saveAdditionalInformation(UserDTO userDTO);
}
