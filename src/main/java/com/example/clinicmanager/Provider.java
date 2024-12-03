package com.example.clinicmanager;

/**
 * An abstract class for healthcare providers associated with a location.
 *
 * @author Sinan Merchant + Varun Bondugula
 */

public abstract class Provider extends Person {
    private Location location;

    /**
     * Constructor to initialize a provider with a profile and specific location.
     *
     * @param profile  The profile of the provider.
     * @param location The location of the provider.
     */
    public Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
    }

    /**
     * Gets the location of the provider.
     *
     * @return The location of the provider.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Generates a string representation of the provider.
     *
     * @return a string representation of the provider.
     */
    @Override
    public String toString() {
        return super.toString() + ", Location: " + location.toString();
    }

    /**
     * Abstract method to get the rate charged by the provider.
     *
     * @return the rate charged per visit.
     */
    public abstract int rate();
}
