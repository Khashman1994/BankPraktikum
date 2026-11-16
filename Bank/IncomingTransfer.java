package bank;

/**
 * Repräsentiert eine eingehende Überweisung (Empfang).
 * Der Betrag wird dem Konto gutgeschrieben.
 */
public class IncomingTransfer extends Transfer {

    /**
     * Konstruktor mit den Basisattributen.
     *
     * @param date        das Datum der Überweisung
     * @param amount      der Betrag (muss positiv sein)
     * @param description die Beschreibung
     */
    public IncomingTransfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Konstruktor mit allen Attributen.
     *
     * @param date        das Datum der Überweisung
     * @param amount      der Betrag (muss positiv sein)
     * @param description die Beschreibung
     * @param sender      der Sender
     * @param recipient   der Empfänger
     */
    public IncomingTransfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Copy-Konstruktor.
     *
     * @param other die zu kopierende eingehende Überweisung
     */
    public IncomingTransfer(IncomingTransfer other) {
        super(other);
    }

    /**
     * Gibt den Betrag positiv zurück, da Geld eingeht.
     *
     * @return der positive Betrag
     */
    @Override
    public double calculate() {
        return getAmount();
    }
}