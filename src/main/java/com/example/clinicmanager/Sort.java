package com.example.clinicmanager;

/**
 * Utility class to handle sorting of appointments and providers based on various keys.
 * This class provides static methods to sort lists of appointments and providers.
 *
 * @author Varun Bondugula + Sinan Merchant
 */
public class Sort {

    /**
     * Sorts a list of appointments based on the specified key.
     * Key options:
     * - 'P' sorts by patient profile, date/timeslot.
     * - 'L' sorts by provider's location (county), date/timeslot.
     * - 'A' sorts by date/timeslot, provider name.
     * - 'O' sorts office appointments by county, date and time.
     * - 'I' sorts imaging appointments by county, date and time.
     *
     * @param list The list of appointments to be sorted.
     * @param key The sorting key ('P', 'L', 'A', 'O', 'I').
     */
    public static void appointment(List<Appointment> list, char key) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Appointment a1 = list.get(j);
                Appointment a2 = list.get(j + 1);
                if (compareAppointments(a1, a2, key) > 0) {
                    // Swap the appointments if they are in the wrong order
                    Appointment temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Compares two appointments based on the specified key.
     *
     * @param a1 The first appointment.
     * @param a2 The second appointment.
     * @param key The sorting key ('P', 'L', 'A', 'O', 'I').
     * @return A positive value if a1 > a2, a negative value if a1 < a2, and 0 if they are equal.
     */
    private static int compareAppointments(Appointment a1, Appointment a2, char key) {
        switch (key) {
            case 'P': // Sort by patient profile, then date and timeslot
                int patientComparison = a1.getPatient().compareTo(a2.getPatient());
                if (patientComparison != 0) {
                    return patientComparison;
                }
                return compareByDateAndTimeslot(a1, a2);

            case 'L': // Sort by provider's location (county), then date and timeslot
                Provider provider1 = (Provider) a1.getProvider();
                Provider provider2 = (Provider) a2.getProvider();

                int locationComparison = provider1.getLocation().getCounty()
                        .compareTo(provider2.getLocation().getCounty());
                if (locationComparison != 0) {
                    return locationComparison;
                }
                return compareByDateAndTimeslot(a1, a2);

            case 'A': // Sort by date/timeslot, then provider name
                int dateComparison = a1.getDate().compareTo(a2.getDate());
                if (dateComparison != 0) {
                    return dateComparison;
                }
                int timeslotComparison = a1.getTimeslot().compareTo(a2.getTimeslot());
                if (timeslotComparison != 0) {
                    return timeslotComparison;
                }
                return a1.getProvider().getProfile().compareTo(a2.getProvider().getProfile());

            case 'O': // Sort by county, then date and time (only for office appointments)
                return compareByLocationAndDate(a1, a2);

            case 'I': // Sort by county, then date and time (only for imaging appointments)
                return compareByLocationAndDate(a1, a2);

            default:
                throw new IllegalArgumentException("Invalid sort key");
        }
    }

    /**
     * Helper method to compare two appointments by date and timeslot.
     *
     * @param a1 The first appointment.
     * @param a2 The second appointment.
     * @return A positive value if a1 > a2, a negative value if a1 < a2, and 0 if they are equal.
     */
    private static int compareByDateAndTimeslot(Appointment a1, Appointment a2) {
        int dateComparison = a1.getDate().compareTo(a2.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        }
        int timeslotComparison = a1.getTimeslot().compareTo(a2.getTimeslot());
        if (timeslotComparison != 0) {
            return timeslotComparison;
        }
        return a1.getProvider().getProfile().getFirstName().compareTo(a2.getProvider().getProfile().getFirstName());
    }

    /**
     * Helper method to compare two appointments by provider's location (county) and date.
     *
     * @param a1 The first appointment.
     * @param a2 The second appointment.
     * @return A positive value if a1 > a2, a negative value if a1 < a2, and 0 if they are equal.
     */
    private static int compareByLocationAndDate(Appointment a1, Appointment a2) {
        Provider provider1 = (Provider) a1.getProvider();
        Provider provider2 = (Provider) a2.getProvider();

        int locationComparison = provider1.getLocation().getCounty()
                .compareTo(provider2.getLocation().getCounty());
        if (locationComparison != 0) {
            return locationComparison;
        }
        return compareByDateAndTimeslot(a1, a2);
    }

    /**
     * Sorts a list of providers alphabetically by profile (name).
     *
     * @param list The list of providers to be sorted.
     */
    public static void provider(List<Provider> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Provider p1 = list.get(j);
                Provider p2 = list.get(j + 1);
                if (p1.getProfile().compareTo(p2.getProfile()) > 0) {
                    // Swap the providers if they are in the wrong order
                    Provider temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }


}
