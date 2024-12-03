package com.example.clinicmanager;

/**
 * Represents a patient with a profile and a linked list of their medical visits.
 *
 * @author Sinan Merchant + Varun Bondugula
 */

public class Patient extends Person {
    private Visit visit;

    /**
     * Constructor to create an instance of the Patient class.
     *
     * @param profile the profile of the patient
     * @param visits the linked list of visits for the patient
     */
    public Patient(Profile profile, Visit visits) {
        super(profile);
        this.visit = visits;
    }

    /**
     * Gets the visits of the patient.
     *
     * @return the list of visits
     */
    public Visit getVisits() {
        return visit;
    }

    /**
     * Checks if this patient is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the object is a Patient and has the same profile and visits; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;

        Patient other = (Patient) obj;
        return this.visit.equals(other.visit);
    }

    /**
     * Compares this patient to another patient based on their profiles and charges.
     *
     * @param other the patient to compare to
     * @return a negative integer, zero, or a positive integer as this patient is less than, equal to, or greater than the other patient
     */
    public int compareTo(Patient other) {
        int profileComparison = this.profile.compareTo(other.getProfile());
        if (profileComparison != 0) {
            return profileComparison;
        }

        return Integer.compare(this.charge(), other.charge());
    }

    /**
     * Computes the total charge for the visits
     *
     * @return the total charge for the visits
     */
    public int charge() {
        Visit currentVisit = this.visit;
        int totalCharge = 0;
        while (currentVisit != null) {
            Person provider = currentVisit.getAppointment().getProvider();
            if (provider instanceof Provider) {
                totalCharge += ((Provider) provider).rate();
            }
            currentVisit = currentVisit.getNext();
        }
        return totalCharge;
    }


    /**
     * Generates a string representation of the patient
     *
     * @return a string representation of the patient
     */
    @Override
    public String toString() {
        return "Patient: " + super.toString() + ", Total Charge: " + this.charge();
    }

    /**
     * Testbed main method
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
            // Create a Profile for the Patient
            Profile patientProfile = new Profile("Varun", "Bondugula", new Date(1989, 12, 13));
            Person patient = new Patient(patientProfile, null);

            // Create Profile for the Doctor
            Profile doctorProfile = new Profile("Dr. Smith", "Doctor", new Date(1970, 1, 1));
            Location doctorLocation = Location.BRIDGEWATER;
            Specialty doctorSpecialty = Specialty.FAMILY;
            Doctor doctor = new Doctor(doctorProfile, doctorLocation, doctorSpecialty, "NPI123");

            // Create Profile for the Technician
            Profile technicianProfile = new Profile("Jane", "Doe", new Date(1980, 3, 15));
            Location technicianLocation = Location.EDISON;
            Technician technician = new Technician(technicianProfile, technicianLocation, 150);

            // Create Appointment objects
            Appointment appointment1 = new Appointment(new Date(2024, 10, 30), Timeslot.getTimeslot(1), patient, doctor);
            Appointment appointment2 = new Appointment(new Date(2024, 11, 1), Timeslot.getTimeslot(1), patient, technician);

            // Create Visit nodes
            Visit visit1 = new Visit(appointment1);
            Visit visit2 = new Visit(appointment2);
            visit1.setNext(visit2);  // Link visits

            // Create a Patient with the linked list of visits
            patient = new Patient(patientProfile, visit1);


            // Expected output:
            // Patient: John Doe, Total Charge: [sum of charges]
            // Where sum of charges = doctor's rate + technician's rate
    }
}
