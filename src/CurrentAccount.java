import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CurrentAccount extends Account{
    CreditCard card;
    ArrayList<Transaction> transactions;
    HashMap<CalendarTool, Long> deposits;
    HashMap<CalendarTool, Long> withdraws;
    HashMap<CalendarTool, Long> bankWithdraws;

    public CurrentAccount(Person o, Bank b, Long bal, CalendarTool d){
        super(o, b, bal, d);
        card = new CreditCard(this);
        deposits = new HashMap<>();
        withdraws = new HashMap<>();
        bankWithdraws = new HashMap<>();
        transactions = new ArrayList<>();
    }

    public void depositMoney(Long amount, CalendarTool calendarTool){
        balance += amount;
        owner.totalBalance += amount;
        transactions.add(new Transaction(0, calendarTool, amount));
    }

    public int checkWithdraw(Long amount, CalendarTool calendarTool){ //-3 if owner has late instalment. -2 if can not withdraw more money at the date. -1 if amount is more than balance. 0 if no problem.
        if(owner.hasLateInstalment(bank))
            return -3;

        if(amount > balance)
            return -1;

        for (Transaction transaction : transactions) {
            if ((transaction.date.equals(calendarTool) && transaction.type == 1 && transaction.amount + amount > 2000L) || amount > 2000L)
                return -2;
        }
        return 0;
    }

    public int checkWithdrawBank(Long amount){
        if(amount > balance)
            return -2;

        if(owner.hasLateInstalment(bank))
            return -1;

        return 0;
    }

    public void withdrawMoney(Long amount, CalendarTool calendarTool){
        balance -= amount;
        owner.totalBalance -= amount;
        transactions.add(new Transaction(1, calendarTool, amount));
    }

    public int checkSend(Long amount, CalendarTool calendarTool){
        if(owner.hasLateInstalment(bank))
            return -3;

        if(amount > balance)
            return -1;

        for (Transaction transaction : transactions) {
            if ((transaction.date.equals(calendarTool) && transaction.type == 2 && transaction.amount + amount > 5000L) || amount > 5000L)
                return -2;
        }
        return 0;
    }

    public void send(Long amount, CalendarTool date, CurrentAccount receiver){
        balance -= amount;
        owner.totalBalance -= amount;
        transactions.add(new Transaction(2, date, amount, receiver));
    }

    public void receive(Long amount, CalendarTool date, CurrentAccount sender){
        balance += amount;
        owner.totalBalance += amount;
        transactions.add(new Transaction(3, date, amount, sender));
    }

    public void receiveLoan(Long amount){
        transactions.add(new Transaction(4, Admin.calender, amount));
        this.balance += amount;
        this.owner.totalBalance += amount;
    }

    public void payLoan(Long amount){
        transactions.add(new Transaction(5, Admin.calender, amount));
        this.balance -= amount;
        owner.totalBalance -= amount;
    }

    public void receiveProfit(Long amount){
        balance += amount;
        owner.totalBalance += amount;
        transactions.add(new Transaction(6, Admin.calender, amount));
    }
}
