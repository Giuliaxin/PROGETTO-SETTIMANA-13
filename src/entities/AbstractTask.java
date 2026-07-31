package entities;

import exceptions.RequiredValueMissingException;
import exceptions.ValidationException;

public abstract class AbstractTask {
    private final String taskId;
    private String title;
    private Priority priorityLevel;
    private int durationInMinutes;
    protected boolean isFinished;

    public AbstractTask(String taskId, String title, Priority priorityLevel, int durationInMinutes) {
        if (taskId == null || taskId.isBlank()) {
            throw new RequiredValueMissingException("L'identificativo del task è obbligatorio e non può essere lasciato in bianco.");
        }
        if (title == null || title.isBlank()) {
            throw new RequiredValueMissingException("Assicurati di inserire un titolo valido per il task.");
        }
        if (priorityLevel == null) {
            throw new RequiredValueMissingException("È necessario assegnare un livello di priorità al task.");
        }
        if (durationInMinutes <= 0) {
            throw new ValidationException("Il tempo stimato in minuti deve essere un numero strettamente maggiore di zero.");
        }

        this.taskId = taskId;
        this.title = title;
        this.priorityLevel = priorityLevel;
        this.durationInMinutes = durationInMinutes;
        this.isFinished = false;
    }

    public String getTaskId() { return taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new RequiredValueMissingException("Il titolo fornito non è valido.");
        this.title = title;
    }

    public Priority getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(Priority priorityLevel) {
        if (priorityLevel == null) throw new RequiredValueMissingException("La priorità non può mancare.");
        this.priorityLevel = priorityLevel;
    }

    public int getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(int durationInMinutes) {
        if (durationInMinutes <= 0) throw new ValidationException("Imposta una durata positiva per il task.");
        this.durationInMinutes = durationInMinutes;
    }

    public boolean isFinished() { return isFinished; }

    public abstract void executeTask();

    @Override
    public String toString() {
        return "AbstractTask{" +
                "taskId='" + taskId + '\'' +
                ", title='" + title + '\'' +
                ", priorityLevel=" + priorityLevel +
                ", durationInMinutes=" + durationInMinutes +
                ", isFinished=" + isFinished +
                '}';
    }
}