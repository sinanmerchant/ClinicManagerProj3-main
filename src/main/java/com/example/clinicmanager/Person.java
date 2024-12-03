package com.example.clinicmanager;

/**
 * A base class modeling a person with a profile, used as a superclass for other roles.
 *
 * @author Sinan Merchant + Varun Bondugula
 */

public class Person implements Comparable<Person> {
    protected Profile profile;

    /**
     * Constructor to create a Person instance.
     *
     * @param profile the profile of the person
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Gets the profile of the person.
     *
     * @return the profile of the person
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Compares this Person to another Person based on their profiles.
     *
     * @param other the other person to compare to
     * @return a negative integer, zero, or a positive integer as this person is less than, equal to, or greater than the other person
     */
    @Override
    public int compareTo(Person other) {
        return this.profile.compareTo(other.profile);
    }

    /**
     * Checks if this Person is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the object is a Person and has the same profile; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;

        Person other = (Person) obj;
        return this.profile.equals(other.profile);
    }

    /**
     * Generates a string representation of the Person class
     *
     * @return a string representation of the person
     */
    @Override
    public String toString() {
        return profile.toString();
    }
}
