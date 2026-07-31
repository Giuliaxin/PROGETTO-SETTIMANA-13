package services;

import entities.AbstractTask;
import entities.Priority;
import exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private final List<AbstractTask> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(AbstractTask task) {
        if (task == null) {
            throw new ValidationException("Impossibile aggiungere un task nullo.");
        }

        boolean exists = taskList.stream()
                .anyMatch(t -> t.getTaskId().equalsIgnoreCase(task.getTaskId()));

        if (exists) {
            throw new ValidationException("Errore di inserimento: esiste già un task con il codice " + task.getTaskId());
        }

        taskList.add(task);
        System.out.println("[Manager] Aggiunto nuovo task: " + task.getTaskId() + " - " + task.getTitle());
    }

    public AbstractTask findByCode(String code) {
        return taskList.stream()
                .filter(t -> t.getTaskId().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Ricerca fallita: nessun task trovato con il codice '" + code + "'."));
    }

    public List<AbstractTask> findByPriority(Priority priorityLevel) {
        return taskList.stream()
                .filter(t -> t.getPriorityLevel() == priorityLevel)
                .collect(Collectors.toList());
    }

    public void removeTask(String code) {
        AbstractTask taskToRemove = findByCode(code);
        taskList.remove(taskToRemove);
        System.out.println("[Manager] Rimosso con successo il task: " + code);
    }

    public void completeTask(String code) {
        AbstractTask taskToComplete = findByCode(code);
        taskToComplete.executeTask();
    }

    public void printStatistics() {
        long completedCount = taskList.stream()
                .filter(AbstractTask::isFinished)
                .count();

        long pendingCount = taskList.stream()
                .filter(t -> !t.isFinished())
                .count();

        System.out.println("\n>>> STATISTICHE DEL TASK MANAGER <<<");
        System.out.println("  • Task completati: " + completedCount);
        System.out.println("  • Task da completare: " + pendingCount);
        System.out.println("  • Totale task registrati: " + taskList.size());
        System.out.println("====================================\n");
    }
}