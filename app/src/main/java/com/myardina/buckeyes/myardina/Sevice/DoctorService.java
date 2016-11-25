package com.myardina.buckeyes.myardina.Sevice;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;

import java.util.List;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface DoctorService extends UserService {

    /**
     * Update requested doctor information.
     */
    void updateDoctorToNotAvailable(DoctorDTO doctorDTO);

    /**
     * Update whether or not doctor is available.
     */
    void updateDoctorAvailability(DoctorDTO doctorDTO);

    /**
     * Retrieve a list of doctors that are verified and available.
     */
    List<DoctorDTO> retrieveAvailableDoctors(DataSnapshot snapshot);
}
