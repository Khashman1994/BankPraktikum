package Test;

import Bank.Transfer;
import Bank.Payment;


public class Main {
  public static void main(String[] args) {
        System.out.println("Praktikum 2: Bank-Transaktionen");
        System.out.println("=== 1. TEST: CALCULATE () ===");

        // Einzahlung: 1000€ minus 5% Zinsen (0.05) -> Erwartet: 950€
        Payment einzahlung = new Payment("26.10.2026", 1000.0, "Gehalt", 0.05, 0.1);
        System.out.println("Einzahlung berechnet: " + einzahlung.calculate() + " €");

        // Auszahlung: -1000€ plus 10% Zinsen (0.1) -> Erwartet: -1100€
        Payment auszahlung = new Payment("26.10.2026", -1000.0, "Geldautomat", 0.05, 0.1);
        System.out.println("Auszahlung berechnet: " + auszahlung.calculate() + " €");

        // Überweisung: Betrag bleibt unverändert -> Erwartet: 200€
        Transfer ueberweisung = new Transfer("26.10.2026", 200.0, "Miete", "Khashman", "Herr Müller");
        System.out.println("Überweisung berechnet: " + ueberweisung.calculate() + " €");


        System.out.println("\n=== 2. TEST: TOSTRING () ===");

        // toString() wird beim Ausgeben automatisch aufgerufen
        System.out.println("Payment als String:  " + einzahlung.toString());
        System.out.println("Transfer als String: " + ueberweisung.toString());


        System.out.println("\n=== 3. TEST: EQUALS () ===");

        // Kopie von 'einzahlung' über den Copy-Konstruktor erstellen
        Payment kopie = new Payment(einzahlung);

        // Vergleich mit identischer Kopie (soll true sein)
        System.out.println("Gleiches Objekt verglichen (true?): " + einzahlung.equals(kopie));

        // Vergleich mit Auszahlung (soll false sein)
        System.out.println("Ungleiches Objekt verglichen (false?): " + einzahlung.equals(auszahlung));
    }
    
}

