
// Repräsentiert eine Überweisung von einem Sender an einen Empfänger
package Bank;

import java.util.Objects;

public class Transfer extends Transaction {

    //Attributes
    
    private String sender, recipient;

    /**
     * Gibt den Namen des Senders zurück.
     *
     * @return der Sender als String
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gibt den Namen des Empfängers zurück.
     *
     * @return der Empfänger als String
     */
    public String getRecipient() {
        return recipient;
    }
    /**
     * Setzt den Namen des Senders.
     *
     * @param sender der einzustellende Sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    /**
     * Setzt den Namen des Empfängers.
     *
     * @param recipient der einzustellende Empfänger
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Setzt den Betrag der Überweisung mit Validierung.
     * Bei Überweisungen sind nur positive Beträge zulässig.
     *
     * @param amount der einzustellende Betrag (muss &gt; 0 sein)
     */
    @Override
    public void setAmount(double amount) {
    if (amount > 0) {
        super.setAmount(amount);
    } else {
        System.out.println("Fehler: Bei einer Überweisung sind nur positive Beträge erlaubt!");
    }
}

/**
     * Konstruktor zur Initialisierung der Basisattribute einer Überweisung.
     *
     * @param date        das Datum der Überweisung
     * @param amount      der zu überweisende Betrag (muss positiv sein)
     * @param description die Beschreibung der Überweisung
     */
    public Transfer(String date,  double amount,String description) {
        super(date,  amount, description);
       
    }

    // H
    /**
     * Hauptkonstruktor: Nutzt den Grundkonstruktor über this() wieder
     *
     * @param date        das Datum der Überweisung
     * @param amount      der Betrag (muss positiv sein)
     * @param description die Beschreibung
     * @param sender      der Name des Senders
     * @param recipient   der Name des Empfängers
     */
     public Transfer(String date, double amount, String description, String sender, String recipient) {
        this(date, amount, description);
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     * Copy-Konstruktor zur Erzeugung einer tiefen Kopie eines Transfer-Objekts.
     *
     * @param other das zu kopierende Transfer-Objekt
     */
    public Transfer(Transfer other) {
    super(other); // Kopiert date, amount, description
    if (other != null) {
        setSender(other.sender);
        setRecipient(other.recipient);
    }
}

/**
     * Gibt den Überweisungsbetrag unverändert zurück, da bei Überweisungen
     * keine Zinsen anfallen.
     *
     * @return der unveränderte Betrag
     */
@Override
    public double calculate() {
        return getAmount();
    }


//toString durch printObject ersetzt
/**
     * Gibt eine Zeichenkette mit allen Daten der Überweisung zurück.
     *
     * @return eine Zeichenkette mit allen Daten der Überweisung
     */
@Override
    public String toString() {
        return super.toString() + 
               ", Sender: " + sender + 
               ", Recipient: " + recipient;
    }

    /**
     * Vergleicht dieses Transfer-Objekt mit einem anderen Objekt auf Gleichheit.
     *
     * @param obj das zu vergleichende Objekt
     * @return {@code true}, wenn Basisattribute, Sender und Empfänger übereinstimmen, sonst {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Transfer other = (Transfer) obj;
        return Objects.equals(sender, other.sender) &&
               Objects.equals(recipient, other.recipient);
    }

}