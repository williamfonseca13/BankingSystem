package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * Account implementation for commercial (business) customers.
 * The account's holder is a {@link Company}.
 */
public class CommercialAccount extends Account {

    private final Company company;
    private final Long accountNumber;
    private final int pin;
    private double startingDeposit;
    private final List<Person> authorizedUsers;

    public CommercialAccount(Company company, Long accountNumber, int pin, double startingDeposit) {
        super(company, accountNumber, pin, startingDeposit);
        this.authorizedUsers = new ArrayList<>();
        this.company = company;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.startingDeposit = startingDeposit;
    }

    /**
     * Add person to list of authorized users.
     *
     * @param person The authorized user to be added to the account.
     */
    protected void addAuthorizedUser(Person person) {
        if (authorizedUsers.stream().noneMatch(p -> p.getIdNumber() == person.getIdNumber()))
            authorizedUsers.add(person);
    }

    /**
     * Verify if the person is part of the list of authorized users for this account.
     *
     * @param person
     * @return <code>true</code> if person matches an authorized user in {@link #authorizedUsers}; <code>false</code> otherwise.
     */
    public boolean isAuthorizedUser(Person person) {
        return authorizedUsers.stream().anyMatch(
                p -> p.getIdNumber() == person.getIdNumber()
                        && p.getFirstName().equals(person.getFirstName())
                        && p.getLastName().equals(person.getLastName())
        );
    }
}
