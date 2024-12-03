package com.example.clinicmanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the validity and comparison methods of the Date class using JUnit.
 *
 * @author Sinan Merchant + Varun Bondugula
 */
public class DateTest {

    /**
     * Test case for a valid date in a leap year.
     */
    @Test
    public void testValidLeapYearDate() {
        Date validLeapYearDate = new Date(2024, 2, 29);
        assertTrue(validLeapYearDate.isValid());
    }

    /**
     * Test case for an invalid date in a non leap year.
     */
    @Test
    public void testInvalidLeapYearDate() {
        Date invalidLeapYearDate = new Date(2023, 2, 29);
        assertFalse(invalidLeapYearDate.isValid());
    }

    /**
     * Test case for a valid date in a month with 31 days.
     */
    @Test
    public void testValid31DayMonthDate() {
        Date valid31DayMonthDate = new Date(2023, 12, 31);
        assertTrue(valid31DayMonthDate.isValid());
    }

    /**
     * Test case for an invalid date in a month with 30 days.
     */
    @Test
    public void testInvalidAprilDate() {
        Date invalidAprilDate = new Date(2023, 4, 31);
        assertFalse(invalidAprilDate.isValid());
    }

    /**
     * Test case for an invalid day value.
     */
    @Test
    public void testInvalidDay() {
        Date invalidDay = new Date(2023, 3, 0);
        assertFalse(invalidDay.isValid());
    }

    /**
     * Test case for an invalid month value.
     */
    @Test
    public void testInvalidMonth() {
        Date invalidMonth = new Date(2023, 13, 15);
        assertFalse(invalidMonth.isValid());
    }

    /**
     * Test case for a valid date in a month with 30 days.
     */
    @Test
    public void testValid30DayMonthDate() {
        Date valid30DayMonthDate = new Date(2023, 9, 30);
        assertTrue(valid30DayMonthDate.isValid());
    }

    /**
     * Test case for an invalid date in February.
     */
    @Test
    public void testInvalidFebruaryDate() {
        Date invalidFebruaryDate = new Date(2020, 2, 30);
        assertFalse(invalidFebruaryDate.isValid());
    }

}