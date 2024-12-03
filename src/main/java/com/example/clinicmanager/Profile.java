package com.example.clinicmanager;

/**
 * Profile class represents a patient's profile with first name, last name, and date of birth.
 * It implements Comparable to allow sorting based on last name, first name, and date of birth.
 *
 * @author Sinan Merchant + Varun Bondugula
 */
public class Profile implements Comparable<Profile> {
    private String fname; // First name of the patient
    private String lname; // Last name of the patient
    private Date dob;     // Date of birth of the patient

    /**
     * Constructor to initialize a Profile object with first name, last name, and date of birth.
     *
     * @param fname First name of the patient.
     * @param lname Last name of the patient.
     * @param dob   Date of birth of the patient.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Overrides the equals method to compare profiles.
     * Two profiles are equal if they have the same first name, last name, and date of birth.
     *
     * @param obj The object to compare against.
     * @return true if profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return fname.equalsIgnoreCase(profile.fname) &&
                lname.equalsIgnoreCase(profile.lname) &&
                dob.equals(profile.dob);
    }

    /**
     * Getter method for first name
     *
     * @return last name
     */
    public String getFirstName() {
        return this.fname;
    }

    /**
     * Getter method for last name
     *
     * @return last name
     */
    public String getLastName() {
        return this.lname;
    }

    /**
     * Overrides the toString method to provide a textual representation of the profile.
     *
     * @return A string representation of the profile.
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Compares this profile with another profile for sorting purposes.
     * Sorting is based on last name, then first name, then date of birth.
     *
     * @param other The other profile to compare against.
     * @return -1, 0, or 1 as this profile is less than, equal to, or greater than the specified profile.
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = this.lname.compareTo(other.lname);
        if (lastNameComparison != 0) return Integer.signum(lastNameComparison);

        int firstNameComparison = this.fname.compareTo(other.fname);
        if (firstNameComparison != 0) return Integer.signum(firstNameComparison);

        return Integer.signum(this.dob.compareTo(other.dob));
    }

    /**
     * Main testbed method for testing the Profile class.
     *
     * @param args command line
     */
    public static void main(String[] args) {
    }


}