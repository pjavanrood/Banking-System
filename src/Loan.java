import java.util.ArrayList;
import java.util.HashMap;

public class Loan {
    Bank bank;
    CurrentAccount currentAccount;
    Person borrower;

    CalendarTool date;
    CalendarTool nextPayment;
    CalendarTool expiration;

    Long value;
    Long toPay;
    Long instalmentAmount;
    Long payed;

    int interest;
    int latePaymentCharge;
    int lateInstalment;
    int lateAfter3months;

    HashMap<CalendarTool, Long> payments;

    boolean paymentDone;

    public Loan(Bank bank1, Person b, Long val, CurrentAccount account){
        bank = bank1;
        currentAccount = account;
        borrower = b;

        date = Admin.calender;
        nextPayment = date.goForward(1, 1);
        expiration = date.goForward(4, 0);

        value = val;
        toPay = (long) Math.ceil(val*(20 + 100)*0.01);
        instalmentAmount = (long) Math.ceil((val*(bank1.interest + 100)*0.01) / 48);
        payed = 0L;

        interest = 20;
        latePaymentCharge = 2; //for each late payment
        lateInstalment = 0;
        lateAfter3months = 0;

        payments = new HashMap<>();
        paymentDone = false;
    }

    public void update(){
        if(nextPayment.equals(Admin.calender)){
            if(currentAccount.balance < instalmentAmount){
                lateInstalment++;
                if((lateInstalment == 3 && lateAfter3months == 0) || ( lateInstalment > 3)){
                    if(lateAfter3months > 3)
                        lateAfter3months++;
                    interest += 2;
                    toPay = (long) Math.ceil(value*(interest + 100)*0.01) - payed;
                    instalmentAmount = toPay / CalendarTool.calcMonthDifference(expiration, Admin.calender);
                }
                nextPayment = nextPayment.goForward(1, 1);
                return;
            }

            if(toPay < instalmentAmount){
                lateAfter3months = 0;
                lateInstalment = 0;
                payed += toPay;
                toPay = 0L;
                currentAccount.payLoan(toPay);
                bank.balance += instalmentAmount;
                nextPayment = nextPayment.goForward(1, 1);
                return;
            }

            lateAfter3months = 0;
            lateInstalment = 0;
            payed += instalmentAmount;
            toPay -= instalmentAmount;
            currentAccount.payLoan(instalmentAmount);
            bank.balance += instalmentAmount;
            if(toPay == 0){
                paymentDone = true;
            }

            nextPayment = nextPayment.goForward(1, 1);
        }
    }

    public void payLoan(Long amount){
        if(amount < instalmentAmount){
            System.out.println("Amount is less than instalment amount.");
            System.out.println("instalment amount" + instalmentAmount);
            return;
        }

        if(toPay < amount){
            System.out.println("amount is more than remaining instalments value.");
            System.out.println("remaining instalments value" + toPay);
            return;
        }
        lateAfter3months = 0;
        lateInstalment = 0;
        currentAccount.payLoan(amount);
        payed += amount;
        toPay -= amount;
        bank.balance += amount;
    }
}
