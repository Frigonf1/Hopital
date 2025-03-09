package Backend;

import java.util.LinkedList;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PriorityQueue {
    protected LinkedList<UUID> fileP3 = new LinkedList<>();
    protected LinkedList<UUID> fileP4 = new LinkedList<>();
    protected LinkedList<UUID> fileP5 = new LinkedList<>();

    // Store patient arrival times
    protected Map<UUID, LocalDateTime> patientArrivalTimes = new HashMap<>();

    // Average processing times in minutes for each priority level
    private final int P3_PROCESSING_TIME = 15;
    private final int P4_PROCESSING_TIME = 20;
    private final int P5_PROCESSING_TIME = 30;

    // Current hospital load factor (1.0 = normal, >1.0 = busier than normal)
    private double loadFactor = 1.0;

    protected void addPatient(UUID id, int priority) {
        // Record patient arrival time
        patientArrivalTimes.put(id, LocalDateTime.now());

        switch (priority) {
            case 3: fileP3.add(id); break;
            case 4: fileP4.add(id); break;
            case 5: fileP5.add(id); break;
            default: System.out.println("Invalid priority level"); break;
        }
    }

    protected void removePatient(UUID id, int priority) {
        switch (priority) {
            case 3: fileP3.remove(id); break;
            case 4: fileP4.remove(id); break;
            case 5: fileP5.remove(id); break;
            default: System.out.println("Invalid priority level"); break;
        }

        // Remove from arrival times tracking
        patientArrivalTimes.remove(id);
    }

    protected UUID nextPatient(int priority) {
        UUID patientId = null;
        switch (priority) {
            case 3:
                if (!fileP3.isEmpty()) {
                    patientId = fileP3.getFirst();
                    fileP3.removeFirst();
                }
                break;
            case 4:
                if (!fileP4.isEmpty()) {
                    patientId = fileP4.getFirst();
                    fileP4.removeFirst();
                }
                break;
            case 5:
                if (!fileP5.isEmpty()) {
                    patientId = fileP5.getFirst();
                    fileP5.removeFirst();
                }
                break;
            default: System.out.println("Invalid priority level"); break;
        }

        // Remove from arrival times tracking if patient exists
        if (patientId != null) {
            patientArrivalTimes.remove(patientId);
        }

        return patientId;
    }

    protected UUID getNextPatient(int priority) {
        switch (priority) {
            case 3: return !fileP3.isEmpty() ? fileP3.getFirst() : null;
            case 4: return !fileP4.isEmpty() ? fileP4.getFirst() : null;
            case 5: return !fileP5.isEmpty() ? fileP5.getFirst() : null;
            default:
                System.out.println("Invalid priority level");
                return null;
        }
    }

    protected int getPatientPosition(UUID id, int priority) {
        switch (priority){
            case 3: return fileP3.indexOf(id);
            case 4: return fileP4.indexOf(id);
            case 5: return fileP5.indexOf(id);
            default:
                System.out.println("Invalid priority level");
                return -1;
        }
    }

    protected int getPriority(UUID id){
        if (fileP3.contains(id)) return 3;
        if (fileP4.contains(id)) return 4;
        if (fileP5.contains(id)) return 5;
        return 0; // Not found
    }

    /**
     * Set the current hospital load factor to adjust wait time calculations
     * @param factor Load factor (1.0 = normal, >1.0 = busier)
     */
    public void setLoadFactor(double factor) {
        if (factor > 0) {
            this.loadFactor = factor;
        }
    }

    /**
     * Get the current hospital load factor
     * @return Current load factor
     */
    public double getLoadFactor() {
        return this.loadFactor;
    }

    /**
     * Calculate estimated wait time for a patient
     * @param id Patient UUID
     * @param priority Patient priority level
     * @return WaitTimeInfo object containing all wait information
     */
    public WaitTimeInfo getWaitTimeInfo(UUID id, int priority) {
        int position = getPatientPosition(id, priority);

        if (position == -1) {
            return new WaitTimeInfo(0, 0, 0, null, -1);
        }

        // Calculate base processing time based on priority
        int processingTime;
        switch (priority) {
            case 3: processingTime = P3_PROCESSING_TIME; break;
            case 4: processingTime = P4_PROCESSING_TIME; break;
            case 5: processingTime = P5_PROCESSING_TIME; break;
            default: processingTime = 0;
        }

        // Calculate total wait time in minutes, accounting for position and hospital load
        int waitTimeMinutes = (int) (position * processingTime * loadFactor);

        // Calculate hours and remaining minutes
        int hours = waitTimeMinutes / 60;
        int minutes = waitTimeMinutes % 60;

        // Calculate expected treatment time
        LocalDateTime arrivalTime = patientArrivalTimes.get(id);
        LocalDateTime expectedTreatmentTime = null;
        long waitedMinutes = 0;

        if (arrivalTime != null) {
            waitedMinutes = Duration.between(arrivalTime, LocalDateTime.now()).toMinutes();
            expectedTreatmentTime = LocalDateTime.now().plusMinutes(waitTimeMinutes);
        }

        return new WaitTimeInfo(waitTimeMinutes, hours, minutes, expectedTreatmentTime, position);
    }

    /**
     * Calculate wait time as a formatted string
     * @param id Patient UUID
     * @param priority Patient priority level
     * @return Formatted wait time string
     */
    public String calculateWaitTime(UUID id, int priority) {
        WaitTimeInfo info = getWaitTimeInfo(id, priority);

        if (info.getTotalMinutes() == 0) {
            return "Patient not found in queue";
        }

        return "Votre temps d'attente estimé est de " + info.getHours() + " heures et " +
                info.getMinutes() + " minutes";
    }

    /**
     * Get total count of patients in all queues
     * @return Total patient count
     */
    public int getTotalPatientCount() {
        return fileP3.size() + fileP4.size() + fileP5.size();
    }

    /**
     * Get count of patients by priority
     * @param priority Priority level
     * @return Count of patients with that priority
     */
    public int getPatientCountByPriority(int priority) {
        switch (priority) {
            case 3: return fileP3.size();
            case 4: return fileP4.size();
            case 5: return fileP5.size();
            default: return 0;
        }
    }
}