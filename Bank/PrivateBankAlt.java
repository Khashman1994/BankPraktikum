package bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import bank.exceptions.*;



public class PrivateBankAlt implements Bank {
       /**
     * Name der privaten Bank.
     */
  protected  String name;
  /**
     * Zinssatz bei Einzahlungen (Wert zwischen 0.0 und 1.0).
     * Wird bei Payments in addTransaction übernommen.
     */
  protected  double incomingInterest;

    /**
     * Zinssatz bei Auszahlungen (Wert zwischen 0.0 und 1.0).
     * Wird bei Payments in addTransaction übernommen.
     */
  protected  double outgoingInterest;
   /**
     * Verknüpft jeden Kontonamen mit seiner Liste von Transaktionen.
     * Schlüssel: Name des Kontos, Wert: Liste der zugehörigen Transaktionen.
     */
  protected   Map<String, List<Transaction>> accountsToTransactions =new java.util.HashMap<>();


  /**
     * Gibt den Namen der Bank zurück.
     *
     * @return der Name der Bank
     */
  public String getName() {
    return name;
  }
  /**
     * Setzt den Namen der Bank.
     *
     * @param name der neue Name der Bank
     */
  public void setName(String name) {
    this.name = name;
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
     * Setzt den Zinssatz für Einzahlungen mit Validierung.
     *
     * @param incomingInterest der Zinssatz als Dezimalzahl (0.0 bis 1.0)
     * @throws IllegalArgumentException wenn der Wert außerhalb von 0 bis 1 liegt
     */

  public void setIncomingInterest(double incomingInterest) {
    if (incomingInterest >= 0 && incomingInterest <= 1) {
        this.incomingInterest = incomingInterest;
    } else {
        throw new IllegalArgumentException("Incoming interest must be between 0 and 1.");
    }
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
     * Setzt den Zinssatz für Auszahlungen mit Validierung.
     *
     * @param outgoingInterest der Zinssatz als Dezimalzahl (0.0 bis 1.0)
     * @throws IllegalArgumentException wenn der Wert außerhalb von 0 bis 1 liegt
     */
  public void setOutgoingInterest(double outgoingInterest) {
    if (outgoingInterest >= 0 && outgoingInterest <= 1) {
        this.outgoingInterest = outgoingInterest;
    } else {
        throw new IllegalArgumentException("Outgoing interest must be between 0 and 1.");
    }
}

 /**
     * Konstruktor zur Initialisierung von Name und Zinssätzen der Bank.
     * Die Zinssätze werden über die Setter geprüft.
     *
     * @param name             der Name der Bank
     * @param incomingInterest der Zinssatz bei Einzahlungen (0.0 bis 1.0)
     * @param outgoingInterest der Zinssatz bei Auszahlungen (0.0 bis 1.0)
     * @throws IllegalArgumentException wenn ein Zinssatz außerhalb von 0 bis 1 liegt
     */
  public PrivateBankAlt(String name, double incomingInterest, double outgoingInterest) {
    this.name = name;
    setIncomingInterest(incomingInterest);
    setOutgoingInterest(outgoingInterest);
  }

   /**
     * Copy-Konstruktor: Erzeugt eine neue Bank mit den gleichen Werten
     * für name, incomingInterest und outgoingInterest wie other.
     *
     * @param other die zu kopierende PrivateBank
     */
  public PrivateBankAlt(PrivateBank other) {
    this(other.name, other.incomingInterest, other.outgoingInterest);
}

// toString method
  @Override
  public String toString() {
    return "PrivateBank{" +
            "name='" + name + '\'' +
            ", incomingInterest=" + incomingInterest +
            ", outgoingInterest=" + outgoingInterest +
            ", accountsToTransactions=" + accountsToTransactions +
            '}';
  }
//equals method
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PrivateBank that = (PrivateBank) o;
    return Double.compare(that.incomingInterest, incomingInterest) == 0 &&
            Double.compare(that.outgoingInterest, outgoingInterest) == 0 &&
            name.equals(that.name) &&
            accountsToTransactions.equals(that.accountsToTransactions);
  }


  /**
 * Legt ein neues, leeres Konto an.
 *
 * @param account der Name des Kontos
 * @throws AccountAlreadyExistsException wenn das Konto bereits existiert
 */
@Override
public void createAccount(String account) throws AccountAlreadyExistsException {
    if (accountsToTransactions.containsKey(account)) {
        throw new AccountAlreadyExistsException("Konto " + account + " existiert bereits!");
    }
    accountsToTransactions.put(account, new ArrayList<>());
}

/**
 * Legt ein neues Konto mit bereits vorhandenen Transaktionen an.
 * Doppelte Transaktionen werden nicht akzeptiert.
 *
 * @param account      der Name des Kontos
 * @param transactions die Transaktionen für das neue Konto
 * @throws AccountAlreadyExistsException    wenn das Konto bereits existiert
 * @throws TransactionAlreadyExistException wenn eine Transaktion doppelt vorkommt
 * @throws TransactionAttributeException    wenn eine Transaktion ungültige Attribute hat
 */
@Override
public void createAccount(String account, List<Transaction> transactions)
        throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
    if (accountsToTransactions.containsKey(account)) {
        throw new AccountAlreadyExistsException("Konto " + account + " existiert bereits!");
    }
    List<Transaction> newList = new ArrayList<>();
    for (Transaction transaction : transactions) {
        // Prüfung der Attribute
        if (transaction instanceof Transfer) {
            if (transaction.getAmount() <= 0) {
                throw new TransactionAttributeException("Überweisungsbetrag muss positiv sein!");
            }
        } else if (transaction instanceof Payment) {
            Payment payment = (Payment) transaction;
            if (payment.getIncomingInterest() < 0 || payment.getIncomingInterest() > 1
                    || payment.getOutgoingInterest() < 0 || payment.getOutgoingInterest() > 1) {
                throw new TransactionAttributeException("Zinsen müssen zwischen 0 und 1 liegen!");
            }
            // Zinsen der Bank übernehmen
            payment.setIncomingInterest(incomingInterest);
            payment.setOutgoingInterest(outgoingInterest);
        }
        // Duplikat-Prüfung
        if (newList.contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaktion existiert bereits: " + transaction);
        }
        newList.add(transaction);
    }
    accountsToTransactions.put(account, newList);
}


/**
 * Fügt einem bestehenden Konto eine Transaktion hinzu.
 * Bei Payments werden die Zinssätze der Bank übernommen.
 *
 * @param account     der Name des Kontos
 * @param transaction die hinzuzufügende Transaktion
 * @throws TransactionAlreadyExistException wenn die Transaktion bereits existiert
 * @throws AccountDoesNotExistException     wenn das Konto nicht existiert
 * @throws TransactionAttributeException    wenn die Transaktion ungültige Attribute hat
 */
@Override
public void addTransaction(String account, Transaction transaction)
        throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
    if (!accountsToTransactions.containsKey(account)) {
        throw new AccountDoesNotExistException("Konto " + account + " existiert nicht!");
    }
    // Prüfung der Attribute
    if (transaction instanceof Transfer) {
        if (transaction.getAmount() <= 0) {
            throw new TransactionAttributeException("Überweisungsbetrag muss positiv sein!");
        }
    } else if (transaction instanceof Payment) {
        Payment payment = (Payment) transaction;
        if (payment.getIncomingInterest() < 0 || payment.getIncomingInterest() > 1
                || payment.getOutgoingInterest() < 0 || payment.getOutgoingInterest() > 1) {
            throw new TransactionAttributeException("Zinsen müssen zwischen 0 und 1 liegen!");
        }
        // Zinsen der Bank übernehmen
        payment.setIncomingInterest(incomingInterest);
        payment.setOutgoingInterest(outgoingInterest);
    }
    // Duplikat-Prüfung
    List<Transaction> list = accountsToTransactions.get(account);
    if (list.contains(transaction)) {
        throw new TransactionAlreadyExistException("Transaktion existiert bereits: " + transaction);
    }
    list.add(transaction);
}


