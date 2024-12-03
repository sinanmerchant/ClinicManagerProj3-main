package com.example.clinicmanager;

/**
 * Specialty enum defines the provider specialties and their associated charges per visit.
 * This enum is used to associate medical charges with specific specialties.
 *
 * @author Sinan Merchant + Varun Bondugula
 */
public enum Specialty {
    FAMILY(250),            // Family medicine specialty with charge per visit
    PEDIATRICIAN(300),      // Pediatric specialty with charge per visit
    ALLERGIST(350);         // Allergy specialty with charge per visit

    private final int charge; // Charge associated with the specialty

    /**
     * Constructor to initialize a specialty with a specific charge per visit.
     *
     * @param charge The charge associated with the specialty.
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Gets the charge associated with the specialty.
     *
     * @return The charge per visit for the specialty.
     */
    public int getCharge() {
        return charge;
    }
}