package com.example.clinicmanager;

/**
 * Location enum defines the clinic locations with their county and zip code information.
 * This enum is used to associate providers with specific locations.
 *
 * @author Sinan Merchant + Varun Bondugula
 */
public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    private final String county; // County where the location is situated
    private final String zip;    // Zip code of the location

    /**
     * Constructor to initialize a location with county and zip code.
     *
     * @param county The county of the location.
     * @param zip    The zip code of the location.
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Gets the county of the location.
     *
     * @return The county of the location.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gets the zip code of the location.
     *
     * @return The zip code of the location.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Gets the name of the location.
     *
     * @return The name of the location.
     */
    public String getName() {
        return this.name();
    }
}
