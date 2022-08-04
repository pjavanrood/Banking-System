public class Transaction {
    final String[] types = {"deposit", "withdraw", "send", "receive", "Loan", "LoanPay", "profit receive"};
    int type;
    CalendarTool date;
    Long amount;
    CurrentAccount receiver;
    CurrentAccount sender;

    public Transaction(int type, CalendarTool date, Long amount) {
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public Transaction(int type, CalendarTool date, Long amount, CurrentAccount account) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        if(type == 2){
            this.receiver = account;
        }
        else if(type == 3){
            this.sender = account;
        }
    }
}
