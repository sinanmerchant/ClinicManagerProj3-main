package com.example.clinicmanager;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Represents a specific date, including year, month, and day with validation methods
 *
 * @author Varun Bondugula, Sinan Merchant
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;


    public static final int DAYS_SHORT = 30;
    public static final int DAYS_LONG = 31;
    public static final int DAYS_IN_FEBRUARY_NON_LEAP_YEAR = 28;
    public static final int DAYS_IN_FEBRUARY_LEAP_YEAR = 29;

    /**
     * Constructor to create an instance of the Date class.
     *
     * @param year the year of the date
     * @param month the month of the date
     * @param day the day of the date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Validates that the date exists, considering leap years
     *
     * @return true if the date is valid; false otherwise
     */
    public boolean isValid() {
        if (month < JANUARY || month > DECEMBER) {
            return false;
        }

        if (day < 1 || day > 31) {
            return false;
        }

        if (month == JANUARY || month == MARCH || month == MAY || month == JULY ||
                month == AUGUST || month == OCTOBER || month == DECEMBER) {
            return day <= DAYS_LONG;
        }
        else if (month == APRIL || month == JUNE || month == SEPTEMBER || month == NOVEMBER) {
            return day <= DAYS_SHORT;
        }
        else if (month == FEBRUARY) {
            if (isLeapYear()) {
                return day <= DAYS_IN_FEBRUARY_LEAP_YEAR;
            } else {
                return day <= DAYS_IN_FEBRUARY_NON_LEAP_YEAR;
            }
        }
        return false;
    }

    /**
     * Creates a Date instance from a LocalDate.
     *
     * @param localDate the LocalDate to convert
     * @return a Date instance representing the same date as the LocalDate
     */
    public static Date fromLocalDate(LocalDate localDate) {
        return new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    /**
     * Checks if the date is today or in the past
     *
     * @return true if the date is today or in the past; false otherwise
     */
    public boolean isTodayOrBefore() {
        Calendar today = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day); // Set the date to the appointment date

        return !date.after(today); // Return true if the date is today or in the past
    }

    /**
     * Checks if the date is today or in the future.
     *
     * @return true if the date is today or in the future; false otherwise
     */
    public boolean isTodayOrAfter() {
        Calendar today = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day);

        return !date.before(today);
    }

    /**
     * Checks if the date falls on a weekend
     *
     * @return true if the date falls on a weekend; false otherwise
     */
    public boolean isWeekend() {
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day); // Set the date to the appointment date

        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK); // Get the day of the week
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY; // Return true if Saturday or Sunday
    }

    /**
     * Checks if the date is within six months from today
     *
     * @return true if the date is within six months from today; false otherwise
     */
    public boolean isWithinSixMonths() {
        Calendar today = Calendar.getInstance();
        Calendar sixMonthsFromNow = Calendar.getInstance();
        sixMonthsFromNow.add(Calendar.MONTH, 6); // Add 6 months to today's date

        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day); // Set the date to the appointment date

        return date.after(today) && date.before(sixMonthsFromNow); // Check if date is within 6 months from today
    }

    /**
     * Determines if the year is a leap year.
     *
     * @return true if the year is a leap year; false otherwise
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAL == 0;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares this date with another date.
     *
     * @param other the date to compare to
     * @return a negative integer, zero, or a positive integer as this date is before, equal to, or after the other date
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        } else if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    /**
     * Checks if this date is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the object has the same year, month, and day; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;

        Date other = (Date) obj;
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }

    /**
     * Generates a string representation of the date in MM/DD/YYYY format.
     *
     * @return a formatted string of the date
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", month, day, year);
    }

    /**
     * Main testbed method for testing the Date class.
     *
     * @param args command line
     */
    public static void main(String[] args) {
    }
}