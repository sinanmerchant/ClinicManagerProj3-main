package com.example.clinicmanager;

/**
 * Models an imaging appointment, including the type of radiology service.
 *
 * @author Sinan Merchant + Varun Bondugula
 */
public class Imaging extends Appointment {
    private Radiology room;

    /**
     * Constructor to create an instance of the Imaging class.
     *
     * @param date The date of the imaging appointment.
     * @param timeslot The timeslot of the imaging appointment.
     * @param patient The patient for the imaging appointment.
     * @param provider The provider for the imaging appointment.
     * @param room The radiology room (X-ray, ultrasound, or CAT scan).
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    /**
     * Gets the radiology room for the imaging appointment.
     *
     * @return The radiology room (X-ray, ultrasound, or CAT scan).
     */
    public Radiology getRoom() {
        return room;
    }

    /**
     * Generates a string representation of the Imaging class.
     *
     * @return A string representation of the Imaging appointment.
     */
    @Override
    public String toString() {
        return super.toString() + "[" + room.toString().toUpperCase() + "]";
    }

    /**
     * Main method to test the Imaging class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Profile patientProfile = new Profile("Varun", "Bondugula", new Date(1989, 12, 13));
        Person patient = new Patient(patientProfile, null);

        Profile providerProfile = new Profile("Dr. Patel", "Provider", new Date(1975, 6, 22));
        Person provider = new Doctor(providerProfile, Location.BRIDGEWATER, Specialty.FAMILY, "124829213");

        Radiology xrayRoom = Radiology.XRAY;

        Imaging imagingAppointment = new Imaging(new Date(2024, 10, 3), Timeslot.getTimeslot(1), patient, provider, xrayRoom);
    }
}
