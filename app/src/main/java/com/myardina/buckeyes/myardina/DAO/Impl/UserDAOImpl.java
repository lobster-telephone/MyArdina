package com.myardina.buckeyes.myardina.DAO.Impl;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DAO.UserDAO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

/**
 * @author by Tyler on 10/26/2016.
 */
public abstract class UserDAOImpl extends BaseDAOImpl implements UserDAO {

    public abstract UserDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild);

    public abstract void saveAdditionalInformation(UserDTO userDTO);

    public abstract void saveRegisterInformation(UserDTO userDTO);

}
