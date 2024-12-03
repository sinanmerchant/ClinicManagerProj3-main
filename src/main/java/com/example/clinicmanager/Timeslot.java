package com.example.clinicmanager;

/**
 * Represents a time slot for scheduling appointments with hour and minute details.
 *
 * @author Sinan Merchant + Varun Bondugula
 */

public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    /**
     * Constructor to initialize a timeslot with a specific hour and minute.
     *
     * @param hour   The hour of the timeslot (24-hour format).
     * @param minute The minute of the timeslot.
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets the hour of the timeslot.
     *
     * @return The hour of the timeslot (24-hour format).
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of the timeslot.
     *
     * @return The minute of the timeslot.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Compares this timeslot to another timeslot based on hour and minute.
     *
     * @param other The other timeslot to compare to.
     * @return A negative integer, zero, or a positive integer as this timeslot is earlier than,
     *         equal to, or later than the specified timeslot.
     */
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return this.hour - other.hour;
        }
        return this.minute - other.minute;
    }

    /**
     * Checks if this timeslot is equal to another object.
     *
     * @param obj The object to compare to.
     * @return true if the object is a Timeslot and has the same hour and minute; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Timeslot timeslot = (Timeslot) obj;
        return hour == timeslot.hour && minute == timeslot.minute;
    }

    /**
     * Returns a timeslot based on a number from 1 to 12.
     *
     * @param number The timeslot number
     * @return The corresponding Timeslot object.
     */
    public static Timeslot getTimeslot(int number) {
        switch (number) {
            case 1:
                return new Timeslot(9, 0);    // 9:00 AM
            case 2:
                return new Timeslot(9, 30);   // 9:30 AM
            case 3:
                return new Timeslot(10, 0);   // 10:00 AM
            case 4:
                return new Timeslot(10, 30);  // 10:30 AM
            case 5:
                return new Timeslot(11, 0);   // 11:00 AM
            case 6:
                return new Timeslot(11, 30);  // 11:30 AM
            case 7:
                return new Timeslot(14, 0);   // 2:00 PM
            case 8:
                return new Timeslot(14, 30);  // 2:30 PM
            case 9:
                return new Timeslot(15, 0);   // 3:00 PM
            case 10:
                return new Timeslot(15, 30);  // 3:30 PM
            case 11:
                return new Timeslot(16, 0);   // 4:00 PM
            case 12:
                return new Timeslot(16, 30);  // 4:30 PM
            default:
                throw new IllegalArgumentException("Invalid timeslot number.");
        }
    }

    /**
     * Provides a string representation of the timeslot in HH:MM AM/PM format.
     *
     * @return A formatted string of the timeslot.
     */
    @Override
    public String toString() {
        String period = "AM";
        int formattedHour = hour;
        if (hour >= 12) {
            period = "PM";
            if (formattedHour > 12) {
                formattedHour -= 12;
            }
        }
        if (formattedHour == 0) {
            formattedHour = 12;
        }
        return String.format("%d:%02d %s", formattedHour, minute, period);
    }

}
