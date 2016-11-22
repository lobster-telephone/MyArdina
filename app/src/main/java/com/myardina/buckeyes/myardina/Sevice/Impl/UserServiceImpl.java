package com.myardina.buckeyes.myardina.Sevice.Impl;

import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.Sevice.UserService;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public abstract class UserServiceImpl extends BaseServiceImpl implements UserService {

   public abstract UserDTO retrieveFromId(String id);
}
