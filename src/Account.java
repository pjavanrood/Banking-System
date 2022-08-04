public abstract class Account {
    Person owner;
    Bank bank;
    Long number;
    Long balance;
    CalendarTool openDate;
    public Account(Person o, Bank b, Long bal, CalendarTool d){
        owner = o;
        bank = b;
        number = CentralBank.generateAccountNumber(bank.bin, bank.accountsList.size());
        balance = bal;
        openDate = d;
    }

    public static  boolean checkAge(Person person){
        if(CalendarTool.calcYearDifference(Admin.calender, person.birthDate) < 16)
            return false;
        return true;
    }
}
