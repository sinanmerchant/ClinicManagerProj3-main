package com.example.clinicmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;


public class ClinicManagerController {
    @FXML private DatePicker appointmentDate;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dateOfBirth;
    @FXML private RadioButton officeVisit;
    @FXML private RadioButton imagingService;
    @FXML private ComboBox<String> timeslotComboBox;
    @FXML private ComboBox<String> providerOrRoom; // Used for both NPI or room selection
    @FXML private TextArea outputArea;
    @FXML private Button scheduleButton;
    @FXML private Button cancelButton;

    @FXML private TextField rescheduleFirstName;
    @FXML private TextField rescheduleLastName;
    @FXML private DatePicker rescheduleDateOfBirth;
    @FXML private DatePicker existingAppointmentDate;
    @FXML private ComboBox<String> oldTimeslotComboBox;
    @FXML private DatePicker newAppointmentDate;
    @FXML private ComboBox<String> newTimeslotComboBox;
    @FXML private Button rescheduleButton;
    @FXML private Button clearRescheduleButton;

    @FXML private Button printByDateButton;
    @FXML private Button printByPatientButton;
    @FXML private Button printByLocationButton;
    @FXML private Button printBillingStatementsButton;
    @FXML private Button printOfficeAppointmentsButton;
    @FXML private Button printImagingAppointmentsButton;
    @FXML private Button printProviderCreditsButton;

    @FXML private TableView<Location> locationTable;
    @FXML
    private TableColumn<Location, String> col_city, col_county, col_zip;

    private List<Provider> providerList; // List of providers
    private List<Appointment> appointmentList; // List of all appointments
    private List<Technician> technicianList; // Circular list for assigning technicians
    private ToggleGroup visitTypeGroup;


    @FXML
    /**
     * Initializes the Clinic Manager Controller, sets up components and listeners for scheduling and managing appointments.
     */
    public void initialize() {
        providerList = new List<>();
        appointmentList = new List<>();
        technicianList = new List<>();
        loadProviders();
        displayProviders();
        displayTechnicians();

        populateTimeslotComboBox();

        initializeRescheduleTab();

        // Populate the list based on appointment type selection
        visitTypeGroup = new ToggleGroup();
        officeVisit.setToggleGroup(visitTypeGroup);
        imagingService.setToggleGroup(visitTypeGroup);

        visitTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateProviderOrRoomOptions());

        scheduleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> scheduleAppointment());
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> cancelAppointment());

        printByDateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> displayAppointments("Date"));
        printByPatientButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> displayAppointments("Patient"));
        printByLocationButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> displayAppointments("Location"));
        printBillingStatementsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> printBillingStatements());
        printOfficeAppointmentsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> displayAppointments("Office"));
        printImagingAppointmentsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> displayAppointments("Radiology"));
        printProviderCreditsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> printProviderCredits());

        ObservableList<Location> locations =
                FXCollections.observableArrayList(Location.values());
        locationTable.setItems(locations);
        col_city.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_zip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        col_county.setCellValueFactory(new PropertyValueFactory<>("county"));
    }

    /**
     * Displays appointments sorted by the specified sort type (e.g., Date, Patient, Location).
     *
     * @param sortType The type of sorting to apply.
     */
    private void displayAppointments(String sortType) {
        if (appointmentList.isEmpty()) {
            outputArea.appendText("Schedule calendar is empty.\n");
            return;
        }

        switch (sortType) {
            case "Date":
                Sort.appointment(appointmentList, 'A');
                outputArea.appendText("** List of appointments ordered by date/time/provider. **\n");
                break;
            case "Patient":
                Sort.appointment(appointmentList, 'P');
                outputArea.appendText("** List of appointments ordered by patient name/date/time. **\n");
                break;
            case "Location":
                Sort.appointment(appointmentList, 'L');
                outputArea.appendText("** List of appointments ordered by location/date/time. **\n");
                break;
            case "Office":
                Sort.appointment(appointmentList, 'O');
                outputArea.appendText("** List of office appointments ordered by county/date/time. **\n");
                for (int i = 0; i < appointmentList.size(); i++) {
                    if (!(appointmentList.get(i) instanceof Imaging)) {
                        outputArea.appendText(appointmentList.get(i).toString() + "\n");
                    }
                }
                outputArea.appendText("** end of list **" + "\n");
                break;
            case "Radiology":
                Sort.appointment(appointmentList, 'I');
                outputArea.appendText("** List of radiology appointments ordered by county/date/time. **\n");
                for (int i = 0; i < appointmentList.size(); i++) {
                    if (appointmentList.get(i) instanceof Imaging) {
                        outputArea.appendText(appointmentList.get(i).toString() + "\n");
                    }
                }
                outputArea.appendText("** End of list **" + "\n");
                break;
        }

        if (sortType == "Radiology" || sortType == "Office") {
            return;
        }

        for (Appointment appointment : appointmentList) {
            outputArea.appendText(appointment.toString() + "\n");
        }
        outputArea.appendText("** End of list **\n");
    }

    /**
     * Prints the billing statements for each patient with total due, sorted by patient.
     */
    private void printBillingStatements() {
        if (appointmentList.isEmpty()) {
            outputArea.appendText("Schedule calendar is empty.\n");
            return;
        }

        Sort.appointment(appointmentList, 'P');
        outputArea.appendText("** Billing Statements ordered by patient **\n");

        Person currentPatient = null;
        double totalDue = 0.0;
        int index = 1;

        for (Appointment appointment : appointmentList) {
            Person patient = appointment.getPatient();
            Person provider = appointment.getProvider();
            double rate = 0.0;
            if (provider instanceof Provider) {
                rate += ((Provider) provider).rate();
            }
            if (currentPatient != null && !currentPatient.equals(patient)) {
                outputArea.appendText("(" + index + ") " + currentPatient + " [Total Due: $" + totalDue + "]\n");
                index++;
                totalDue = 0;
            }

            totalDue += rate;
            currentPatient = patient;
        }

        if (currentPatient != null) {
            outputArea.appendText("(" + index + ") " + currentPatient + " [Total Due: $" + totalDue + "]\n");
        }
        outputArea.appendText("** End of billing statements **\n");
    }

    /**
     * Prints the provider credits.
     */
    private void printProviderCredits() {
        if (appointmentList.size() < 1) {
            outputArea.appendText("Schedule Calendar is empty.\n");
            return;
        }
        outputArea.appendText("\n** Credit amount ordered by provider. **\n");

        Sort.provider(providerList);
        for (int i = 0; i < providerList.size(); i++) {
            Provider provider = providerList.get(i);
            double totalCredit = 0.0;
            for (Appointment appointment : appointmentList) {
                if (appointment.getProvider().equals(provider)) {
                    totalCredit += provider.rate();
                }
            }

            outputArea.appendText("(" + (i+1) + ") " + provider.getProfile().toString() + " [credit amount: $" + totalCredit + "]" + "\n");
        }
        outputArea.appendText("** end of list **\n");
    }

    /**
     * Updates the available options in the providerOrRoom ComboBox based on the selected visit type (Office or Imaging).
     */
    private void updateProviderOrRoomOptions() {
        providerOrRoom.getItems().clear();

        if (officeVisit.isSelected()) {
            // Populate with NPIs for office visits
            for (Provider provider : providerList) {
                if (provider instanceof Doctor) {
                    providerOrRoom.getItems().add(((Doctor) provider).getNpi());
                }
            }
        } else if (imagingService.isSelected()) {
            // Populate with room names for imaging services (mock example)
            providerOrRoom.getItems().addAll("CATSCAN", "ULTRASOUND", "XRAY");
        }
    }

    /**
     * Loads providers from the "providers.txt" file.
     */
    private void loadProviders() {
        File file = new File("src/main/java/com/example/clinicmanager/providers.txt");
        Scanner scanner = initializeScanner(file);

        if (scanner == null) return;

        // Process each line in the file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            processProviderLine(line);
        }
        reverseTechnicianList();
        scanner.close();

        outputArea.appendText("Providers loaded to the list.\n");
    }

    /**
     * Reverses the order of the technicians in the technicianList.
     */
    private void reverseTechnicianList() {
        int left = 0;
        int right = technicianList.size() - 1;

        while (left < right) {
            Technician temp = technicianList.get(left);
            technicianList.set(left, technicianList.get(right));
            technicianList.set(right, temp);

            left++;
            right--;
        }
    }

    /**
     * Initializes the scanner for the file.
     *
     * @param file The file to scan.
     * @return A Scanner object to read the file or null if the file is not found.
     */
    private Scanner initializeScanner(File file) {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            outputArea.appendText("File not found: " + file.getPath() + "\n");
            return null;
        }
    }

    /**
     * Processes a single provider line and adds the provider to the appropriate list.
     *
     * @param line The line of text from the file that describes a provider.
     */
    private void processProviderLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        if (!tokenizer.hasMoreTokens()) {
            return;
        }

        String providerType = tokenizer.nextToken().toUpperCase();

        String firstName = getNextToken(tokenizer);
        String lastName = getNextToken(tokenizer);
        Date dob = parseDate(getNextToken(tokenizer));
        Location location = parseLocation(getNextToken(tokenizer));

        if (providerType.equals("D")) {
            processDoctor(tokenizer, firstName, lastName, dob, location);
        } else if (providerType.equals("T")) {
            processTechnician(tokenizer, firstName, lastName, dob, location);
        }
    }

    /**
     * Parses a location from the provided string.
     *
     * @param locationStr The string representation of the location.
     * @return The corresponding Location enum or null if the location is invalid.
     */
    private Location parseLocation(String locationStr) {
        try {
            return Location.valueOf(locationStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            outputArea.appendText("Invalid location: " + locationStr + "\n");
            return null;
        }
    }

    /**
     * Helper method to parse a date string in MM/DD/YYYY format into a Date object.
     *
     * @param dateStr The date string in MM/DD/YYYY format.
     * @return A Date object representing the parsed date.
     */
    private Date parseDate(String dateStr) {
        String[] dateParts = dateStr.split("/");
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        return new Date(year, month, day); // Return a Date object with int parameters
    }

    /**
     * Cancels an existing appointment based on user-provided details (date, timeslot, and patient profile).
     */
    private void cancelAppointment() {
        // Retrieve date and time from input fields
        LocalDate selectedDate = appointmentDate.getValue();
        if (selectedDate == null) {
            outputArea.appendText("Please select a valid appointment date for cancellation.\n");
            return;
        }
        int timeslotIndex = getSelectedTimeslotIndex();
        if (timeslotIndex == -1) {
            outputArea.appendText("Please select a valid timeslot for cancellation.\n");
            return;
        }

        // Retrieve patient details
        String fname = firstName.getText().trim();
        String lname = lastName.getText().trim();
        if (!isAlphabetic(fname) || !isAlphabetic(lname)) {
            outputArea.appendText("First and last names must contain alphabetic characters only.\n");
            return;
        }
        LocalDate dob = dateOfBirth.getValue();
        if (fname.isEmpty() || lname.isEmpty() || dob == null) {
            outputArea.appendText("Please provide full patient details for cancellation.\n");
            return;
        }

        // Create Profile and Timeslot for appointment lookup
        Profile profile = new Profile(fname, lname, Date.fromLocalDate(dob));
        Timeslot timeslot = Timeslot.getTimeslot(timeslotIndex);
        Date appointmentDate = Date.fromLocalDate(selectedDate);

        // Search for the appointment
        Appointment toCancel = findAppointment(appointmentDate, timeslot, profile);
        if (toCancel == null) {
            outputArea.appendText("No matching appointment found for cancellation.\n");
            return;
        }

        // Remove the appointment
        appointmentList.remove(toCancel);
        outputArea.appendText("Appointment canceled for " + fname + " " + lname + " on " + selectedDate + " at " + timeslot + ".\n");
    }

    /**
     * Processes and adds a doctor to the provider list.
     *
     * @param tokenizer The tokenizer containing doctor information.
     * @param firstName The doctor's first name.
     * @param lastName The doctor's last name.
     * @param dob The doctor's date of birth.
     * @param location The location where the doctor works.
     */
    private void processDoctor(StringTokenizer tokenizer, String firstName, String lastName, Date dob, Location location) {
        Specialty specialty = parseSpecialty(getNextToken(tokenizer));
        String npi = getNextToken(tokenizer);

        if (specialty != null && npi != null) {
            Doctor doctor = new Doctor(new Profile(firstName, lastName, dob), location, specialty, npi);
            providerList.add(doctor);
        }
    }

    /**
     * Parses a specialty from the provided string.
     *
     * @param specialtyStr The string representation of the specialty.
     * @return The corresponding Specialty enum or null if the specialty is invalid.
     */
    private Specialty parseSpecialty(String specialtyStr) {
        try {
            return Specialty.valueOf(specialtyStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            outputArea.appendText("Invalid specialty: " + specialtyStr + "\n");
            return null;
        }
    }

    /**
     * Processes and adds a technician to the provider list and technician list.
     *
     * @param tokenizer The tokenizer containing technician information.
     * @param firstName The technician's first name.
     * @param lastName The technician's last name.
     * @param dob The technician's date of birth.
     * @param location The location where the technician works.
     */
    private void processTechnician(StringTokenizer tokenizer, String firstName, String lastName, Date dob, Location location) {
        String npiStr = getNextToken(tokenizer);

        if (npiStr != null) {
            Technician technician = new Technician(new Profile(firstName, lastName, dob), location, Integer.parseInt(npiStr));
            addTechnician(technician);
            providerList.add(technician);
        }
    }

    /**
     * Retrieves the next token from the tokenizer.
     *
     * @param tokenizer The tokenizer to extract the next token from.
     * @return The next token as a String or null if no more tokens are available.
     */
    private String getNextToken(StringTokenizer tokenizer) {
        return tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
    }

    /**
     * Displays the list of providers sorted by profile.
     */
    private void displayProviders() {
        Sort.provider(providerList);
        for (int i = 0; i < providerList.size(); i++) {
            outputArea.appendText(providerList.get(i).toString() + "\n");
        }
        outputArea.appendText("\n");
    }

    /**
     * Prints the list for the technicians
     */
    private void displayTechnicians() {
        outputArea.appendText("Rotation list for the technicians.\n");

        for (int i = 0; i < technicianList.size(); i++) {
            Technician technician = technicianList.get(i);
            String fullName = technician.getProfile().getFirstName() + " " + technician.getProfile().getLastName();
            String location = technician.getLocation().toString();

            outputArea.appendText(fullName + " (" + location + ")");

            if (i < technicianList.size()-1) {
                outputArea.appendText(" --> ");
            }
        }

        // Move to the next line after the output
        outputArea.appendText("\n");
    }

    /**
     * Adds a technician to the technicianList and maintains alphabetical order by name.
     *
     * @param technician The technician to be added.
     */
    private void addTechnician(Technician technician) {
        technicianList.add(technician);
    }

    /**
     * Populates the timeslotComboBox with formatted time options.
     */
    private void populateTimeslotComboBox() {
        for (int i = 1; i <= 12; i++) {
            Timeslot timeslot = Timeslot.getTimeslot(i);
            timeslotComboBox.getItems().add(timeslot.toString());  // Display formatted time
        }
    }

    /**
     * Schedules an appointment based on user input, handling both office and imaging appointments.
     */
    private void scheduleAppointment() {
        LocalDate selectedDate = appointmentDate.getValue();

        if (!isAlphabetic(firstName.getText()) || !isAlphabetic(lastName.getText())) {
            outputArea.appendText("First and last names must contain alphabetic characters only.\n");
            return;
        }

        if (!validateAppointmentDate(selectedDate)) {
            return;
        }

        int timeslotIndex = getSelectedTimeslotIndex();
        if (timeslotIndex == -1) {
            outputArea.setText("Please select a valid timeslot.");
            return;
        }

        if (dateOfBirth.getValue() == null) {
            outputArea.appendText("Date of birth is required.\n");
            return;
        }

        Profile profile = createPatientProfile(firstName.getText(), lastName.getText(), dateOfBirth.getValue().toString());

        if (profile == null) {
            outputArea.appendText("Please enter valid appointment details.\n");
            return;
        }

        if (officeVisit.isSelected()) {
            // Handle Office Visit
            String npi = providerOrRoom.getValue();
            if (npi == null || npi.isEmpty()) {
                outputArea.appendText("Please select a provider NPI.\n");
                return;
            }
            Provider provider = findProviderByNPI(npi);
            if (provider == null || !(provider instanceof Doctor)) {
                outputArea.appendText("Invalid NPI or provider not found.\n");
                return;
            }

            Appointment appointment = new Appointment(Date.fromLocalDate(selectedDate), Timeslot.getTimeslot(timeslotIndex), new Person(profile), provider);
            if (appointmentExists(appointment) || isProviderBookedAt(Date.fromLocalDate(selectedDate), Timeslot.getTimeslot(timeslotIndex), provider)) {
                return;
            }
            appointmentList.add(appointment);
            outputArea.appendText("Office appointment scheduled: " + appointment + "\n");
        } else if (imagingService.isSelected()) {
            // Handle Imaging Service
            String roomName = providerOrRoom.getValue();
            if (roomName == null || roomName.isEmpty()) {
                outputArea.appendText("Please select a room for imaging service.\n");
                return;
            }
            Radiology room;
            try {
                room = Radiology.valueOf(roomName.replace(" ", "_").toUpperCase()); // Replace spaces with underscores if needed
            } catch (IllegalArgumentException e) {
                outputArea.appendText("Invalid room for imaging service.\n");
                return;
            }

            Technician technician = assignTechnician(Timeslot.getTimeslot(timeslotIndex), room);
            if (technician == null) {
                outputArea.appendText("No available technician for " + roomName + " at the selected timeslot.\n");
                return;
            }

            Imaging appointment = new Imaging(Date.fromLocalDate(selectedDate), Timeslot.getTimeslot(timeslotIndex), new Person(profile), technician, room);
            if (appointmentExists(appointment)) {
                outputArea.appendText("Imaging appointment already exists.\n");
                return;
            }

            appointmentList.add(appointment);
            outputArea.appendText("Imaging appointment scheduled: " + appointment + "\n");
        } else {
            outputArea.appendText("Please select an appointment type (Office or Imaging).\n");
        }
    }

    /**
     * Initializes the reschedule tab, setting up event handlers and populating timeslot options.
     */
    private void initializeRescheduleTab() {
        populateTimeslotComboBox(oldTimeslotComboBox);
        populateTimeslotComboBox(newTimeslotComboBox);

        rescheduleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> rescheduleAppointment());
        clearRescheduleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> clearRescheduleForm());
    }

    /**
     * Populates a given ComboBox with available timeslot options.
     *
     * @param comboBox The ComboBox to populate with timeslot options.
     */
    private void populateTimeslotComboBox(ComboBox<String> comboBox) {
        for (int i = 1; i <= 12; i++) {
            Timeslot timeslot = Timeslot.getTimeslot(i);
            comboBox.getItems().add(timeslot.toString());
        }
    }

    /**
     * Checks if a given name consists of only alphabetic characters.
     *
     * @param name The name to validate.
     * @return True if the name contains only alphabetic characters, false otherwise.
     */
    private boolean isAlphabetic(String name) {
        return name.matches("[a-zA-Z]+");
    }

    /**
     * Reschedules an existing appointment based on user-provided details.
     */
    private void rescheduleAppointment() {
        if (existingAppointmentDate.getValue()==null || newAppointmentDate.getValue()==null) {
            outputArea.appendText("Appointment Date is required.\n");
            return;
        }
        LocalDate oldDate = existingAppointmentDate.getValue();
        int oldTimeslotIndex = oldTimeslotComboBox.getSelectionModel().getSelectedIndex() + 1;
        LocalDate newDate = newAppointmentDate.getValue();
        int newTimeslotIndex = newTimeslotComboBox.getSelectionModel().getSelectedIndex() + 1;

        String firstName = rescheduleFirstName.getText().trim();
        String lastName = rescheduleLastName.getText().trim();

        if (!isAlphabetic(firstName) || !isAlphabetic(lastName)) {
            outputArea.appendText("First and last names must contain alphabetic characters only.\n");
            return;
        }

        if (rescheduleDateOfBirth.getValue()==null || rescheduleDateOfBirth.getValue()==null) {
            outputArea.appendText("Date of birth is required.\n");
            return;
        }

        LocalDate dob = rescheduleDateOfBirth.getValue();

        // Validate all fields
        if (oldDate == null || oldTimeslotIndex <= 0 || newDate == null || newTimeslotIndex <= 0 ||
                firstName.isEmpty() || lastName.isEmpty() || dob == null) {
            outputArea.appendText("Please enter all required reschedule details.\n");
            return;
        }

        // Create patient profile
        Profile profile = new Profile(firstName, lastName, Date.fromLocalDate(dob));
        Timeslot oldTimeslot = Timeslot.getTimeslot(oldTimeslotIndex);
        Timeslot newTimeslot = Timeslot.getTimeslot(newTimeslotIndex);

        // Find existing appointment to reschedule
        Appointment existingAppointment = findAppointment(Date.fromLocalDate(oldDate), oldTimeslot, profile);
        if (existingAppointment == null) {
            outputArea.appendText("Appointment not found.\n");
            return;
        }


        Appointment conflictingAppointment = findAppointment(Date.fromLocalDate(newDate), newTimeslot, profile);
        if (conflictingAppointment != null) {
            outputArea.appendText(conflictingAppointment.getPatient().getProfile() + " has an existing appointment at " + newTimeslot + "\n");
            return;
        }

        appointmentList.remove(existingAppointment);
        Appointment newAppointment = new Appointment(Date.fromLocalDate(newDate), newTimeslot, new Person(profile), existingAppointment.getProvider());
        appointmentList.add(newAppointment);
        outputArea.appendText("Appointment rescheduled to: " + newAppointment + "\n");
    }

    /**
     * Clears all fields in the reschedule form.
     */
    private void clearRescheduleForm() {
        rescheduleFirstName.clear();
        rescheduleLastName.clear();
        rescheduleDateOfBirth.setValue(null);
        existingAppointmentDate.setValue(null);
        oldTimeslotComboBox.getSelectionModel().clearSelection();
        newAppointmentDate.setValue(null);
        newTimeslotComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Finds an appointment by date, timeslot, and patient profile.
     *
     * @param appointmentDate The appointment date.
     * @param timeslot The appointment timeslot.
     * @param profile The patient profile.
     * @return The appointment if found, null otherwise.
     */
    private Appointment findAppointment(Date appointmentDate, Timeslot timeslot, Profile profile) {

        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment appointment = appointmentList.get(i);
            if (appointment.getDate().equals(appointmentDate)
                    && appointment.getTimeslot().equals(timeslot)
                    && appointment.getPatient().getProfile().equals(profile)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Clears all fields in the scheduling form.
     */
    private void clearForm() {
        appointmentDate.setValue(null);
        firstName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        timeslotComboBox.getSelectionModel().clearSelection();
        providerOrRoom.getSelectionModel().clearSelection();
        visitTypeGroup.selectToggle(null);
        outputArea.clear();
    }

    /**
     * Retrieves the currently selected timeslot index from the ComboBox.
     *
     * @return The 1-based index of the selected timeslot, or -1 if none is selected.
     */
    private Integer getSelectedTimeslotIndex() {
        int selectedIndex = timeslotComboBox.getSelectionModel().getSelectedIndex();
        return selectedIndex >= 0 ? selectedIndex + 1 : -1;  // Return 1-based index or -1 if none selected
    }

    /**
     * Validates the selected appointment date.
     *
     * @param date The date to validate.
     * @return True if the date is valid for scheduling, false otherwise.
     */
    private boolean validateAppointmentDate(LocalDate date) {
        if (date == null) {
            outputArea.appendText("Please select a valid appointment date.\n");
            return false;
        }

        Date appointmentDate = Date.fromLocalDate(date);
        if (!appointmentDate.isValid()) {
            outputArea.appendText("Invalid calendar date selected.\n");
            return false;
        }

        if (appointmentDate.isTodayOrBefore()) {
            outputArea.appendText("Appointment date must be in the future.\n");
            return false;
        }

        if (appointmentDate.isWeekend()) {
            outputArea.appendText("Appointment date cannot be on a weekend.\n");
            return false;
        }

        if (!appointmentDate.isWithinSixMonths()) {
            outputArea.appendText("Appointment date must be within the next six months.\n");
            return false;
        }

        return true;
    }

    /**
     * Creates a Profile object from patient details provided by the user.
     *
     * @param fname The first name of the patient.
     * @param lname The last name of the patient.
     * @param dobStr The date of birth of the patient in string format.
     * @return A Profile object if all details are valid, or null if invalid.
     */
    private Profile createPatientProfile(String fname, String lname, String dobStr) {
        try {
            LocalDate dob = LocalDate.parse(dobStr);
            Date dateOfBirth = Date.fromLocalDate(dob);

            if (!dateOfBirth.isValid() || dateOfBirth.isTodayOrAfter()) {
                outputArea.appendText("Invalid date of birth.\n");
                return null;
            }
            return new Profile(fname, lname, dateOfBirth);
        } catch (Exception e) {
            outputArea.appendText("Invalid date format for date of birth.\n");
            return null;
        }
    }

    /**
     * Searches for a provider by NPI from the provider list.
     *
     * @param npi The NPI (National Provider Identifier) to search for.
     * @return The matching Provider, or null if no match is found.
     */
    private Provider findProviderByNPI(String npi) {
        for (Provider provider : providerList) {
            if (provider instanceof Doctor && ((Doctor) provider).getNpi().equals(npi)) {
                return provider;
            }
        }
        return null;
    }

    /**
     * Assigns an available technician for an imaging service at a specific timeslot.
     *
     * @param timeslot The timeslot for the imaging service.
     * @param room The Radiology room required for the service.
     * @return An available Technician, or null if no technician is available.
     */
    private Technician assignTechnician(Timeslot timeslot, Radiology room) {
        // Logic to find an available technician based on room and timeslot
        for (Technician technician : technicianList) {
            if (isTechnicianAvailable(technician, timeslot) && isRoomAvailable(technician.getLocation(), timeslot, room)) {
                return technician;
            }
        }
        return null;
    }

    /**
     * Checks if an appointment already exists in the appointment list.
     *
     * @param appointment The appointment to check.
     * @return True if the appointment exists, false otherwise.
     */
    private boolean appointmentExists(Appointment appointment) {
        for (Appointment existingAppointment : appointmentList) {
            if (existingAppointment.equals(appointment)) {
                outputArea.appendText("This appointment already exists.\n");
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a provider is already booked at a specified date and timeslot.
     *
     * @param date The date of the appointment.
     * @param timeslot The timeslot of the appointment.
     * @param provider The provider to check availability for.
     * @return True if the provider is already booked, false otherwise.
     */
    private boolean isProviderBookedAt(Date date, Timeslot timeslot, Provider provider) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getDate().equals(date) && appointment.getTimeslot().equals(timeslot) && appointment.getProvider().equals(provider)) {
                outputArea.appendText("Provider is already booked at this time.\n");
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a technician is available at a specified timeslot.
     *
     * @param technician The technician to check.
     * @param timeslot The timeslot for which availability is checked.
     * @return True if the technician is available, false otherwise.
     */
    private boolean isTechnicianAvailable(Technician technician, Timeslot timeslot) {
        for (Appointment appointment : appointmentList) {
            if (appointment instanceof Imaging && ((Imaging) appointment).getProvider().equals(technician) && appointment.getTimeslot().equals(timeslot)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a room is available for a specific radiology service at a timeslot.
     *
     * @param location The location of the room.
     * @param timeslot The timeslot for which availability is checked.
     * @param service The radiology service.
     * @return True if the room is available, false otherwise.
     */
    private boolean isRoomAvailable(Location location, Timeslot timeslot, Radiology service) {
        for (Appointment appointment : appointmentList) {
            if (appointment instanceof Imaging && ((Imaging) appointment).getRoom().equals(service) && appointment.getTimeslot().equals(timeslot)) {
                return false;
            }
        }
        return true;
    }
}
