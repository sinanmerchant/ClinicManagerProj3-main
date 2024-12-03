package com.example.clinicmanager;

/**
 * Models a technician who provides services at a set rate per visit.
 *
 * @author Sinan Merchant + Varun Bondugula
 */

public class Technician extends Provider {
    private int ratePerVisit;

    /**
     * Constructor to create a Technician instance.
     *
     * @param profile  The profile of the technician.
     * @param location The location of the technician's practice.
     * @param ratePerVisit The rate per visit for the technician.
     */
    public Technician(Profile profile, Location location, int ratePerVisit) {
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Returns the rate per visit for the technician.
     *
     * @return The rate per visit for the technician.
     */
    @Override
    public int rate() {
        return ratePerVisit;
    }

    /**
     * Generates a string representation of the technician.
     *
     * @return A string representation of the technician.
     */
    @Override
    public String toString() {
        return String.format("[%s, %s, %s %s][rate: $%.2f]",
                profile.toString(),
                getLocation().toString(),
                getLocation().getCounty(),
                getLocation().getZip(),
                (double) ratePerVisit);
    }
}
