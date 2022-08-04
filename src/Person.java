import java.util.ArrayList;

public class Person {
    private String firstName;
    private String lastName;
    public Long id;
    CalendarTool birthDate;
    ArrayList<Account> accountsList;
    ArrayList<Loan> loans;
    Long totalLoanVal;
    int totalLoanCount;
    Long totalBalance;

    Person(String firstName, String lastName, Long id, CalendarTool birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.birthDate = birthDate;
        this.accountsList = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.totalLoanCount = 0;
        this.totalBalance = 0L;
        this.totalLoanVal = 0L;
        CentralBank.personAccountMap.put(this, new ArrayList<>());
    }

    Person(){
        this.accountsList = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.totalLoanCount = 0;
        this.totalBalance = 0L;
        this.totalLoanVal = 0L;
        CentralBank.personAccountMap.put(this, new ArrayList<>());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean checkActiveLoan(Bank bank){
        for (Loan loan : loans) {
            if(loan.bank.bin == bank.bin && !loan.paymentDone)
                return true;
        }
        return false;
    }

    public boolean hasLateInstalment(Bank bank){
        for (Loan loan : loans) {
            if(loan.bank.bin == bank.bin && loan.lateInstalment != 0)
                return true;
        }
        return false;
    }

    public int checkLoanQualification(Long amount, Bank bank){
        if(!(this instanceof LegalPerson) && CalendarTool.calcYearDifference(Admin.calender, birthDate) < 18)
            return -1;

        if((this instanceof LegalPerson) && CalendarTool.calcYearDifference(Admin.calender, ((LegalPerson) this).getCeo().birthDate) <= 18)
            return -2;

        if(this.checkActiveLoan(bank))
            return -3;


        if(bank.getPersonBalance(this)*5L < amount)
            return -4;


        return 0;
    }

    public void receiveLoan(Bank bank, Long amount, CurrentAccount account){
        Loan loan = new Loan(bank, this, amount, account);
        loans.add(loan);
        CentralBank.loans.add(loan);
        bank.loansList.add(loan);
        account.receiveLoan(amount);
        bank.balance -= amount;
    }
}
