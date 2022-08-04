import java.util.ArrayList;
import java.util.HashMap;

public class Bank {
    String name;
    CalendarTool establishDate;
    CalendarTool profitDate;
    int bin;
    int incomeRate;
    int stAccProfRate;
    int ltAccProfRate;
    int interest;
    Long balance;
    Long nextProfitAmount;
    ArrayList<Account> accountsList;
    HashMap<Person, ArrayList<Account> > personAccMap;
    ArrayList<Loan> loansList;

    public Bank(String n, Long bal, int incomeR, int stProRate, int ltProRate){
        name = n;
        establishDate = Admin.calender;
        profitDate = establishDate.goForward(1, 1);
        nextProfitAmount = (long) Math.ceil(bal*incomeR*0.01);
        bin = setBin(n);
        balance = bal;
        incomeRate = incomeR;
        stAccProfRate = stProRate;
        ltAccProfRate = ltProRate;
        accountsList = new ArrayList<>();
        personAccMap = new HashMap<>();
        loansList = new ArrayList<>();
        interest = 20; //loan interest in 4 years
    }

    public int getIncomeRate() {
        return incomeRate;
    }

    public int getStAccProfRate() {
        return stAccProfRate;
    }

    public int getLtAccProfRate() {
        return ltAccProfRate;
    }

    public int getInterest() {
        return interest;
    }

    private static int setBin(String bankName) {
        if(bankName.equalsIgnoreCase("melli"))
            return 603799;

        else if(bankName.equalsIgnoreCase("sepah"))
            return 589210;

        else if(bankName.equalsIgnoreCase("keshavarzi"))
            return 603770;

        else if(bankName.equalsIgnoreCase("maskan"))
            return 628023;

        else if(bankName.equalsIgnoreCase("eghtesad novin"))
            return 627412;

        else if(bankName.equalsIgnoreCase("parsian"))
            return 622106;

        else if(bankName.equalsIgnoreCase("sarmaye"))
            return 639607;

        else if(bankName.equalsIgnoreCase("dey"))
            return 502938;

        else if(bankName.equalsIgnoreCase("saderat"))
            return 603769;

        else if(bankName.equalsIgnoreCase("mellat"))
            return 610433;

        else if(bankName.equalsIgnoreCase("tejarat"))
            return 627353;

        else
            return (int) (Math.abs(Math.random()) * (999999 - 100000) + 10000); //Generate random 6 digit number
    }

    public void setInterest(int interest) {
        this.incomeRate = interest;
    }

    public void setIncomeRate(int incomeRate) {
        this.incomeRate = incomeRate;
    }

    public void increaseBalance(Long amount){
        balance += amount;
    }

    public Long getPersonBalance(Person person){
        Long total = 0L;
        for (Person person1 : personAccMap.keySet()) {
            if(person1 instanceof LegalPerson){
                if(((LegalPerson) person1).id.equalsIgnoreCase(((LegalPerson) person).id)){
                    for (Account account : personAccMap.get(person1)) {
                        total += account.balance;
                    }
                    break;
                }
            }
            if(person1.id.equals(person.id)){
                for (Account account : personAccMap.get(person1)) {
                    total += account.balance;
                }
            }
        }
        if(total == 0L)
            return -1L;
        return total;
    }

    public void update(){
        if(Admin.calender.equals(profitDate)){
            balance += nextProfitAmount;
            nextProfitAmount = (long) Math.ceil(balance*incomeRate*0.01);
            profitDate = Admin.calender.goForward(1, 1);
        }
    }
}
