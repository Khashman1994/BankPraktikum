package Test;

import bank.*;
import bank.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse für Praktikum 3.
 * Testet die Klassen PrivateBank und PrivateBankAlt sowie die Fehlerfälle (Exceptions).
 */
public class Main {

    /**
     * Einstiegspunkt des Programms. Führt alle Tests nacheinander aus.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
    public static void main(String[] args) {

        System.out.println("===== Praktikum 3: Tests =====");

        // ------------------------------------------------------------
        System.out.println("\n=== 1. PrivateBank anlegen ===");
        // Zinsen größer 0, wie in der Aufgabe verlangt
        PrivateBank bank = new PrivateBank("Sparkasse", 0.05, 0.1);
        System.out.println(bank);

        // ------------------------------------------------------------
        System.out.println("\n=== 2. Konten und Transaktionen (PrivateBank, Variante 1) ===");
        try {
            bank.createAccount("Adam");

            // Payments: Die Zinsen werden von der Bank überschrieben (0.05 / 0.1)
            bank.addTransaction("Adam", new Payment("01.11.2026", 1000, "Gehalt", 0, 0));
            bank.addTransaction("Adam", new Payment("02.11.2026", -200, "Geldautomat", 0, 0));

            // Transfers: Variante 1 -> Incoming / Outgoing
            bank.addTransaction("Adam", new IncomingTransfer("03.11.2026", 300, "Geschenk", "Eva", "Adam"));
            bank.addTransaction("Adam", new OutgoingTransfer("04.11.2026", 500, "Miete", "Adam", "Vermieter"));

            System.out.println("Transaktionen von Adam:");
            for (Transaction t : bank.getTransactions("Adam")) {
                System.out.println("  " + t);
            }

            // Erwartet: 950 - 220 + 300 - 500 = 530
            System.out.println("Kontostand Adam : " + bank.getAccountBalance("Adam"));

            System.out.println("Aufsteigend sortiert:");
            for (Transaction t : bank.getTransactionsSorted("Adam", true)) {
                System.out.println("  " + t.calculate());
            }
            System.out.println("Absteigend sortiert:");
            for (Transaction t : bank.getTransactionsSorted("Adam", false)) {
                System.out.println("  " + t.calculate());
            }

            System.out.println("Nur positive Transaktionen erwartet 2: " + bank.getTransactionsByType("Adam", true).size());
            System.out.println("Nur negative Transaktionen erwartet 2: " + bank.getTransactionsByType("Adam", false).size());

            // containsTransaction und removeTransaction
            Payment test = new Payment("05.11.2026", 50, "Test", 0, 0);
            bank.addTransaction("Adam", test);
            System.out.println("Enthält Test-Payment (true?): " + bank.containsTransaction("Adam", test));
            bank.removeTransaction("Adam", test);
            System.out.println("Nach dem Löschen (false?): " + bank.containsTransaction("Adam", test));

        } catch (Exception e) {
            System.out.println("Unerwarteter Fehler: " + e.getMessage());
        }

        // ------------------------------------------------------------
        System.out.println("\n=== 3. createAccount mit Transaktionsliste ===");
        try {
            List<Transaction> liste = new ArrayList<>();
            liste.add(new Payment("01.11.2026", 100, "Startguthaben", 0, 0));
            liste.add(new IncomingTransfer("02.11.2026", 50, "Taschengeld", "Adam", "Eva"));
            bank.createAccount("Eva", liste);
            // Erwartet: 95 + 50 = 145
            System.out.println("Kontostand Eva (erwartet 145.0): " + bank.getAccountBalance("Eva"));
        } catch (Exception e) {
            System.out.println("Unerwarteter Fehler: " + e.getMessage());
        }

        // ------------------------------------------------------------
        System.out.println("\n=== 4. Fehlerfälle (Exceptions) ===");

        // AccountAlreadyExistsException
        try {
            bank.createAccount("Adam");
        } catch (AccountAlreadyExistsException e) {
            System.out.println("OK, AccountAlreadyExistsException: " + e.getMessage());
        }

        // AccountDoesNotExistException bei addTransaction
        try {
            bank.addTransaction("Unbekannt", new Payment("01.11.2026", 10, "Test", 0, 0));
        } catch (AccountDoesNotExistException e) {
            System.out.println("OK, AccountDoesNotExistException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falsche Exception: " + e);
        }

        // TransactionAlreadyExistException
        try {
            bank.addTransaction("Adam", new Payment("01.11.2026", 1000, "Gehalt", 0, 0));
        } catch (TransactionAlreadyExistException e) {
            System.out.println("OK, TransactionAlreadyExistException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falsche Exception: " + e);
        }

        // TransactionDoesNotExistException
        try {
            bank.removeTransaction("Adam", new Payment("09.09.2026", 1, "Gibt es nicht", 0, 0));
        } catch (TransactionDoesNotExistException e) {
            System.out.println("OK, TransactionDoesNotExistException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falsche Exception: " + e);
        }

        // AccountDoesNotExistException bei removeTransaction
        try {
            bank.removeTransaction("Unbekannt", new Payment("01.11.2026", 10, "Test", 0, 0));
        } catch (AccountDoesNotExistException e) {
            System.out.println("OK, AccountDoesNotExistException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falsche Exception: " + e);
        }

        // TransactionAttributeException: Überweisung mit negativem Betrag
        try {
            bank.addTransaction("Adam", new OutgoingTransfer("01.11.2026", -100, "Ungültig", "Adam", "Eva"));
        } catch (TransactionAttributeException e) {
            System.out.println("OK, TransactionAttributeException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falsche Exception: " + e);
        }

        // ------------------------------------------------------------
        System.out.println("\n=== 5. PrivateBankAlt (Variante 2, instanceof) ===");
        PrivateBankAlt bankAlt = new PrivateBankAlt("Volksbank", 0.05, 0.1);
        try {
            bankAlt.createAccount("Adam");
            bankAlt.addTransaction("Adam", new Payment("01.11.2026", 1000, "Gehalt", 0, 0));
            // Normale Transfers: Die Bank entscheidet selbst über sender
            bankAlt.addTransaction("Adam", new Transfer("02.11.2026", 300, "Geschenk", "Eva", "Adam"));
            bankAlt.addTransaction("Adam", new Transfer("03.11.2026", 500, "Miete", "Adam", "Vermieter"));
            // Erwartet: 950 + 300 - 500 = 750
            System.out.println("Kontostand Adam (erwartet 750.0): " + bankAlt.getAccountBalance("Adam"));
        } catch (Exception e) {
            System.out.println("Unerwarteter Fehler: " + e.getMessage());
        }

        // ------------------------------------------------------------
        System.out.println("\n=== 6. equals mit zwei PrivateBank-Objekten ===");
        PrivateBank b1 = new PrivateBank("Commerzbank", 0.02, 0.03);
        PrivateBank b2 = new PrivateBank("Commerzbank", 0.02, 0.03);
        PrivateBank b3 = new PrivateBank("Deutsche Bank", 0.02, 0.03);
        PrivateBank kopie = new PrivateBank(b1);

        System.out.println("b1 equals b2 (true?): " + b1.equals(b2));
        System.out.println("b1 equals Kopie (true?): " + b1.equals(kopie));
        System.out.println("b1 equals b3 (false?): " + b1.equals(b3));

        try {
            b1.createAccount("Konto 1");
        } catch (AccountAlreadyExistsException e) {
            System.out.println("Unerwarteter Fehler: " + e.getMessage());
        }
        System.out.println("Nach neuem Konto in b1 -> b1 equals b2 (false?): " + b1.equals(b2));
    }
}