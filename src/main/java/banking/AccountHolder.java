package banking;

/**
 * Abstract Account Holder.
 */
public abstract class AccountHolder {
    private final int idNumber;

    /**
     * @param idNumber The holder unique ID.
     */
    protected AccountHolder(int idNumber) {
        this.idNumber = idNumber;
    }

    public int getIdNumber() {
        return this.idNumber;
    }
}
