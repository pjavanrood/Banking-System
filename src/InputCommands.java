import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputCommands {
    //add person -fn (first name) -ln (last name) (national code) (birth date YYYY/MM/DD)
    ADD_PERSON("^(?i)add person -fn ([a-z]+\\s*[a-z]*) -ln ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+/[0-1]?[0-9]?/[0-3]?[0-9]?)$"),

    //add company (company name) (CEO national code)
    ADD_COMPANY("^(?i)add company ([a-z]+\\s*[a-z]*) ([0-9]+)$"),

    //add bank (bank name)
    ADD_BANK("^(?i)add bank ([a-z]+\\s*[a-z]*)$"),

    //add bank (bank name) (initial balance)
    ADD_BANK_INITIAL_BALANCE("^(?i)add bank ([a-z]+\\s*[a-z]*) ([0-9]+)$"),

    //set bank income percent (bank name) (income percent)
    SET_BANK_INCOME_PERCENT("^(?i)set bank income percent ([a-z]+\\s*[a-z]*) ([0-9]+)$"),

    //set bank interest percent (bank name) (interest percent)
    SET_BANK_INTEREST_PERCENT("^(?i)set bank interest percent ([a-z]+\\s*[a-z]*) ([0-9]+)$"),

    //increase bank balance (bank name) (amount)
    INCREASE_BANK_BALANCE("^(?i)increase bank balance ([a-z]+\\s*[a-z]*) ([0-9]+)$"),

    //Open current account <bankName> <nationalCode> <initialAmount>
    OPEN_CURRENT_ACCOUNT("^(?i)open current account ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Open current account <bankName> <company id> <initialAmount>
    OPEN_CURRENT_ACCOUNT_LEGAL_PERSON("^(?i)open current account ([a-z]+\\s*[a-z]*) ([0-9]+_[a-z]+\\_*[a-z]*) ([0-9]+)$"),

    //Open deposit account <bankName> <nationalCode> <Long/short-term> <initialAmount> <Current Account Number>
    OPEN_DEPOSIT_ACCOUNT("^(?i)open deposit account ([a-z]+\\s*[a-z]*) ([0-9]+) ([s, l]) ([0-9]+) ([0-9]+)$"),

    //Open deposit account <bankName> <company id> <Long/short-term> <initialAmount> <Current Account Number>
    OPEN_DEPOSIT_ACCOUNT_LEGAL_PERSON("^(?i)open deposit account ([a-z]+\\s*[a-z]*) ([0-9]+_[a-z]+\\_*[a-z]*) ([s, l]) ([0-9]+) ([0-9]+)$"),

    //Close account <bankName> <nationalCode> <accountNum>
    CLOSE_ACCOUNT("^(?i)close account ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Close account <bankName> <Company id> <accountNum>
    CLOSE_ACCOUNT_LEGAL_PERSON("^(?i)close account ([a-z]+\\s*[a-z]*) ([0-9]+_[a-z]+\\_*[a-z]*) ([0-9]+)$"),

    //Change card password <cardNum> <password> <newPassword>
    CHANGE_CARD_PASSWORD("^(?i)change card password ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Set card second password <cardNum> <password> <secondPassword>
    SET_CARD_SECOND_PASSWORD("^(?i)Set card second password ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Change card second password <cardNum> <password> <newSecondPassword>
    CHANGE_SECOND_PASSWORD("(?i)change card second password ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Extend the expiration date <bankName> <cardNum> <nationalCode>
    EXTEND_EXPIRATION_DATE("^(?i)extend the expiration date ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Deposit money <bankName> <accountNum> <amount>
    DEPOSIT_MONEY("^(?i)deposit money ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Withdraw money <cardNum> <password> <amount>
    WITHDRAW_MONEY("^(?i)withdraw money ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Withdraw money <bankName> <accountNum> <nationalCode> <amount>
    WITHDRAW_MONEY_BANK("^(?i)withdraw money ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Withdraw money <bankName> <accountNum> <company id> <amount>
    WITHDRAW_MONEY_BANK_COMPANY("^(?i)withdraw money ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+_[a-z]+\\_*[a-z]*) ([0-9]+)$"),

    //Get account balance <cardNum> <password>
    GET_ACCOUNT_BALANCE("^(?i)Get account balance ([0-9]+) ([0-9]+)$"),

    //Transfer money to another account <cardNum> <password> <receiverCardNum> <amount>
    TRANSFER_MONEY("^(?i)Transfer money to another account ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Transfer money to another account <bankName> <accountNum> <nationalCode> <receiverAccountNum> <amount>
    TRANSFER_MONEY_BANK("^(?i)Transfer money to another account ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Transfer money to another account <bankName> <accountNum> <company id> <receiverAccountNum> <amount>
    TRANSFER_MONEY_BANK_COMPANY("^(?i)Transfer money to another account ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+_[a-z]+\\_*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Receive loan <bankName> <nationalCode> <current account number> <Amount>
    RECEIVE_LOAN("^(?i)Receive loan ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+) ([0-9]+)$"),

    //Receive loan <bankName> <company id> <current account number> <Amount>
    RECEIVE_LOAN_COMPANY("^(?i)Receive loan ([a-z]+\\s*[a-z]*) ([0-9]+_[a-z]+\\_*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Pay off the loan <bankName> <nationalCode> <Amount>
    PAY_LOAN("^(?i)Pay off the loan ([a-z]+\\s*[a-z]*) ([0-9]+) ([0-9]+)$"),

    //Pay off the loan <bankName> <company id> <Amount>
    PAY_LOAN_COMPANY("^(?i)Pay off the loan ([a-z]+\\s*[a-z]*) ([0-9]+_[a-z]+\\_*[a-z]*) ([0-9]+)$"),

    //Go for <num> days
    GO_DAYS("^(?i)Go for ([0-9]+) days$"),

    //Go for <num> months
    GO_MONTHS("^(?i)Go for ([0-9]+) months$"),

    //Go for <num> years
    GO_YEARS("^(?i)Go for ([0-9]+) years$"),

    //Go to date <day/month/year>
    GO_DATE("^(?i)Go to date ([0-9]+)/([0-9]+)/([0-9]+)$"),

    //Show accounts for <nationalCode>
    SHOW_ACCOUNTS_PERSON("^(?i)show accounts for ([0-9]+)$"),

    //Show accounts for <company id>
    SHOW_ACCOUNTS_LEGAL_PERSON("^(?i)show accounts for ([0-9]+_[a-z]+\\_*[a-z]*)$"),

    //Show details of the loan for <nationalCode>
    SHOW_LOANS_PERSON("^(?i)show details of the loan for ([0-9]+)$"),

    //Show details of the loan for <company Id>
    SHOW_LOANS_LEGAL_PERSON("^(?i)show details of the loan for ([0-9]+_[a-z]+\\_*[a-z]*)$"),

    //Show bank info <bankName>
    SHOW_BANK_INFO("^(?i)show bank info ([a-z]+\\s*[a-z]*)$")
    ;

    private final Pattern commandPattern;

    InputCommands(String s) {
        this.commandPattern = Pattern.compile(s);
    }

    public Matcher matcher(String s){
        return this.commandPattern.matcher(s);
    }
}
