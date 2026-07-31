package entities;

import exceptions.ValidationException;

public class SimpleTask extends AbstractTask {

    public SimpleTask(String taskId, String title, Priority priorityLevel, int durationInMinutes) {
        super(taskId, title, priorityLevel, durationInMinutes);
    }

    @Override
    public void executeTask() {
        if (isFinished()) {
            throw new ValidationException("Operazione negata: questo task base risulta già contrassegnato come chiuso.");
        }
        this.isFinished = true;
        System.out.println("-> Ottimo lavoro! Il task '" + getTitle() + "' è stato concluso.");
    }
}