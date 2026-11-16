package bank;

/**
 * Repräsentiert eine ausgehende Überweisung (Sendung).
 * Der Betrag wird vom Konto abgezogen.
 */
public class OutgoingTransfer extends Transfer {

    /**
     * Konstruktor mit den Basisattributen.
     *
     * @param date        das Datum der Überweisung
     * @param amount      der Betrag (muss positiv sein)
     * @param description die Beschreibung
     */
    public OutgoingTransfer(String date, double amount, String description) {
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
    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Copy-Konstruktor.
     *
     * @param other die zu kopierende ausgehende Überweisung
     */
    public OutgoingTransfer(OutgoingTransfer other) {
        super(other);
    }

    /**
     * Gibt den Betrag negativ zurück, da Geld abgeht.
     *
     * @return der negative Betrag
     */
    @Override
    public double calculate() {
        return -getAmount();
    }
}