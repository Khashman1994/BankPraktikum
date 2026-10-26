package Bank;

 public interface CalculateBill {
    /**
     * Berechnet den Endbetrag einer Transaktion unter Berücksichtigung
     * von möglichen Zinsen oder Gebühren.
     *
     * @return der berechnete Betrag als double
     */
    double calculate();
}
