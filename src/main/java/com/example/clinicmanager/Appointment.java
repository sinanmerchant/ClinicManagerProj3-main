package com.example.clinicmanager;

/**
 * This class provides methods for getting the date, timeslot, patient, and provider of the appointment,
 * and implements Comparable for sorting and comparing appointments.
 * 
 * @author Sinan Merchant, Varun Bondugula
 */
public class Appointment implements Comparable<Appointment> {
    private Date date; // date
    private Timeslot timeslot; // timeslot
    private Person patient; // person
    private Person provider; // person

    /**
     * Constructs an instance of the Appointment class with the specified details.
     * 
     * @param date      the date of the appointment
     * @param timeslot  the timeslot of the appointment
     * @param patient   the patient for the appointment (of type Person)
     * @param provider  the provider for the appointment (of type Person)
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Returns the date of the appointment.
     * 
     * @return the date of the appointment
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the timeslot of the appointment.
     * 
     * @return the timeslot of the appointment
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Returns the patient of the appointment.
     * 
     * @return the patient of the appointment
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Returns the provider of the appointment.
     * 
     * @return the provider of the appointment
     */
    public Person getProvider() {
        return provider;
    }

    /**
     * Compares this appointment with another appointment for ordering.
     * The comparison is based on date, then timeslot, and finally patient.
     * 
     * @param other the appointment being compared
     * @return a negative integer, zero, or a positive integer if this appointment is less than, equal to, or greater than the specified appointment
     */
    @Override
    public int compareTo(Appointment other) {
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int timeslotComparison = this.timeslot.compareTo(other.timeslot);
        if (timeslotComparison != 0) {
            return timeslotComparison;
        }

        return this.patient.compareTo(other.patient);  // Assuming Person class implements Comparable
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj the object to compare
     * @return {@code true} if this appointment is equal to the specified object; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;

        Appointment other = (Appointment) obj;
        return this.date.equals(other.date)
                && this.timeslot.equals(other.timeslot)
                && this.patient.equals(other.patient);
    }

    /**
     * Returns a string representation of the appointment.
     * 
     * @return a string containing the details of the appointment (date, timeslot, patient, provider)
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                date.toString(),
                timeslot.toString(),
                patient.toString(),
                provider.toString());
    }

    /**
     * Main method for testing the Appointment class.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
    }
}
