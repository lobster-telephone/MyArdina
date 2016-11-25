package com.myardina.buckeyes.myardina.DAO;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.BaseDTO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;

import java.util.Map;

/**
 * @author Tyler Lacks on 11/15/2016.
 */
public interface BaseDAO {

    void insert(BaseDTO baseDTO, String table);

    void update(Map<String, Object> insertMap, String table, String childId);

    Object retrieve(DataSnapshot snapshot, Class clazz);

    UserDTO retrieveUser(DataSnapshot dataSnapshot, boolean findChild, Class<? extends UserDTO> clazz);
}
