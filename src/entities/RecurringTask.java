package entities;

import exceptions.ValidationException;

public class RecurringTask extends AbstractTask {
    private final int intervalDays;
    private int completionCount;

    public RecurringTask(String taskId, String title, Priority priorityLevel, int durationInMinutes, int intervalDays) {
        super(taskId, title, priorityLevel, durationInMinutes);
        if (intervalDays <= 0) {
            throw new ValidationException("L'intervallo di ripetizione in giorni deve essere superiore a zero.");
        }
        this.intervalDays = intervalDays;
        this.completionCount = 0;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public int getCompletionCount() {
        return completionCount;
    }

    @Override
    public void executeTask() {
        this.completionCount++;
        this.isFinished = false;
        System.out.println("-> Ripetizione registrata per '" + getTitle() + "'. Esecuzioni totali finora: " + completionCount);
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", intervalDays=" + intervalDays +
                ", completionCount=" + completionCount +
                '}';
    }
}