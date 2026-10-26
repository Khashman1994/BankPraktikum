// Basisklasse für alle Transaktionen (Ein-/Auszahlungen und Überweisungen)
package Bank;

import java.util.Objects;

public abstract class  Transaction implements CalculateBill {
    // Datum im Format DD.MM.YYYY
    private String date,description;

    

    // Geldbetrag
    private double amount;

    /**
     * 2. Wie ist ein Copy-Konstruktor aufgebaut?
     * Ein Copy-Konstruktor zeichnet sich durch zwei Merkmale aus:
     *
     * Er hat denselben Namen wie die Klasse.
     *
     * Er nimmt ein Objekt derselben Klasse als Parameter entgegen.
     * Quelle; Gemini, https://www.gemini.de/lexikon/copy-konstruktor#:~:text=Ein%20Copy%2DKonstruktor%20zeichnet%20sich,als%20Parameter%20entgegen.
     */

    
    /**
     * Copy-Konstruktor zur Erzeugung einer tiefen Kopie einer bestehenden Transaktion.
     *
     * @param other das zu kopierende Transaction-Objekt
     */
   public Transaction(Transaction other) {
    if (other != null) {
        setDate(other.date);
        setAmount(other.amount);        
        setDescription(other.description);
    }
}
    
    /**
     * Konstruktor zur Initialisierung aller Basisattribute einer Transaktion.
     *
     * @param date        das Datum der Transaktion 
     * @param amount      der Geldbetrag der Transaktion
     * @param description die Beschreibung oder der Verwendungszweck
     */
    public Transaction(String date,  double amount,String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
            
    }
    /**
     * Gibt das Datum der Transaktion zurück.
     *
     * @return das Datum als String
     */
    public String getDate() {
        return date;
    }

    /**
     * Gibt die Beschreibung der Transaktion zurück.
     *
     * @return die Beschreibung als String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gibt den Nennbetrag der Transaktion zurück.
     *
     * @return der Betrag als double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setzt das Datum der Transaktion.
     *
     * @param date das einzustellende Datum
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setzt die Beschreibung der Transaktion.
     *
     * @param description die einzustellende Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Setzt den Betrag der Transaktion.
     *
     * @param amount der einzustellende Geldbetrag
     */

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /* 
    //PrintObject die drückt die Attribute der Transaktion auf der Konsole aus
    public void printObject() {
    System.out.println("Date: " + date);
    System.out.println("Amount: " + amount);
    System.out.println("Description: " + description);
}
    */





// hier ersetzen wir die Methode printObject() durch die toString()-Methode

/**
     * Erstellt eine Textrepräsentation der Transaktion inklusive des berechneten Betrags.
     *
     * @return eine Zeichenkette mit den Basisdaten der Transaktion
     */

    public String toString() {
        return "Date: " + date + 
               ", Amount: " + calculate() + 
               ", Description: " + description;
    }

     /* 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else return false;

    }*/

    /**
     * Überprüft, ob diese Transaktion inhaltlich mit einem anderen Objekt identisch ist.
     *
     * @param obj das zu vergleichende Objekt
     * @return {@code true}, wenn alle Attribute übereinstimmen, sonst {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction other = (Transaction) obj;
        return Double.compare(other.amount, amount) == 0 &&
               Objects.equals(date, other.date) &&
               Objects.equals(description, other.description);
    } 
               
}
