package com.example.clinicmanager;

/**
 * Enum listing various radiology services available in the clinic.
 *
 * @author Sinan Merchant + Varun Bondugula
 */

public enum Radiology {
    CATSCAN("CAT Scan"),
    ULTRASOUND("Ultrasound"),
    XRAY("Xray");

    private final String serviceDescription;

    /**
     * Constructor for the Radiology enum.
     *
     * @param serviceDescription A string description of the imaging service.
     */
    Radiology(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    /**
     * Gets the description of the imaging service.
     *
     * @return The description of the imaging service.
     */
    @Override
    public String toString() {
        return serviceDescription;
    }
}