/**
 * Entfernt eine Transaktion aus einem Konto.
 *
 * @param account     der Name des Kontos
 * @param transaction die zu entfernende Transaktion
 * @throws AccountDoesNotExistException     wenn das Konto nicht existiert
 * @throws TransactionDoesNotExistException wenn die Transaktion nicht gefunden wird
 */
@Override
public void removeTransaction(String account, Transaction transaction)
        throws AccountDoesNotExistException, TransactionDoesNotExistException {
    if (!accountsToTransactions.containsKey(account)) {
        throw new AccountDoesNotExistException("Konto " + account + " existiert nicht!");
    }
    if (!accountsToTransactions.get(account).remove(transaction)) {
        throw new TransactionDoesNotExistException("Transaktion existiert nicht: " + transaction);
    }
}

/**
 * Prüft, ob eine Transaktion in einem Konto existiert.
 *
 * @param account     der Name des Kontos
 * @param transaction die gesuchte Transaktion
 * @return true, wenn die Transaktion existiert, sonst false
 */
@Override
public boolean containsTransaction(String account, Transaction transaction) {
    List<Transaction> list = accountsToTransactions.get(account);
    return list != null && list.contains(transaction);
}


/**
 * Gibt alle Transaktionen eines Kontos zurück.
 *
 * @param account der Name des Kontos
 * @return die Liste der Transaktionen (leer, wenn das Konto nicht existiert)
 */
