public class DepositAccount extends Account{
    CalendarTool expirationDate;
    CalendarTool nextProfitDate;
    boolean isLongTerm;
    CurrentAccount profitAccount;
    int profitRate;
    Long monthlyProfit;
    boolean isExpired;
    public DepositAccount(Person o, Bank b, Long bal, CalendarTool d, boolean longT, CalendarTool expDate, CurrentAccount profAcc){
        super(o, b, bal, d);
        isLongTerm = longT;
        isExpired = false;
        expirationDate = expDate;
        nextProfitDate = d.goForward(1, 1);
        profitAccount = profAcc;
        if(isLongTerm){
            profitRate = 15;
        }
        else
            profitRate = 10;
        monthlyProfit = (long) Math.ceil(balance*profitRate*0.01);
    }

    public Long calculateCharge(CalendarTool closeDate){
        int months = CalendarTool.calcMonthDifference(expirationDate, closeDate);
        Double charge = months * 0.50 * 0.01 * balance;
        return  (charge).longValue();
    }

    public void update(){
        if(Admin.calender.equals(nextProfitDate)){
            bank.balance -= monthlyProfit;
            profitAccount.receiveProfit(monthlyProfit);
            nextProfitDate = nextProfitDate.goForward(1, 1);
        }

        if(Admin.calender.equals(expirationDate)){
            bank.balance -= monthlyProfit;
            profitAccount.receiveProfit(monthlyProfit);
            profitAccount.receiveProfit(balance);
            balance = 0L;
            isExpired = true;
        }
    }

}
