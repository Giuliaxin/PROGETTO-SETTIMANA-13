import entities.*;
import exceptions.RequiredValueMissingException;
import exceptions.ValidationException;
import services.TaskManager;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println(">>> FASE A: ISTANZIAZIONE DEGLI OGGETTI E VALIDAZIONE DATI <<<");

        try {
            SimpleTask baseTask = new SimpleTask("TSK-100", "Scrittura documentazione tecnica", Priority.HIGH, 120);
            DeadlineTask urgentTask = new DeadlineTask("TSK-101", "Consegna mockup al cliente", Priority.MEDIUM, 30, LocalDate.now().plusDays(5));
            RecurringTask gymTask = new RecurringTask("TSK-102", "Riunione di allineamento team", Priority.LOW, 45, 7);

            System.out.println("Creazione avvenuta con successo. Dettaglio oggetti:");
            System.out.println(baseTask);
            System.out.println(urgentTask);
            System.out.println(gymTask);

        } catch (ValidationException | RequiredValueMissingException e) {
            System.out.println("Eccezione imprevista durante la creazione: " + e.getMessage());
        }

        System.out.println("\n--- Simulazione di creazione fallita (Dati invalidi) ---");
        try {
            SimpleTask invalidTask = new SimpleTask("ERR-99", "", Priority.HIGH, -50);
        } catch (RequiredValueMissingException e) {
            System.out.println("Ottimo, blocco per campo obbligatorio funzionante: " + e.getMessage());
        } catch (ValidationException e) {
            System.out.println("Ottimo, blocco logico funzionante: " + e.getMessage());
        }

        try {
            DeadlineTask pastDeadlineTask = new DeadlineTask("ERR-100", "Task scaduto", Priority.HIGH, 30, LocalDate.now().minusDays(3));
        } catch (ValidationException e) {
            System.out.println("Ottimo, blocco scadenza passata funzionante: " + e.getMessage());
        }

        try {
            RecurringTask invalidFreqTask = new RecurringTask("ERR-101", "Task frequenza errata", Priority.LOW, 30, -2);
        } catch (ValidationException e) {
            System.out.println("Ottimo, blocco frequenza negativa funzionante: " + e.getMessage());
        }


        System.out.println("\n>>> FASE B: TEST DEL COMPORTAMENTO DI ESECUZIONE (executeTask) <<<");

        SimpleTask baseTask = new SimpleTask("TSK-100", "Scrittura documentazione tecnica", Priority.HIGH, 120);
        baseTask.executeTask();
        try {
            baseTask.executeTask();
        } catch (ValidationException e) {
            System.out.println("Controllo su SimpleTask superato: " + e.getMessage());
        }

        System.out.println();
        try {
            DeadlineTask overdueTask = new DeadlineTask("TSK-OLD", "Rinnovo licenza software", Priority.HIGH, 15, LocalDate.now().minusDays(3));
            overdueTask.executeTask();
        } catch (ValidationException e) {
            System.out.println("Controllo su DeadlineTask superato: " + e.getMessage());
        }

        System.out.println();
        RecurringTask meetingTask = new RecurringTask("TSK-102", "Riunione di allineamento team", Priority.LOW, 45, 7);
        meetingTask.executeTask();
        meetingTask.executeTask();
        meetingTask.executeTask();
        System.out.println("Riepilogo finale del task ripetitivo: " + meetingTask);

        System.out.println("\n>>> FASE C: TEST DEL TASK MANAGER E FUNZIONI STREAM <<<");
        TaskManager manager = new TaskManager();

        try {
            manager.addTask(new SimpleTask("MGR-01", "Aggiornamento server", Priority.HIGH, 60));
            manager.addTask(new DeadlineTask("MGR-02", "Dichiarazione redditi", Priority.HIGH, 180, LocalDate.now().plusDays(10)));
            manager.addTask(new RecurringTask("MGR-03", "Pulizia log database", Priority.LOW, 15, 1));
            manager.addTask(new SimpleTask("MGR-04", "Testing applicativo", Priority.MEDIUM, 90));
            manager.addTask(new DeadlineTask("MGR-05", "Rinnovo dominio", Priority.MEDIUM, 45, LocalDate.now().plusDays(15)));

            System.out.println("\n--- Test inserimento duplicato ---");
            manager.addTask(new SimpleTask("MGR-01", "Task clone", Priority.MEDIUM, 30));
        } catch (ValidationException e) {
            System.out.println("Errore intercettato dal Manager: " + e.getMessage());
        }

        try {
            System.out.println("\n--- Test ricerca per priorità (HIGH) ---");
            List<AbstractTask> highPriorityTasks = manager.findByPriority(Priority.HIGH);
            highPriorityTasks.forEach(t -> System.out.println("Trovato: " + t.getTitle()));

            System.out.println("\n--- Test ricerca con lista vuota per priorità ---");
            List<AbstractTask> emptyList = manager.findByPriority(Priority.HIGH).stream()
                    .filter(t -> t.getDurationInMinutes() > 9999)
                    .toList();
            System.out.println("Elementi trovati con filtro vuoto: " + emptyList.size());

            System.out.println("\n--- Test completamento tramite Manager ---");
            manager.completeTask("MGR-01");

            System.out.println("\n--- Test ricerca codice inesistente ---");
            manager.findByCode("GHOST-999");

        } catch (ValidationException e) {
            System.out.println("Errore intercettato dal Manager: " + e.getMessage());
        }

        try {
            System.out.println("\n--- Test statistiche ---");
            manager.printStatistics();

            System.out.println("--- Test rimozione task ---");
            manager.removeTask("MGR-03");

            System.out.println("\n--- Test manipolazione codice inesistente ---");
            manager.completeTask("GHOST-999");

        } catch (ValidationException e) {
            System.out.println("Errore intercettato dal Manager: " + e.getMessage());
        }

        manager.printStatistics();
    }
}