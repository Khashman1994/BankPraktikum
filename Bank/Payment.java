// Repräsentiert Ein- und Auszahlungen inklusive Zinsangaben
package Bank;
public class Payment extends Transaction  {
   

    // Zinsen bei Ein-und Auszahlungen (Wert zwischen 0.0 und 1.0)
    
    private double incomingInterest, 
                    outgoingInterest;

    /**
     * Setter mit Überprüfung für Auszahlungszinsen (0 % bis 100 %)
     *
     * @param incomingInterest der einzustellende Zinssatz als Dezimalzahl
     */
    public void setIncomingInterest(double incomingInterest) {
         if (incomingInterest >= 0.0 && incomingInterest <= 1.0) {
        this.incomingInterest = incomingInterest;
       
        } else {
        System.out.println("Fehler: Zinsen müssen zwischen 0 und 1 liegen!");
    }
    }

    
   
   
    /**
     * Setzt den Zinssatz für Auszahlungen mit Validierung (0.0 bis 1.0).
     *
     * @param outgoingInterest der einzustellende Zinssatz als Dezimalzahl
     */

    public void setOutgoingInterest(double outgoingInterest) {
    if (outgoingInterest >= 0.0 && outgoingInterest <= 1.0) {
        this.outgoingInterest = outgoingInterest;
        
    } else {
        System.out.println("Fehler: Zinsen müssen zwischen 0 und 1 liegen!");
    }
}
    /**
     * Gibt den Zinssatz für Einzahlungen zurück.
     *
     * @return der Einzahlungszinssatz
     */

    public double getIncomingInterest() {
        return incomingInterest;
    }


    /**
     * Gibt den Zinssatz für Auszahlungen zurück.
     *
     * @return der Auszahlungszinssatz
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }
   
    /**
     * Konstruktor zur Initialisierung der Basisattribute einer Zahlung.
     *
     * @param date        das Datum der Zahlung
     * @param amount      der Betrag (positiv für Einzahlung, negativ für Auszahlung)
     * @param description die Beschreibung der Zahlung
     */
    public Payment(String date,  double amount,String description) {
        super(date,  amount, description);
       
    }

    /**
     * Hauptkonstruktor: Wiederverwendung des Grundkonstruktors zur vollständigen Initialisierung aller Attribute einer Zahlung.
     *
     * @param date             das Datum der Zahlung
     * @param amount           der Geldbetrag
     * @param description      die Beschreibung
     * @param incomingInterest der Zinssatz bei Einzahlungen (0.0 bis 1.0)
     * @param outgoingInterest der Zinssatz bei Auszahlungen (0.0 bis 1.0)
     */
    // 


    public Payment(String date,  double amount,String description, double incomingInterest, double outgoingInterest) {
        this(date, amount, description);
       setIncomingInterest(incomingInterest);
       setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy-Konstruktor zur Erzeugung einer tiefen Kopie einer Payment-Instanz.
     *
     * @param other das zu kopierende Payment-Objekt
     */
    public Payment(Payment other) {
    super(other); // Kopiert date, amount, description
    if (other != null) {
        setIncomingInterest(other.incomingInterest);
        setOutgoingInterest(other.outgoingInterest);
    }
}

/**
     * Berechnet den tatsächlichen Zahlungsbetrag unter Berücksichtigung der Zinsen.
     * Bei Einzahlungen  wird incomingInterest abgezogen.
     * Bei Auszahlungen wird outgoingInterest aufgeschlagen.
     *
     * @return der berechnete Zahlungsbetrag
     */
@Override
    public double calculate() {
        if (getAmount() > 0) {
            return getAmount() - (getAmount() * incomingInterest);
        } else {
            return getAmount() + (getAmount() * outgoingInterest);
        }
    }

    // toString durch printObject ersetzt

@Override
    public String toString() {
        return super.toString() + 
               ", Incoming Interest: " + incomingInterest + 
               ", Outgoing Interest: " + outgoingInterest;
    }

    /**
     * Überprüft, ob diese Payment-Instanz inhaltlich mit einem anderen Objekt identisch ist.
     *
     * @param obj das zu vergleichende Objekt
     * @return {@code true}, wenn alle Attribute übereinstimmen, sonst {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Payment other = (Payment) obj;
        return Double.compare(other.incomingInterest, incomingInterest) == 0 &&
               Double.compare(other.outgoingInterest, outgoingInterest) == 0;
    }

}