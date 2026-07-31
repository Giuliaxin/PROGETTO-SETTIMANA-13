package entities;

import exceptions.RequiredValueMissingException;
import exceptions.ValidationException;
import java.time.LocalDate;

public class DeadlineTask extends AbstractTask {
    private final LocalDate deadlineDate;

    public DeadlineTask(String taskId, String title, Priority priorityLevel, int durationInMinutes, LocalDate deadlineDate) {
        super(taskId, title, priorityLevel, durationInMinutes);
        if (deadlineDate == null) {
            throw new RequiredValueMissingException("Specifica obbligatoriamente una data di termine per il task.");
        }
        this.deadlineDate = deadlineDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(deadlineDate);
    }

    @Override
    public void executeTask() {
        if (isFinished()) {
            throw new ValidationException("Operazione negata: impossibile riaprire un task con scadenza già archiviato.");
        }
        if (isOverdue()) {
            throw new ValidationException("Impossibile procedere: il task ha superato la sua scadenza prevista il " + deadlineDate);
        }

        this.isFinished = true;
        System.out.println("-> Perfetto! Il task limitato '" + getTitle() + "' è stato consegnato in tempo.");
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", deadlineDate=" + deadlineDate +
                ", isOverdue=" + isOverdue() +
                '}';
    }
}