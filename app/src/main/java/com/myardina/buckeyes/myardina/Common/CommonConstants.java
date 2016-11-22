package com.myardina.buckeyes.myardina.Common;

/**
 * @author Tyler Lacks 10/9/2016.
 */
public class CommonConstants {

    // GENERAL CONSTANTS
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String USD = "USD";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String USER_ACCOUNT_ID = "userAccountId";
    public static final String DOCTOR_DTO = "DOCTOR_DTO";
    public static final String PATIENT_DTO = "PATIENT_DTO";
    public static final String PAYMENT_DTO = "PAYMENT_DTO";
    public static final String ADMIN_DTO = "ADMIN_DTO";

    // DATABASE CONSTANTS
    public static final String FIRST_NAME_COL = "firstName";
    public static final String LAST_NAME_COL = "lastName";
    public static final String REQUESTER_PHONE_NUMBER_COL = "requesterPhoneNumber";
    public static final String AVAILABLE_COL = "available";
    public static final String VERIFIED_DOCTOR_COL = "verifiedDoctor";
    public static final String REQUESTED_COL = "requested";
    public static final String EMAIL_COL = "email";
    public static final String LOCATION_COL = "location";
    public static final String PHONE_NUMBER_COL = "phoneNumber";
    public static final String DOCTORS_TABLE = "Doctors";
    public static final String PATIENTS_TABLE = "Patients";
    public static final String PAYMENTS_TABLE = "Payments";
    public static final String PICKLIST_TABLE = "Picklist";

    // LOGIN CONSTANTS
    public static final String DUMMY_PATIENT_EMAIL = "a@p.com";
    public static final String DUMMY_PATIENT_PASSWORD = "Dummy1";
    public static final String DUMMY_DOCTOR_EMAIL = "a@d.com";
    public static final String DUMMY_DOCTOR_PASSWORD = "Dummy1";

    // REGISTER CONSTANTS
    public static final String DEFAULT_REQUESTER_NUMBER = "0000000000";

    // SYMPTOMS CONSTANTS
    public static final String MY_HEAD = "my_head";
    public static final String MY_CHEST = "mv_chest";
    public static final String MY_ABDOMEN = "mv_abdomen";
    public static final String MY_ARM = "mv_arm";
    public static final String MY_LEGS = "mv_legs";
    public static final String BODY_PART = "body_part";
    public static final String SYMPTOMS_PICKER = "symptomsPicker";

    // PATIENT PAYMENT ACTIVITY
    public static final String FLAT_RATE = "79.00";
    public static final String DOCTOR_TELEMEDICINE = "doctor telemedicine";
    public static final String CONFIG_CLIENT_ID_DEVELOPMENT = "ASXfMywB-eKIJKk2fSZ9ydmY6L_g3LKLEcG1JScixKR9-t1X_cTMrTbQg5fhv-FsPwdzH3c4RNHmOjs6";
    // WILL BE DETERMINED WHEN RELEASING APPLICATION TO STORE
//    public static final String CONFIG_CLIENT_ID_RELEASE = "";
//    public static final int REQUEST_CODE_PAYMENT = 1;
//    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
//    public static final int REQUEST_CODE_PROFILE_SHARING = 3;

    // DOCTORS AVAILABLE ACTIVITY
    public static final String NO_DOCTORS_MESSAGE = "No doctors are currently available.";

    // ADMIN ACTIVITY
    public static final String NO_PAYMENTS_MESSAGE = "No payments are currently pending.";
    public static final String FROM = "FROM";

    // PICKLIST KEYS
    public static final String ADMINS_PICKLIST = "Admins";
}
