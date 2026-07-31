import entities.*;
import exceptions.RequiredValueMissingException;
import exceptions.ValidationException;

import java.time.LocalDate;

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
            // Passo una stringa vuota per far scattare l'eccezione
            SimpleTask invalidTask = new SimpleTask("ERR-99", "", Priority.HIGH, -50);
        } catch (RequiredValueMissingException e) {
            System.out.println("Ottimo, blocco per campo obbligatorio funzionante: " + e.getMessage());
        } catch (ValidationException e) {
            System.out.println("Ottimo, blocco logico funzionante: " + e.getMessage());
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
        DeadlineTask overdueTask = new DeadlineTask("TSK-OLD", "Rinnovo licenza software", Priority.HIGH, 15, LocalDate.now().minusDays(3));
        try {
            overdueTask.executeTask();
        } catch (ValidationException e) {
            System.out.println("Controllo su DeadlineTask superato: " + e.getMessage());
        }

        System.out.println();
        RecurringTask meetingTask = new RecurringTask("TSK-102", "Riunione di allineamento team", Priority.LOW, 45, 7);
        meetingTask.executeTask();
        meetingTask.executeTask();
        System.out.println("Riepilogo finale del task ripetitivo: " + meetingTask);
    }
}