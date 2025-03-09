package Backend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class HospitalQueueInterface extends JFrame {
    private Backend.PriorityQueue queue;
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JLabel totalPatientsLabel;
    private JLabel p3CountLabel;
    private JLabel p4CountLabel;
    private JLabel p5CountLabel;
    private JLabel loadFactorLabel;
    private JSlider loadFactorSlider;

    // Store patient details for display purposes
    private Map<UUID, PatientDetails> patientDetails = new HashMap<>();

    private static class PatientDetails {
        String name;
        int age;
        String condition;
        int priority;

        public PatientDetails(String name, int age, String condition, int priority) {
            this.name = name;
            this.age = age;
            this.condition = condition;
            this.priority = priority;
        }
    }

    // Sample patient data
    private final String[] SAMPLE_NAMES = {
            "Emma Martin", "Noah Tremblay", "Olivia Chen", "Liam Rodriguez", "Sophia Kim",
            "William Patel", "Ava Singh", "James Nguyen", "Isabella Garcia", "Logan Smith",
            "Charlotte Brown", "Benjamin Wilson", "Amelia Johnson", "Lucas Davis", "Mia Taylor",
            "Elijah Lee", "Harper Anderson", "Oliver Thompson", "Aria Martinez", "Mason Jackson"
    };

    private final String[] SAMPLE_CONDITIONS = {
            "Sprained ankle", "Migraine", "Chest pain", "Minor burn", "Allergic reaction",
            "Back pain", "Minor laceration", "Persistent cough", "Urinary infection", "Abdominal pain",
            "High fever", "Dehydration", "Dizziness", "Ear infection", "Asthma attack",
            "Eye infection", "Food poisoning", "Minor head injury", "Nausea and vomiting", "Skin rash"
    };

    public HospitalQueueInterface() {
        queue = new Backend.PriorityQueue();

        setTitle("Hospital Priority Queue System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top panel for controls
        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 0));

        // Add patient panel
        JPanel addPatientPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        addPatientPanel.setBorder(BorderFactory.createTitledBorder("Add Patient"));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField conditionField = new JTextField();
        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"P3 (Low Urgency)", "P4 (Less Urgent)", "P5 (Non-Urgent)"});

        addPatientPanel.add(new JLabel("Name:"));
        addPatientPanel.add(nameField);
        addPatientPanel.add(new JLabel("Age:"));
        addPatientPanel.add(ageField);
        addPatientPanel.add(new JLabel("Condition:"));
        addPatientPanel.add(conditionField);
        addPatientPanel.add(new JLabel("Priority:"));
        addPatientPanel.add(priorityCombo);

        JButton addButton = new JButton("Add Patient");
        JButton randomButton = new JButton("Add Random Patient");

        addPatientPanel.add(addButton);
        addPatientPanel.add(randomButton);

        // Queue operations panel
        JPanel queueOpPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        queueOpPanel.setBorder(BorderFactory.createTitledBorder("Queue Operations"));

        JButton nextP3Button = new JButton("Next P3 Patient");
        JButton nextP4Button = new JButton("Next P4 Patient");
        JButton nextP5Button = new JButton("Next P5 Patient");
        JButton checkWaitButton = new JButton("Check Selected Wait Time");

        queueOpPanel.add(nextP3Button);
        queueOpPanel.add(nextP4Button);
        queueOpPanel.add(nextP5Button);
        queueOpPanel.add(checkWaitButton);

        loadFactorLabel = new JLabel("Load Factor: 1.0");
        loadFactorSlider = new JSlider(JSlider.HORIZONTAL, 10, 30, 10);
        loadFactorSlider.setMajorTickSpacing(5);
        loadFactorSlider.setMinorTickSpacing(1);
        loadFactorSlider.setPaintTicks(true);
        loadFactorSlider.setPaintLabels(true);

        queueOpPanel.add(loadFactorLabel);
        queueOpPanel.add(loadFactorSlider);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Queue Statistics"));

        totalPatientsLabel = new JLabel("Total Patients: 0");
        p3CountLabel = new JLabel("P3 Patients: 0");
        p4CountLabel = new JLabel("P4 Patients: 0");
        p5CountLabel = new JLabel("P5 Patients: 0");

        statsPanel.add(totalPatientsLabel);
        statsPanel.add(p3CountLabel);
        statsPanel.add(p4CountLabel);
        statsPanel.add(p5CountLabel);

        // Add all panels to control panel
        controlPanel.add(addPatientPanel);
        controlPanel.add(queueOpPanel);
        controlPanel.add(statsPanel);

        // Table panel
        String[] columnNames = {"ID", "Name", "Age", "Condition", "Priority", "Wait Time", "Expected Treatment Time"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setFillsViewportHeight(true);

        // Set custom column widths
        patientTable.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID column
        patientTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name column
        patientTable.getColumnModel().getColumn(2).setPreferredWidth(40);  // Age column
        patientTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Condition column
        patientTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Priority column
        patientTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Wait Time column
        patientTable.getColumnModel().getColumn(6).setPreferredWidth(180); // Expected Treatment Time column

        JScrollPane scrollPane = new JScrollPane(patientTable);

        // Add panels to main panel
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);

        // Add event listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    String condition = conditionField.getText().trim();
                    int priorityIndex = priorityCombo.getSelectedIndex();

                    if (name.isEmpty() || condition.isEmpty()) {
                        JOptionPane.showMessageDialog(HospitalQueueInterface.this,
                                "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Convert priority index to actual priority value
                    int priorityValue = priorityIndex + 3; // P3, P4, P5

                    // Add patient to queue
                    addPatientToQueue(name, age, condition, priorityValue);

                    // Clear fields
                    nameField.setText("");
                    ageField.setText("");
                    conditionField.setText("");
                    priorityCombo.setSelectedIndex(0);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(HospitalQueueInterface.this,
                            "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRandomPatient();
            }
        });

        nextP3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextPatient(3);
            }
        });

        nextP4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextPatient(4);
            }
        });

        nextP5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextPatient(5);
            }
        });

        checkWaitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSelectedPatientWaitTime();
            }
        });

        loadFactorSlider.addChangeListener(e -> {
            double factor = loadFactorSlider.getValue() / 10.0;
            loadFactorLabel.setText("Load Factor: " + factor);
            queue.setLoadFactor(factor);
            updateTable(); // Refresh wait times
        });

        // Add some sample patients
        for (int i = 0; i < 10; i++) {
            addRandomPatient();
        }

        setLocationRelativeTo(null);
    }

    private void addPatientToQueue(String name, int age, String condition, int priority) {
        UUID patientId = UUID.randomUUID();

        // Store patient details
        patientDetails.put(patientId, new PatientDetails(name, age, condition, priority));

        // Add to backend queue
        queue.addPatient(patientId, priority);

        // Update the table and stats
        updateTable();
        updateStats();
    }

    private void addRandomPatient() {
        Random random = new Random();

        String name = SAMPLE_NAMES[random.nextInt(SAMPLE_NAMES.length)];
        int age = random.nextInt(80) + 18; // Ages 18-97
        String condition = SAMPLE_CONDITIONS[random.nextInt(SAMPLE_CONDITIONS.length)];
        int priority = random.nextInt(3) + 3; // P3, P4, or P5

        addPatientToQueue(name, age, condition, priority);
    }

    private void processNextPatient(int priority) {
        UUID patientId = queue.nextPatient(priority);

        if (patientId != null) {
            String patientName = patientDetails.get(patientId).name;
            JOptionPane.showMessageDialog(this,
                    "Now treating: " + patientName + " (Priority " + priority + ")",
                    "Patient Called", JOptionPane.INFORMATION_MESSAGE);

            // Remove from our details store
            patientDetails.remove(patientId);

            // Update display
            updateTable();
            updateStats();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No patients with priority " + priority + " in queue.",
                    "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void checkSelectedPatientWaitTime() {
        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a patient from the table.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the patient ID from the first column
        String idString = (String) tableModel.getValueAt(selectedRow, 0);
        UUID patientId = UUID.fromString(idString);

        PatientDetails details = patientDetails.get(patientId);
        Backend.WaitTimeInfo waitInfo = queue.getWaitTimeInfo(patientId, details.priority);

        // Format for display
        String message = "Patient: " + details.name + "\n" +
                "Priority: P" + details.priority + "\n" +
                "Position in queue: " + (waitInfo.getQueuePosition() + 1) + "\n" +
                "Estimated wait: " + waitInfo.getFormattedTime() + "\n";

        if (waitInfo.getExpectedTreatmentTime() != null) {
            message += "Expected treatment time: " + waitInfo.getFormattedExpectedTime();
        }

        JOptionPane.showMessageDialog(this, message, "Wait Time Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // Update P3 patients
        updateTableForPriority(3);

        // Update P4 patients
        updateTableForPriority(4);

        // Update P5 patients
        updateTableForPriority(5);
    }

    @SuppressWarnings("unchecked")
    private void updateTableForPriority(int priority) {
        LinkedList<UUID> patientList;

        try {
            // Use reflection to access the protected LinkedList fields
            java.lang.reflect.Field field;
            switch (priority) {
                case 3: field = Backend.PriorityQueue.class.getDeclaredField("fileP3"); break;
                case 4: field = Backend.PriorityQueue.class.getDeclaredField("fileP4"); break;
                case 5: field = Backend.PriorityQueue.class.getDeclaredField("fileP5"); break;
                default: return;
            }

            field.setAccessible(true);
            patientList = (LinkedList<UUID>) field.get(queue);

            for (UUID patientId : patientList) {
                PatientDetails details = patientDetails.get(patientId);

                if (details != null) {
                    Backend.WaitTimeInfo waitInfo = queue.getWaitTimeInfo(patientId, priority);

                    tableModel.addRow(new Object[] {
                            patientId.toString(),
                            details.name,
                            details.age,
                            details.condition,
                            "P" + priority,
                            waitInfo.getFormattedTime(),
                            waitInfo.getFormattedExpectedTime()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error accessing queue data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateStats() {
        totalPatientsLabel.setText("Total Patients: " + queue.getTotalPatientCount());
        p3CountLabel.setText("P3 Patients: " + queue.getPatientCountByPriority(3));
        p4CountLabel.setText("P4 Patients: " + queue.getPatientCountByPriority(4));
        p5CountLabel.setText("P5 Patients: " + queue.getPatientCountByPriority(5));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HospitalQueueInterface app = new HospitalQueueInterface();
            app.setVisible(true);
        });
    }
}
