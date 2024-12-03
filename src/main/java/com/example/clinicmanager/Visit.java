package com.example.clinicmanager;

/**
 * The Visit class defines a node in a singly linked list that maintains the list of visits.
 * Each visit node contains an Appointment object and a reference to the next visit in the list.
 *
 * @author Sinan Merchant + Varun Bonduluga
 */
public class Visit {
    private Appointment appointment; // A reference to an appointment object
    private Visit next; // A reference to the next visit object in the list

    /**
     * Constructor to create a Visit object with a specified appointment.
     *
     * @param appointment The appointment associated with this visit.
     */
    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null;
    }

    /**
     * Gets the appointment associated with this visit.
     *
     * @return The appointment of this visit.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Gets the next visit in the list.
     *
     * @return The next visit node.
     */
    public Visit getNext() {
        return next;
    }

    /**
     * Sets the next visit in the list.
     *
     * @param next The next visit node to link.
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * Generates a string representation of the {@code Visit} object.
     *
     * @return A string representation of the visit, including its appointment details.
     */
    @Override
    public String toString() {
        return appointment.toString();
    }
}