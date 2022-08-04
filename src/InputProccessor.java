import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;

public class InputProccessor {
    Admin admin;
    Scanner console;

    public InputProccessor(Admin admin) {
        this.admin = admin;
        this.console = new Scanner(System.in);
    }

    public void run(){
        String input;
        while (!(input = this.console.nextLine()).equalsIgnoreCase("exit")){
            if(InputCommands.ADD_PERSON.matcher(input).matches())
                Admin.addPerson(input);

            else if(InputCommands.ADD_COMPANY.matcher(input).matches())
                Admin.addCompany(input);

            else if(InputCommands.ADD_BANK.matcher(input).matches())
                Admin.addBank(input);

            else if(InputCommands.ADD_BANK_INITIAL_BALANCE.matcher(input).matches())
                Admin.addBankWithInitialBalance(input);

            else if(InputCommands.SET_BANK_INCOME_PERCENT.matcher(input).matches())
                Admin.setBankIncomeRate(input);

            else if(InputCommands.SET_BANK_INTEREST_PERCENT.matcher(input).matches())
                Admin.setBankInterestPercent(input);

            else if(InputCommands.INCREASE_BANK_BALANCE.matcher(input).matches())
                Admin.increaseBankBalance(input);

            else if(InputCommands.OPEN_CURRENT_ACCOUNT.matcher(input).matches())
                Admin.openCurrentAccount(input);

            else if(InputCommands.OPEN_CURRENT_ACCOUNT_LEGAL_PERSON.matcher(input).matches())
                Admin.openCurrentAccountLegalPerson(input);

            else if(InputCommands.OPEN_DEPOSIT_ACCOUNT.matcher(input).matches())
                Admin.openDepositAccount(input);

            else if(InputCommands.OPEN_DEPOSIT_ACCOUNT_LEGAL_PERSON.matcher(input).matches())
                Admin.openDepositAccountLegalPerson(input);

            else if(InputCommands.CLOSE_ACCOUNT.matcher(input).matches())
                Admin.closeAccount(input);

            else if(InputCommands.CLOSE_ACCOUNT_LEGAL_PERSON.matcher(input).matches())
                Admin.closeAccountLegalPerson(input);

            else if(InputCommands.CHANGE_CARD_PASSWORD.matcher(input).matches())
                Admin.changeCardPassword(input);

            else if(InputCommands.SET_CARD_SECOND_PASSWORD.matcher(input).matches())
                Admin.setCardSecondPassword(input);

            else if(InputCommands.CHANGE_SECOND_PASSWORD.matcher(input).matches())
                Admin.changeCardSecondPassword(input);

            else if(InputCommands.EXTEND_EXPIRATION_DATE.matcher(input).matches())
                Admin.extendExpirationDate(input);

            else if(InputCommands.DEPOSIT_MONEY.matcher(input).matches())
                Admin.depositMoney(input);

            else if(InputCommands.WITHDRAW_MONEY.matcher(input).matches())
                Admin.withdrawMoney(input);

            else if(InputCommands.WITHDRAW_MONEY_BANK.matcher(input).matches())
                Admin.withdrawMoneyBank(input, false);

            else if(InputCommands.WITHDRAW_MONEY_BANK_COMPANY.matcher(input).matches())
                Admin.withdrawMoneyBank(input, true);

            else if(InputCommands.GET_ACCOUNT_BALANCE.matcher(input).matches())
                Admin.getAccountBalance(input);

            else if(InputCommands.TRANSFER_MONEY.matcher(input).matches())
                Admin.transferMoney(input);

            else if(InputCommands.TRANSFER_MONEY_BANK.matcher(input).matches())
                Admin.transferMoneyBank(input, false);

            else if(InputCommands.TRANSFER_MONEY_BANK_COMPANY.matcher(input).matches())
                Admin.transferMoneyBank(input, true);

            else if(InputCommands.RECEIVE_LOAN.matcher(input).matches())
                Admin.receiveLoan(input, false);

            else if(InputCommands.RECEIVE_LOAN_COMPANY.matcher(input).matches())
                Admin.receiveLoan(input, true);

            else if(InputCommands.PAY_LOAN.matcher(input).matches())
                Admin.payOffLoan(input, false);

            else if(InputCommands.PAY_LOAN_COMPANY.matcher(input).matches())
                Admin.payOffLoan(input, true);

            else if(input.equalsIgnoreCase("Go next day"))
                Admin.updateOneDay();

            else if(input.equalsIgnoreCase("Go next month")){
                Admin.update(CalendarTool.calcDayDifference(Admin.calender.goForward(1, 1), Admin.calender));
            }

            else if(input.equalsIgnoreCase("Go next year")){
                Admin.update(CalendarTool.calcDayDifference(Admin.calender.goForward(1, 0), Admin.calender));
            }

            else if(InputCommands.GO_DAYS.matcher(input).matches()){
                Matcher matcher = InputCommands.GO_DAYS.matcher(input);
                matcher.matches();
                Admin.update(Long.parseLong(matcher.group(1)));
            }

            else if(InputCommands.GO_MONTHS.matcher(input).matches()){
                Matcher matcher = InputCommands.GO_MONTHS.matcher(input);
                matcher.matches();
                Admin.update(CalendarTool.calcDayDifference(Admin.calender.goForward(Integer.parseInt(matcher.group(1)), 1), Admin.calender));
            }

            else if(InputCommands.GO_YEARS.matcher(input).matches()){
                Matcher matcher = InputCommands.GO_YEARS.matcher(input);
                matcher.matches();
                Admin.update(CalendarTool.calcDayDifference(Admin.calender.goForward( Integer.parseInt(matcher.group(1)), 0), Admin.calender));
            }

            else if(InputCommands.GO_DATE.matcher(input).matches()){
                Matcher matcher = InputCommands.GO_DATE.matcher(input);
                matcher.matches();
                CalendarTool calendarTool = new CalendarTool();
                calendarTool.setJulianDate(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
                Long dayDiff = CalendarTool.calcDayDifference(calendarTool, Admin.calender);
                if(dayDiff < 0L){
                    System.out.println("Invalid Date");
                    return;
                }
                Admin.update(dayDiff);
            }

            else if(input.equalsIgnoreCase("show date"))
                Admin.showDate();

            else if(input.equalsIgnoreCase("Show all banks"))
                Admin.showAllBanks();

            else if(input.equalsIgnoreCase("Show all persons"))
                Admin.showAllPersons();

            else if(input.equalsIgnoreCase("Show all companies"))
                Admin.showAllCompanies();

            else if(input.equalsIgnoreCase("Show all accounts"))
                Admin.showAllAccounts();

            else if(input.equalsIgnoreCase("Show all loans"))
                Admin.showAllLoans();

            else if(InputCommands.SHOW_ACCOUNTS_PERSON.matcher(input).matches())
                Admin.showPersonAccounts(input, false);

            else if(InputCommands.SHOW_ACCOUNTS_LEGAL_PERSON.matcher(input).matches())
                Admin.showPersonAccounts(input, true);

            else if(InputCommands.SHOW_LOANS_PERSON.matcher(input).matches())
                Admin.showLoansPerson(input, false);

            else if(InputCommands.SHOW_ACCOUNTS_LEGAL_PERSON.matcher(input).matches())
                Admin.showLoansPerson(input, true);

            else if(InputCommands.SHOW_BANK_INFO.matcher(input).matches())
                Admin.showBankInfo(input);

            else
                System.out.println("Invalid Command!");

        }
    }
}