@Override
public List<Transaction> getTransactions(String account) {
    return accountsToTransactions.getOrDefault(account, new ArrayList<>());
}

/**
 * Berechnet den aktuellen Kontostand.
 * Bei Überweisungen wird mit instanceof und Type-Casting geprüft,
 * ob das Konto Sender oder Empfänger ist.
 *
 * @param account der Name des Kontos
 * @return der aktuelle Kontostand
 */
@Override
public double getAccountBalance(String account) {
    double balance = 0;
    for (Transaction transaction : getTransactions(account)) {
        if (transaction instanceof Transfer) {
            Transfer transfer = (Transfer) transaction;   // Type-Casting
            if (account.equals(transfer.getSender())) {
                balance -= transfer.getAmount();          // Sendung → abziehen
            } else {
                balance += transfer.getAmount();          // Empfang → addieren
            }
        } else {
            balance += transaction.calculate();           // Payment → Zinsen berechnen
        }
    }
    return balance;
}


/**
 * Gibt die Transaktionen eines Kontos sortiert nach berechnetem Betrag zurück.
 *
 * @param account der Name des Kontos
 * @param asc     true für aufsteigend, false für absteigend
 * @return eine neue, sortierte Liste der Transaktionen
 */
@Override
public List<Transaction> getTransactionsSorted(String account, boolean asc) {
    List<Transaction> sorted = new ArrayList<>(getTransactions(account));
    if (asc) {
        sorted.sort((a, b) -> Double.compare(a.calculate(), b.calculate()));
    } else {
        sorted.sort((a, b) -> Double.compare(b.calculate(), a.calculate()));
    }
    return sorted;
}



/**
 * Gibt nur die positiven oder nur die negativen Transaktionen eines Kontos zurück.
 *
 * @param account  der Name des Kontos
 * @param positive true für positive, false für negative Transaktionen
 * @return eine Liste der passenden Transaktionen
 */
@Override
public List<Transaction> getTransactionsByType(String account, boolean positive) {
    List<Transaction> result = new ArrayList<>();
    for (Transaction transaction : getTransactions(account)) {
        if (positive && transaction.calculate() > 0) {
            result.add(transaction);
        } else if (!positive && transaction.calculate() < 0) {
            result.add(transaction);
        }
    }
    return result;
}
 

}
