package Backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to hold detailed wait time information for a patient
 */
public class WaitTimeInfo {
    private final int totalMinutes;
    private final int hours;
    private final int minutes;
    private final LocalDateTime expectedTreatmentTime;
    private final int queuePosition;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public WaitTimeInfo(int totalMinutes, int hours, int minutes, LocalDateTime expectedTreatmentTime, int queuePosition) {
        this.totalMinutes = totalMinutes;
        this.hours = hours;
        this.minutes = minutes;
        this.expectedTreatmentTime = expectedTreatmentTime;
        this.queuePosition = queuePosition;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public LocalDateTime getExpectedTreatmentTime() {
        return expectedTreatmentTime;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public String getFormattedTime() {
        return hours + " heures et " + minutes + " minutes";
    }

    public String getFormattedExpectedTime() {
        if (expectedTreatmentTime == null) {
            return "N/A";
        }
        return expectedTreatmentTime.format(TIME_FORMATTER);
    }

    public String getFormattedExpectedDateTime() {
        if (expectedTreatmentTime == null) {
            return "N/A";
        }
        return expectedTreatmentTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "Temps d'attente estimé: " + getFormattedTime() +
                "\nHeure de traitement prévue: " + getFormattedExpectedTime() +
                "\nPosition dans la file: " + queuePosition;
    }
}