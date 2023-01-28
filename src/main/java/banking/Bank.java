package banking;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * The Bank implementation.
 */
public class Bank implements BankInterface {
    private final LinkedHashMap<Long, Account> accounts;
    private final AtomicLong counter = new AtomicLong(1L);

    public Bank() {
        accounts = new LinkedHashMap<>();
    }

    private Account getAccount(Long accountNumber) {
        return accounts.get(accountNumber);
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
        final long id = counter.incrementAndGet();
        final CommercialAccount commercialAccount = new CommercialAccount(company, id, pin, startingDeposit);
        accounts.put(id, commercialAccount);
        return commercialAccount.getAccountNumber();
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        final long id = counter.incrementAndGet();
        final ConsumerAccount consumerAccount = new ConsumerAccount(person, id, pin, startingDeposit);
        accounts.put(id, consumerAccount);
        return consumerAccount.getAccountNumber();
    }

    public double getBalance(Long accountNumber) {
        final Account account = getAccount(accountNumber);
        return Objects.nonNull(account) ? account.getBalance() : -1.0;
    }

    public void credit(Long accountNumber, double amount) {
        getAccount(accountNumber).creditAccount(amount);
    }

    public boolean debit(Long accountNumber, double amount) {
        return getAccount(accountNumber).debitAccount(amount);
    }

    public boolean authenticateUser(Long accountNumber, int pin) {
        return getAccount(accountNumber).validatePin(pin);
    }

    public void addAuthorizedUser(Long accountNumber, Person authorizedPerson) {

        final Account account = getAccount(accountNumber);

        if (account instanceof CommercialAccount) {
            final CommercialAccount commercialAccount = (CommercialAccount) account;
            commercialAccount.addAuthorizedUser(authorizedPerson);
        }
    }

    public boolean checkAuthorizedUser(Long accountNumber, Person authorizedPerson) {

        if (Objects.isNull(accountNumber) || accountNumber.intValue() == 0 || Objects.isNull(authorizedPerson))
            return false;

        final Account account = getAccount(accountNumber);

        if (account instanceof CommercialAccount) {
            CommercialAccount commercialAccount = (CommercialAccount) account;
            return commercialAccount.isAuthorizedUser(authorizedPerson);
        }

        return false;
    }

    public Map<String, Double> getAverageBalanceReport() {
        return accounts.entrySet().stream()
                .collect(
                        Collectors.groupingBy(e -> e.getValue().getClass().getSimpleName())
                )
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                obj -> obj.getValue().stream().map(x -> x.getValue().getBalance()).collect(Collectors.averagingDouble(r -> r))
                        ));
    }
}
