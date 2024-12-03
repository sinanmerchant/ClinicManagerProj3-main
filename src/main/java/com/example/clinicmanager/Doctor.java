package com.example.clinicmanager;

/**
 * Represents a healthcare provider specializing in a medical field with an NPI.
 *
 * @author Sinan Merchant + Varun Bondugula
 */
public class Doctor extends Provider {
    private Specialty specialty;
    private String npi;

    /**
     * Constructor to create a Doctor instance.
     *
     * @param profile  The profile of the doctor.
     * @param location The location of the doctor's practice.
     * @param specialty The specialty of the doctor.
     * @param npi The National Provider Identification number for the doctor.
     */
    public Doctor(Profile profile, Location location, Specialty specialty, String npi) {
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Gets the specialty of the doctor.
     *
     * @return The doctor's specialty.
     */
    public Specialty getSpecialty() {
        return specialty;
    }

    /**
     * Gets the NPI of the doctor.
     *
     * @return The NPI of the doctor.
     */
    public String getNpi() {
        return npi;
    }

    /**
     * Implements the abstract rate method.
     *
     * @return The rate per visit based on the doctor's specialty.
     */
    @Override
    public int rate() {
        return specialty.getCharge();
    }

    /**
     * Generates a string representation of the doctor.
     *
     * @return A string representation of the doctor.
     */
    @Override
    public String toString() {
        return String.format("[%s, %s, %s %s][%s, #%s]",
                profile.toString(),
                getLocation().toString(),
                getLocation().getCounty(),
                getLocation().getZip(),
                specialty.toString(),
                npi);
    }
}
