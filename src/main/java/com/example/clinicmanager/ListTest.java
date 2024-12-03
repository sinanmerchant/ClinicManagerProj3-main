package com.example.clinicmanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the add and remove methods in the List<E> class.
 * 
 * @author Sinan Merchant + Varun Bonduluga
 */
public class ListTest {

    /**
     * Test case for adding a Doctor object to a List<Provider> object.
     */
    @Test
    public void testAddDoctor() {
        List<Provider> providerList = new List<>();
        Profile doctorProfile = new Profile("John", "Doe", new Date(1975, 3, 15));
        Doctor doctor = new Doctor(doctorProfile, Location.BRIDGEWATER, Specialty.FAMILY, "NPI123");
        
        providerList.add(doctor);
        assertTrue(providerList.contains(doctor));
        assertEquals(1, providerList.size());
    }

    /**
     * Test case for adding a Technician object to a List<Provider> object.
     */
    @Test
    public void testAddTechnician() {
        List<Provider> providerList = new List<>();
        Profile technicianProfile = new Profile("Jane", "Smith", new Date(1980, 7, 20));
        Technician technician = new Technician(technicianProfile, Location.EDISON, 150);
        
        providerList.add(technician);
        assertTrue(providerList.contains(technician));
        assertEquals(1, providerList.size());
    }

    /**
     * Test case for removing a Doctor object from a List<Provider> object.
     */
    @Test
    public void testRemoveDoctor() {
        List<Provider> providerList = new List<>();
        Profile doctorProfile = new Profile("John", "Doe", new Date(1975, 3, 15));
        Doctor doctor = new Doctor(doctorProfile, Location.BRIDGEWATER, Specialty.FAMILY, "NPI123");
        
        providerList.add(doctor);
        providerList.remove(doctor);
        assertFalse(providerList.contains(doctor));
        assertEquals(0, providerList.size());
    }

    /**
     * Test case for removing a Technician object from a List<Provider> object.
     */
    @Test
    public void testRemoveTechnician() {
        List<Provider> providerList = new List<>();
        Profile technicianProfile = new Profile("Jane", "Smith", new Date(1980, 7, 20));
        Technician technician = new Technician(technicianProfile, Location.EDISON, 150);
        
        providerList.add(technician);
        providerList.remove(technician);
        assertFalse(providerList.contains(technician));
        assertEquals(0, providerList.size());
    }
}
