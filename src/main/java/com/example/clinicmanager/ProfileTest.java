package com.example.clinicmanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the compareTo method in the Profile class.
 * 
 * @author Sinan Merchant + Varun Bonduluga
 */
public class ProfileTest {

    /**
     * Test case where compareTo returns -1.
     */
    @Test
    public void testCompareToReturnsNegative() {
        Profile profile1 = new Profile("John", "Doe", new Date(1990, 5, 10));
        Profile profile2 = new Profile("John", "Smith", new Date(1990, 5, 10));
        assertEquals(-1, profile1.compareTo(profile2));

        Profile profile3 = new Profile("Alice", "Doe", new Date(1990, 5, 10));
        Profile profile4 = new Profile("John", "Doe", new Date(1990, 5, 10));
        assertEquals(-1, profile3.compareTo(profile4));

        Profile profile5 = new Profile("John", "Doe", new Date(1990, 5, 10));
        Profile profile6 = new Profile("John", "Doe", new Date(1991, 5, 10));
        assertEquals(-1, profile5.compareTo(profile6));
    }

    /**
     * Test case where compareTo returns 1.
     */
    @Test
    public void testCompareToReturnsPositive() {
        Profile profile1 = new Profile("John", "Smith", new Date(1990, 5, 10));
        Profile profile2 = new Profile("John", "Doe", new Date(1990, 5, 10));
        assertEquals(1, profile1.compareTo(profile2));

        Profile profile3 = new Profile("John", "Doe", new Date(1990, 5, 10));
        Profile profile4 = new Profile("Alice", "Doe", new Date(1990, 5, 10));
        assertEquals(1, profile3.compareTo(profile4));

        Profile profile5 = new Profile("John", "Doe", new Date(1991, 5, 10));
        Profile profile6 = new Profile("John", "Doe", new Date(1990, 5, 10));
        assertEquals(1, profile5.compareTo(profile6));
    }

    /**
     * Test case where compareTo returns 0.
     */
    @Test
    public void testCompareToReturnsZero() {
        Profile profile1 = new Profile("John", "Doe", new Date(1990, 5, 10));
        Profile profile2 = new Profile("John", "Doe", new Date(1990, 5, 10));
        assertEquals(0, profile1.compareTo(profile2));
    }
}