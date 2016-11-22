package com.myardina.buckeyes.myardina.DAO;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;

import java.util.List;

/**
 * @author Tyler Lacks on 11/17/2016.
 */
public interface DoctorDAO extends UserDAO {

    /**
     * Update requested doctor information
     */
    void updateDoctorToNotAvailable(DoctorDTO doctorDTO);

    /**
     * Update whether or not doctor is available
     */
    void updateDoctorAvailability(DoctorDTO doctorDTO);

    /**
     * Retrieve a list of all doctors that are available and verified
     */
    List<DoctorDTO> retrieveAvailableDoctors(DataSnapshot snapshot);
}
