package Backend;

import java.util.LinkedList;
import java.util.UUID;

public class PriorityQueue {
    protected LinkedList<UUID> fileP3 = new LinkedList<>();
    protected LinkedList<UUID> fileP4 = new LinkedList<>();
    protected LinkedList<UUID> fileP5 = new LinkedList<>();

    protected void addPatient(UUID id, int priority) {
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
    }

    protected void nextPatient(int priority) {
        switch (priority) {
            case 3: fileP3.removeFirst(); break;
            case 4: fileP4.removeFirst(); break;
            case 5: fileP5.removeFirst(); break;
            default: System.out.println("Invalid priority level"); break;
        }
    }

    protected void getNextPatient(int priority) {
        switch (priority) {
            case 3: fileP3.getFirst(); break;
            case 4: fileP4.getFirst(); break;
            case 5: fileP5.getFirst(); break;
            default: System.out.println("Invalid priority level"); break;
        }
    }

    protected void getPatientByIndex(UUID id, int priority) {
        switch (priority){
            case 3: fileP3.indexOf(id); break;
            case 4: fileP4.indexOf(id); break;
            case 5: fileP5.indexOf(id); break;
            default: System.out.println("Invalid priority level"); break;
        }
    }

    protected int getPriority(UUID id){
        return 0;
    }

    protected String calculateWaitTime(UUID id, int priority) {
        switch (priority) {
            case 3: {
                int temps = fileP3.indexOf(id) * 15;
                int heures = temps / 60;
                int minutes = temps % 60;

                return "Votre temps d'attente estimé est de " + heures + " heures et " + minutes + " minutes";
            }
            case 4: {
                int temps = fileP4.indexOf(id) * 15;
                int heures = temps / 60;
                int minutes = temps % 60;

                return "Votre temps d'attente estimé est de " + heures + " heures et " + minutes + " minutes";
            }
            case 5: {
                int temps = fileP5.indexOf(id) * 15;
                int heures = temps / 60;
                int minutes = temps % 60;

                return "Votre temps d'attente estimé est de " + heures + " heures et " + minutes + " minutes";
            }
            default:
                System.out.println("Invalid priority level");
                return null;
        }
    }
}
