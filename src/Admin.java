import java.util.ArrayList;
import java.util.regex.Matcher;


public class Admin {
    public static CalendarTool calender;
    public static CentralBank centralBank;
    public static ArrayList<Person> people;
    public static ArrayList<LegalPerson> companiesList;

    Admin(){
        calender = new CalendarTool();
        calender.setToday();
        centralBank = new CentralBank();
        people = new ArrayList<>();
        centralBank = new CentralBank();
        companiesList = new ArrayList<>();
    }

    public static void main(String[] args) { Admin admin = new Admin(); }

    static void addPerson(String command){ //Birthdate input format: YEAR/MONTH/DAY -> 1381/04/20
        try {
            Matcher matcher = InputCommands.ADD_PERSON.matcher(command);
            matcher.matches();
            String[] bDate = matcher.group(4).split("/");
            CalendarTool date = new CalendarTool();
            date.setIranianDate(Integer.parseInt(bDate[0]), Integer.parseInt(bDate[1]), Integer.parseInt(bDate[2]));

            people.add(new Person(matcher.group(1), matcher.group(2), Long.parseLong(matcher.group(3)), date));
            System.out.println("Person added Successfully");
        }catch (Exception | Error e){
            System.out.println("Error! Request Failed");
            System.out.println(e);
        }
    }

    static void addCompany(String command){
        Long ceoId = null;
        Matcher matcher = InputCommands.ADD_COMPANY.matcher(command);
        matcher.matches();
        try {
            ceoId = Long.parseLong(matcher.group(2));
        }catch (Exception exception){
            System.out.println("Error! Request Failed");
            return;
        }
        String companyName = matcher.group(1);
        for (LegalPerson legalPerson : companiesList) {
            if(legalPerson.name.equals(companyName)){
                System.out.println("Company Already Exists!");
                return;
            }
        }
        CalendarTool date = new CalendarTool();
        date.setJulianDate(calender.getJulianYear(), calender.getJulianMonth(), calender.getJulianDay());
        for (Person person : people) {
            if(person.id.equals(ceoId)){
                LegalPerson company = new LegalPerson(person, date, companyName);
                companiesList.add(company);
                System.out.println("Company added Successfully");
                System.out.println("Company Id: " + company.id);
                return;
            }
        }
        System.out.println("CEO not Found!");
    }

    static void addBank(String command){
        Matcher matcher = InputCommands.ADD_BANK.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                System.out.println("Bank Already Exists!");
                return;
            }
        }
        CalendarTool date = new CalendarTool();
        date.setJulianDate(calender.getJulianYear(), calender.getJulianMonth(), calender.getJulianDay());
        Bank bank = new Bank(bankName, 3_000_000_000L, 30, 10, 15);
        CentralBank.banks.add(bank);
        System.out.println("Bank added Successfully");
        System.out.println("Bank BIN: " + bank.bin);
    }

    static void addBankWithInitialBalance(String command){
        Matcher matcher = InputCommands.ADD_BANK_INITIAL_BALANCE.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);

        long initialBalance = 0L;

        try {
            initialBalance = Long.parseLong(matcher.group(2));
        }catch (Exception exception){
            System.out.println("Error! Request Failed");
        }

        if(initialBalance < 3_000_000_000L){
            System.out.println("Initial Balance not enough!");
            return;
        }

        CalendarTool date = new CalendarTool();
        date.setJulianDate(calender.getJulianYear(), calender.getJulianMonth(), calender.getJulianDay());
        Bank bank = new Bank(bankName, initialBalance, 30, 10, 15);
        CentralBank.banks.add(bank);
        System.out.println("Bank added Successfully\nBank BIN: " + bank.bin);
    }

    static void setBankIncomeRate(String command){
        Matcher matcher = InputCommands.SET_BANK_INCOME_PERCENT.matcher(command);
        matcher.matches();
        try {
            String bankName = matcher.group(1);
            for (Bank bank : CentralBank.banks) {
                if (bank.name.equalsIgnoreCase(bankName)) {
                    bank.setIncomeRate(Integer.parseInt(matcher.group(2)));
                    System.out.println("Income rate set Successfully");
                    return;
                }
            }
            System.out.println("Bank not Found!");
        }catch (Exception e){
            System.out.println("Error! Request Failed");
        }
    }

    static void setBankInterestPercent(String command){
        try {
            Matcher matcher = InputCommands.SET_BANK_INTEREST_PERCENT.matcher(command);
            matcher.matches();
            String bankName = matcher.group(1);
            for (Bank bank : CentralBank.banks) {
                if (bank.name.equalsIgnoreCase(bankName)) {
                    bank.setInterest(Integer.parseInt(matcher.group(2)));
                    System.out.println("Interest percent set Successfully");
                    return;
                }
            }
            System.out.println("Bank not Found!");
        }catch (Exception e){
            System.out.println("Error! Request Failed");
        }
    }

    static void increaseBankBalance(String command){
        try {
            Matcher matcher = InputCommands.INCREASE_BANK_BALANCE.matcher(command);
            matcher.matches();
            String bankName = matcher.group(1);
            for (Bank bank : CentralBank.banks) {
                if (bank.name.equalsIgnoreCase(bankName)) {
                    bank.increaseBalance(Long.parseLong(matcher.group(2)));
                    System.out.println("Balance increased Successfully");
                    return;
                }
            }
            System.out.println("Bank not Found!");
        }catch (Exception e){
            System.out.println("Error! Request Failed");
        }
    }

    static void openCurrentAccount(String command){
        Matcher matcher = InputCommands.OPEN_CURRENT_ACCOUNT.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);

        long id = 0L;
        long initialAmount = 0L;

        try {
            id = Long.parseLong(matcher.group(2));
            initialAmount = Long.parseLong(matcher.group(3));
        } catch (Exception exception){
            System.out.println("Error! Request Failed");
            return;
        }
        int bankIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(bankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        for (Person person : people) {
            if(person.id == id){
                Bank bank = CentralBank.banks.get(bankIndex);
                CalendarTool date = new CalendarTool();
                date.setJulianDate(calender.getJulianYear(), calender.getJulianMonth(), calender.getJulianDay());
                if(!Account.checkAge(person)){
                    System.out.println("Owner is not old enough!");
                    return;
                }
                CurrentAccount currentAccount = new CurrentAccount(person, bank, initialAmount, date);
                bank.accountsList.add(currentAccount);
                if(bank.personAccMap.containsKey(person))
                {
                    bank.personAccMap.get(person).add(currentAccount);
                }
                else {
                    bank.personAccMap.put(person, new ArrayList<>());
                    bank.personAccMap.get(person).add(currentAccount);
                }
                person.accountsList.add(currentAccount);
                CentralBank.personAccountMap.get(person).add(currentAccount);
                System.out.println("Current account opened Successfully");
                System.out.printf("Your account number is: %s\n", currentAccount.number.toString());
                System.out.printf("Credit Card PIN: %d\n", currentAccount.card.pin);
                System.out.printf("Credit Card CVV2: %d\n", currentAccount.card.cvv2);
                System.out.printf("Credit Card Expiration Date: %s\n", currentAccount.card.expirationDate.getIranianDate());
                return;
            }
        }
        System.out.println("Person not found!");
    }

    static void openCurrentAccountLegalPerson(String command){
        Matcher matcher = InputCommands.OPEN_CURRENT_ACCOUNT_LEGAL_PERSON.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);

        String companyId = matcher.group(2);
        long initialAmount = 0L;

        try {
            initialAmount = Long.parseLong(matcher.group(3));
        } catch (Exception exception){
            System.out.println("Error! Request Failed");
            return;
        }
        int bankIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(bankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        for (LegalPerson company : companiesList) {
            if(company.id.equals(companyId)){
                Bank bank = CentralBank.banks.get(bankIndex);
                CalendarTool date = new CalendarTool();
                date.setJulianDate(calender.getJulianYear(), calender.getJulianMonth(), calender.getJulianDay());
                if(!Account.checkAge(company.getCeo())){
                    System.out.println("CEO is not old enough!");
                    return;
                }
                CurrentAccount currentAccount = new CurrentAccount(company, bank, initialAmount, date);
                bank.accountsList.add(currentAccount);
                if(bank.personAccMap.containsKey(company))
                {
                    bank.personAccMap.get(company).add(currentAccount);
                }
                else {
                    bank.personAccMap.put(company, new ArrayList<>());
                    bank.personAccMap.get(company).add(currentAccount);
                }
                company.accountsList.add(currentAccount);
                CentralBank.personAccountMap.get(company).add(currentAccount);
                System.out.println("Current account opened Successfully");
                System.out.printf("Your account number is: %s\n", currentAccount.number.toString());
                System.out.printf("Credit Card PIN: %d\n", currentAccount.card.pin);
                System.out.printf("Credit Card CVV2: %d\n", currentAccount.card.cvv2);
                System.out.printf("Credit Card Expiration Date: %s\n", currentAccount.card.expirationDate.getIranianDate());
                return;
            }
        }
        System.out.println("Company not found!");
    }

    static void openDepositAccount(String command){
        Matcher matcher = InputCommands.OPEN_DEPOSIT_ACCOUNT.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        long id = Long.parseLong(matcher.group(2));
        boolean isLongT = false;
        if(matcher.group(3).equalsIgnoreCase("L"))
            isLongT = true;
        long initialBal = Long.parseLong(matcher.group(4));
        if(isLongT && initialBal < 10_000L){
            System.out.println("Insufficient Balance!");
            return;
        }

        if(!isLongT && initialBal < 5_000L){
            System.out.println("Insufficient Balance!");
            return;
        }

        Long currentAccNum = Long.parseLong(matcher.group(5));

        int indx = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                indx = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(indx == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        for (Person person : people) {
            if(person.id.equals(id)){
                Bank bank = CentralBank.banks.get(indx);
                CalendarTool date = new CalendarTool();
                date.setGregorianDate(calender.getGregorianYear(), calender.getGregorianMonth(), calender.getGregorianDay());
                CalendarTool expDate = new CalendarTool();
                if(isLongT)
                    expDate = date.goForward(1, 0);

                else
                    expDate = date.goForward(6, 1);

                int accIndx = -1;
                for (Account account : person.accountsList) {
                    if(account.number.equals(currentAccNum) && account instanceof CurrentAccount)
                        accIndx = person.accountsList.indexOf(account);
                    if(!account.owner.id.equals(id)){
                        System.out.println("Account does not belong to this id!");
                        return;
                    }
                }

                if(accIndx == -1){
                    System.out.println("Current Account not found");
                    return;
                }
                if(!Account.checkAge(person)){
                    System.out.println("Owner is not old enough!");
                    return;
                }
                if(person.checkActiveLoan(bank)){
                    System.out.println("Owner has Active Loan! Can not open Deposit Account!");
                    return;
                }

                DepositAccount depositAccount = new DepositAccount(person, bank, initialBal, date, isLongT, expDate, (CurrentAccount) person.accountsList.get(accIndx));

                person.accountsList.add(depositAccount);
                bank.personAccMap.get(person).add(depositAccount);
                bank.accountsList.add(depositAccount);
                CentralBank.personAccountMap.get(person).add(depositAccount);
                System.out.println("Deposit account opened Successfully");
                System.out.printf("Your account number is: %s\n", depositAccount.number.toString());
                return;
            }
        }
        System.out.println("Person Not Found!");
    }

    static void openDepositAccountLegalPerson(String command){
        Matcher matcher = InputCommands.OPEN_DEPOSIT_ACCOUNT_LEGAL_PERSON.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        String id = matcher.group(2);
        boolean isLongT = false;
        if(matcher.group(3).equalsIgnoreCase("L"))
            isLongT = true;
        long initialBal = Long.parseLong(matcher.group(4));
        if(isLongT && initialBal < 10_000L){
            System.out.println("Insufficient Balance!");
            return;
        }

        if(!isLongT && initialBal < 5_000L){
            System.out.println("Insufficient Balance!");
            return;
        }

        Long currentAccNum = Long.parseLong(matcher.group(5));

        int indx = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                indx = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(indx == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        for (LegalPerson company : companiesList) {
            if(company.id.equals(id)){
                Bank bank = CentralBank.banks.get(indx);
                CalendarTool date = new CalendarTool();
                date.setJulianDate(calender.getJulianYear(), calender.getJulianMonth(), calender.getJulianDay());
                CalendarTool expDate = new CalendarTool();
                if(isLongT)
                    expDate = date.goForward(1, 0);

                else
                    expDate = date.goForward(1, 0);

                int accIndx = -1;
                for (Account account : company.accountsList) {
                    if(account.number.equals(currentAccNum) && account instanceof CurrentAccount)
                        accIndx = company.accountsList.indexOf(account);
                    if(!((LegalPerson) account.owner).id.equalsIgnoreCase(id)){
                        System.out.println("Account does not belong to this company!");
                        return;
                    }
                }

                if(accIndx == -1){
                    System.out.println("Current Account not found");
                    return;
                }
                if(!Account.checkAge(company.getCeo())){
                    System.out.println("CEO is not old enough!");
                    return;
                }
                if(company.checkActiveLoan(bank)){
                    System.out.println("Owner has Active Loan! Can not open Deposit Account!");
                    return;
                }
                DepositAccount depositAccount = new DepositAccount(company, bank, initialBal, date, isLongT, expDate, (CurrentAccount) company.accountsList.get(accIndx));

                company.accountsList.add(depositAccount);
                bank.personAccMap.get(company).add(depositAccount);
                CentralBank.personAccountMap.get(company).add(depositAccount);
                bank.accountsList.add(depositAccount);
                System.out.println("Current account opened Successfully");
                System.out.printf("Your account number is: %s\n", depositAccount.number.toString());
                return;
            }
        }
        System.out.println("Company Not Found!");
    }

    static void closeAccount(String command){
        Matcher matcher = InputCommands.CLOSE_ACCOUNT.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        long id = Long.parseLong(matcher.group(2));
        long accNum = Long.parseLong(matcher.group(3));
        Account account = null;
        int bankIndex = -1;
        int accIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                for (Account account1 : bank.accountsList) {
                    if(account1.number.equals(accNum)) {
                        if(!account1.owner.id.equals(id)){
                            System.out.println("Account does not belong to this national code");
                            return;
                        }
                        accIndex = bank.accountsList.indexOf(account1);
                        account = account1;
                        break;
                    }
                }
                break;
            }
        }

        if(bankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        if(accIndex == -1){
            System.out.println("Account Not Found!");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);
        if(account instanceof DepositAccount){
            Long charge = ((DepositAccount) account).calculateCharge(Admin.calender);
            System.out.println("You Were Charged " + charge + "$ for closing account sooner than agreed date.");
            bank.balance += charge;
            account.balance -= charge;
            DepositAccount depositAccount = (DepositAccount) account;
            depositAccount.profitAccount.balance += account.balance;
            System.out.printf("Your Balance was transferred to your Profit Account\nAmount: %d\nProfit Account Number: %d\n", account.balance, depositAccount.profitAccount.number);
            Person person = account.owner;
            person.accountsList.remove(account);
            bank.accountsList.remove(account);
            bank.personAccMap.get(person).remove(account);
            CentralBank.personAccountMap.get(person).remove(account);
            return;
        }
        Person person = account.owner;
        person.totalBalance -= account.balance;
        System.out.println("account balance was removed from your total balance: " + account.balance);
        person.accountsList.remove(account);
        bank.accountsList.remove(account);
        bank.personAccMap.get(person).remove(account);
        CentralBank.personAccountMap.get(person).remove(account);
    }

    static void closeAccountLegalPerson(String command){
        Matcher matcher = InputCommands.CLOSE_ACCOUNT_LEGAL_PERSON.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        String id = matcher.group(2);
        long accNum = Long.parseLong(matcher.group(3));
        Account account = null;
        int bankIndex = -1;
        int accIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                for (Account account1 : bank.accountsList) {
                    if(account1.number.equals(accNum)) {
                        if(!(account1.owner instanceof LegalPerson)){
                            System.out.println("Account does not belong to a company");
                            return;
                        }

                        if(!(((LegalPerson) account1.owner).id.equals(id))){
                            System.out.println("Account does not belong to this company");
                            return;
                        }

                        accIndex = bank.accountsList.indexOf(account1);
                        account = account1;
                        break;
                    }
                }
                break;
            }
        }

        if(bankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        if(accIndex == -1){
            System.out.println("Account Not Found!");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);
        if(account instanceof DepositAccount){
            Long charge = ((DepositAccount) account).calculateCharge(Admin.calender);
            System.out.println("You Were Charged " + charge + "$ for closing account sooner than agreed date.");
            bank.balance += charge;
            account.balance -= charge;
            DepositAccount depositAccount = (DepositAccount) account;
            depositAccount.profitAccount.balance += account.balance;
            System.out.printf("Your Balance was transferred to your Profit Account\nAmount: %d\nProfit Account Number: %d\n", account.balance, depositAccount.profitAccount.number);
            Person person = account.owner;
            bank.accountsList.remove(account);
            bank.personAccMap.get(person).remove(account);
            person.accountsList.remove(account);
            CentralBank.personAccountMap.get(person).remove(account);
            return;
        }
        Person person = account.owner;
        person.totalBalance -= account.balance;
        System.out.println("account balance was removed from your total balance: " + account.balance);
        bank.accountsList.remove(account);
        bank.personAccMap.get(person).remove(account);
        person.accountsList.remove(account);
        CentralBank.personAccountMap.get(person).remove(account);
    }

    static void changeCardPassword(String command){
        Matcher matcher = InputCommands.CHANGE_CARD_PASSWORD.matcher(command);
        matcher.matches();
        Long cardNum = Long.parseLong(matcher.group(1));

        int pass = Integer.parseInt(matcher.group(2));
        int newPass = Integer.parseInt(matcher.group(3));
        if(matcher.group(3).length() != 4){
            System.out.println("Password should have 4 digits!");
            return;
        }

        if(pass == newPass){
            System.out.println("New password is same as old password!");
            return;
        }
        int bankBin = Integer.parseInt(matcher.group(1).substring(0, 6));

        int bankIndx = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.bin == bankBin)
                bankIndx = CentralBank.banks.indexOf(bank);
        }
        if(bankIndx == -1){
            System.out.println("Bank Not Found!");
            return;
        }
        Bank bank = CentralBank.banks.get(bankIndx);
        boolean accFound = false;
        boolean passRight = false;
        for (Account account : bank.accountsList) {
            if(account instanceof CurrentAccount){
                CurrentAccount currentAccount = (CurrentAccount) account;
                if(account.number.equals(cardNum)){
                    accFound = true;
                    if(currentAccount.card.pin == pass){
                        passRight = true;
                        currentAccount.card.pin = newPass;
                        System.out.println("PIN changed Successfully!");
                        return;
                    }
                }
            }
        }

        if(!accFound){
            System.out.println("Card Not Found!");
            return;
        }

        if(!passRight)
            System.out.println("Incorrect Password!");
    }

    static void setCardSecondPassword(String command){
        Matcher matcher = InputCommands.SET_CARD_SECOND_PASSWORD.matcher(command);
        matcher.matches();
        Long cardNum = Long.parseLong(matcher.group(1));
        int pass = Integer.parseInt(matcher.group(2));
        int newPass = Integer.parseInt(matcher.group(3));
        if(matcher.group(3).length() != 6){
            System.out.println("Second Password must have 6 digits!");
            return;
        }
        int bankBin = Integer.parseInt(matcher.group(1).substring(0, 6));
        int bankIndx = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.bin == bankBin)
                bankIndx = CentralBank.banks.indexOf(bank);
        }
        if(bankIndx == -1){
            System.out.println("Bank Not Found!");
            return;
        }
        Bank bank = CentralBank.banks.get(bankIndx);
        boolean accFound = false;
        boolean passRight = false;
        for (Account account : bank.accountsList) {
            if(account instanceof CurrentAccount){
                CurrentAccount currentAccount = (CurrentAccount) account;
                if(account.number.equals(cardNum)){
                    accFound = true;
                    if(currentAccount.card.pin == pass){
                        if(currentAccount.card.secondPinActivation){
                            System.out.println("Second Password has already been activated!");
                            return;
                        }
                        passRight = true;
                        currentAccount.card.secondPinActivation = true;
                        currentAccount.card.secondPin = newPass;
                        System.out.println("Second Pin activated Successfully!");
                        return;
                    }
                }
            }
        }

        if(!accFound){
            System.out.println("Card Not Found!");
            return;
        }

        if(!passRight)
            System.out.println("Incorrect Password!");

    }

    static void changeCardSecondPassword(String command){
        Matcher matcher = InputCommands.CHANGE_SECOND_PASSWORD.matcher(command);
        matcher.matches();
        Long cardNum = Long.parseLong(matcher.group(1));

        int pass = Integer.parseInt(matcher.group(2));
        int newPass = Integer.parseInt(matcher.group(3));
        if(matcher.group(3).length() != 6){
            System.out.println("Second password must have 6 digits!");
            return;
        }
        int bankBin = Integer.parseInt(matcher.group(1).substring(0, 6));
        int bankIndx = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.bin == bankBin)
                bankIndx = CentralBank.banks.indexOf(bank);
        }
        if(bankIndx == -1){
            System.out.println("Bank Not Found!");
            return;
        }
        Bank bank = CentralBank.banks.get(bankIndx);
        boolean accFound = false;
        boolean passRight = false;
        for (Account account : bank.accountsList) {
            if(account instanceof CurrentAccount){
                CurrentAccount currentAccount = (CurrentAccount) account;
                if(account.number.equals(cardNum)){
                    accFound = true;
                    if(currentAccount.card.pin == pass){
                        if(!currentAccount.card.secondPinActivation){
                            System.out.println("Second Password has not been activated!");
                            return;
                        }
                        passRight = true;
                        currentAccount.card.secondPin = newPass;
                        System.out.println("Second Pin changed Successfully!");
                        return;
                    }
                }
            }
        }

        if(!accFound){
            System.out.println("Card Not Found!");
            return;
        }

        if(!passRight)
            System.out.println("Incorrect Password!");



    }

    static void extendExpirationDate(String command){
        Matcher matcher = InputCommands.EXTEND_EXPIRATION_DATE.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        Long cardNum = Long.parseLong(matcher.group(2));
        Long id = Long.parseLong(matcher.group(3));
        int bankIndx = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndx = CentralBank.banks.indexOf(bank);
                break;
            }
        }
        if(bankIndx == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        boolean cardFound = false;
        boolean ownerFound = false;
        Bank bank = CentralBank.banks.get(bankIndx);
        for (Account account : bank.accountsList) {
            if(account.number.equals(cardNum)){
                cardFound = true;
                if(account.owner.id.equals(id)){
                    ownerFound = true;
                    if(account instanceof CurrentAccount){
                        CurrentAccount currentAccount = (CurrentAccount) account;
                        if(currentAccount.card.active){
                            System.out.println("Card is still active!");
                            return;
                        }

                        if(currentAccount.owner.hasLateInstalment(bank)){
                            System.out.println("You have Late Loan Instalment! No Service Available!");
                            return;
                        }

                        currentAccount.card.extendExpirationDate();
                        System.out.println("Expiration date extended!");
                        System.out.println("new Expiration Date: " + currentAccount.card.expirationDate.getIranianDate());
                        return;
                    }
                }
            }
        }
        if(!cardFound){
            System.out.println("Card Not Found!");
            return;
        }

        if(!ownerFound){
            System.out.println("Card does no belong to this id!");
        }

    }

    static void depositMoney(String command){
        Matcher matcher = InputCommands.DEPOSIT_MONEY.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        Long accNum = Long.parseLong(matcher.group(2));
        Long amount = Long.parseLong(matcher.group(3));

        int bankIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }
        if(bankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        boolean accFound = false;
        Bank bank = CentralBank.banks.get(bankIndex);
        for (Account account : bank.accountsList) {
            if(account.number.equals(accNum)){
                accFound = true;
                if(account instanceof DepositAccount){
                    System.out.println("Can not deposit money into Deposit Account!");
                    return;
                }
                CurrentAccount currentAccount = (CurrentAccount) account;
                currentAccount.depositMoney(amount, calender);
                currentAccount.deposits.put(calender, amount);
                System.out.println("Deposit Successful!");
                System.out.println("Account balance: " + currentAccount.balance);
                return;
            }
        }
        if(!accFound){
            System.out.println("Account Not Found!");
        }
    }

    static void withdrawMoney(String command){
        Matcher matcher = InputCommands.WITHDRAW_MONEY.matcher(command);
        matcher.matches();
        Long accNum = Long.parseLong(matcher.group(1));
        int pass = Integer.parseInt(matcher.group(2));
        Long amount = Long.parseLong(matcher.group(3));

        if(amount > 2000L)
        {
            System.out.println("Can not withdraw more than 2000$");
            return;
        }

        int bankBin = Integer.parseInt(matcher.group(1).substring(0, 6));

        int bankIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.bin == bankBin){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(bankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);

        for (Account account : bank.accountsList) {
            if(account.number.equals(accNum)){
                if(account instanceof DepositAccount){
                    System.out.println("Can not withdraw From deposit account!");
                    return;
                }
                CurrentAccount currentAccount = (CurrentAccount) account;
                switch (currentAccount.checkWithdraw(amount, calender)){
                    case -3 ->{
                        System.out.println("Owner has late loan instalment! No service!");
                        return;
                    }

                    case -2 ->{
                        System.out.println("Can not withdraw more than 2000$ per day!");
                        return;
                    }

                    case -1 ->{
                        System.out.println("Not Enough balance!");
                        return;
                    }
                }
                if(((CurrentAccount) account).card.pin != pass){
                    System.out.println("Wrong Password!");
                    return;
                }
                currentAccount.withdrawMoney(amount, calender);
                System.out.println("Money withdrawn successful!");
                System.out.println("Balance: " + currentAccount.balance);
                return;
            }
        }

        System.out.println("Account Not Found!");
    }

    static void withdrawMoneyBank(String command, boolean isCompany){
        Matcher matcher = InputCommands.WITHDRAW_MONEY_BANK.matcher(command);
        if(isCompany)
            matcher = InputCommands.WITHDRAW_MONEY_BANK_COMPANY.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        Long accNum = Long.parseLong(matcher.group(2));
        Long id = null;
        String compId = matcher.group(3);
        if(!isCompany)
            id = Long.parseLong(matcher.group(3));
        Long amount = Long.parseLong(matcher.group(4));

        int bankIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }
        if(bankIndex == -1){
            System.out.println("Bank not Found!");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);

        for (Account account : bank.accountsList) {
            if(account.number.equals(accNum)){
                if(!isCompany){
                    if (!account.owner.id.equals(id)) {
                        System.out.println("Account does not belong to this id!");
                        return;
                    }
                }

                if(isCompany){
                    if (!((LegalPerson) account.owner).id.equals(compId)) {
                        System.out.println("Account does not belong to this company id!");
                        return;
                    }
                }

                if(!(account instanceof CurrentAccount)){
                    System.out.println("Can not withdraw money from deposit account!");
                    return;
                }

                CurrentAccount currentAccount = (CurrentAccount) account;
                if(currentAccount.checkWithdrawBank(amount) == -2){
                    System.out.println("Not Enough Balance!");
                    return;
                }

                if(currentAccount.checkWithdrawBank(amount) == -1){
                    System.out.println("Owner has late loan instalment! No service!");
                    return;
                }
                currentAccount.withdrawMoney(amount, calender);
                System.out.println("Money withdrawn successful!");
                System.out.println("Balance: " + currentAccount.balance);
                return;
            }
        }

        System.out.println("Account not found!");
    }

    static void getAccountBalance(String command){
        Matcher matcher = InputCommands.GET_ACCOUNT_BALANCE.matcher(command);
        matcher.matches();
        Long accNum = Long.parseLong(matcher.group(1));
        int pass = Integer.parseInt(matcher.group(2));

        int bankBin = Integer.parseInt(matcher.group(1).substring(0, 6));

        int bankIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.bin == bankBin){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }
        if(bankIndex == -1){
            System.out.println("Bank not Found!");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);

        for (Account account : bank.accountsList) {
            if(account.number.equals(accNum)){
                if(!(account instanceof CurrentAccount)){
                    System.out.println("This is a Deposit Account!");
                    return;
                }
                CurrentAccount currentAccount = (CurrentAccount) account;
                if(currentAccount.card.pin != pass){
                    System.out.println("Wrong Password!");
                    return;
                }

                System.out.println("Account balance: " + account.balance);
                return;
            }
        }

        System.out.println("Account Not Found!");
    }

    static void transferMoney(String command){
        Matcher matcher = InputCommands.TRANSFER_MONEY.matcher(command);
        matcher.matches();
        Long cardNum = Long.parseLong(matcher.group(1));
        int pass = Integer.parseInt(matcher.group(2));
        Long receiverNum = Long.parseLong(matcher.group(3));
        Long amount = Long.parseLong(matcher.group(4));

        int bankBin = Integer.parseInt(matcher.group(1).substring(0, 6));
        int receiverBin = Integer.parseInt(matcher.group(3).substring(0, 6));

        int bankIndex = -1;
        int receiverIndex = -1;

        for (Bank bank : CentralBank.banks) {
            if(bank.bin == bankBin)
                bankIndex = CentralBank.banks.indexOf(bank);

            if(bank.bin == receiverBin)
                receiverIndex = CentralBank.banks.indexOf(bank);
        }

        if(bankIndex == -1){
            System.out.println("Bank not Found");
            return;
        }

        if(receiverIndex == -1){
            System.out.println("Receiver Bank not Found");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);
        Bank receiverBank = CentralBank.banks.get(receiverIndex);

        int accIndex = -1;
        int receiverAccIndex = -1;

        for (Account account : bank.accountsList) {
            if(account.number.equals(cardNum)){
                if(!(account instanceof CurrentAccount)){
                    System.out.println("Can not transfer from a Deposit account");
                    return;
                }
                if(((CurrentAccount) account).card.pin != pass){
                    System.out.println("Wrong Password!");
                    return;
                }
                accIndex = bank.accountsList.indexOf(account);
                break;
            }
        }

        for (Account account : receiverBank.accountsList) {
            if(account.number.equals(receiverNum)){
                if(!(account instanceof CurrentAccount)){
                    System.out.println("Can not transfer to a Deposit account");
                    return;
                }

                receiverAccIndex = receiverBank.accountsList.indexOf(account);
                break;
            }
        }

        if(accIndex == -1){
            System.out.println("Account Not Found!");
            return;
        }

        if(receiverAccIndex == -1){
            System.out.println("Receiver Account Not Found!");
            return;
        }

        CurrentAccount sender = (CurrentAccount) bank.accountsList.get(accIndex);
        CurrentAccount receiver = (CurrentAccount) receiverBank.accountsList.get(receiverAccIndex);

        if(sender.checkSend(amount, calender) == -1){
            System.out.println("Not Enough Balance!");
            return;
        }

        if(sender.checkSend(amount, calender) == -2){
            System.out.println("Can not transfer more than 5000$ per day!");
            return;
        }

        if(sender.checkSend(amount, calender) == -3){
            System.out.println("Owner has late loan instalment! No service!");
            return;
        }

        sender.send(amount, calender, receiver);
        receiver.receive(amount, calender, sender);
        System.out.println("Transfer Successful!\nAccount Balance: " + sender.balance);
    }

    static void transferMoneyBank(String command, boolean isCompany){
        Matcher matcher = InputCommands.TRANSFER_MONEY_BANK.matcher(command);
        if(isCompany)
            matcher = InputCommands.TRANSFER_MONEY_BANK_COMPANY.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        Long senderNum = Long.parseLong(matcher.group(2));
        Long id = null;
        String companyId = matcher.group(3);
        if(!isCompany)
            id = Long.parseLong(matcher.group(3));
        Long receiverNum = Long.parseLong(matcher.group(4));
        Long amount = Long.parseLong(matcher.group(5));
        int receiverBin = Integer.parseInt(matcher.group(4).substring(0, 6));
        int senderBankIndex = -1;
        int receiverBankIndex = -1;
        int senderIndex = -1;
        int receiverIndex = -1;

        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                senderBankIndex = CentralBank.banks.indexOf(bank);
            }

            else if(bank.bin == receiverBin){
                receiverBankIndex = CentralBank.banks.indexOf(bank);
            }
        }

        if(senderBankIndex == -1){
            System.out.println("Bank Not Found!");
            return;
        }

        if(receiverBankIndex == -1){
            System.out.println("Receiver Bank Not Found!");
            return;
        }

        Bank senderBank = CentralBank.banks.get(senderBankIndex);
        Bank receiverBank = CentralBank.banks.get(receiverBankIndex);

        for (Account account : senderBank.accountsList) {
            if(account.number.equals(senderNum)){
                senderIndex = senderBank.accountsList.indexOf(account);

                if(!(account instanceof CurrentAccount)){
                    System.out.println("Can not transfer from deposit account");
                    return;
                }

                if(!isCompany){
                    if (!account.owner.id.equals(id)) {
                        System.out.println("Account does not belong to this id!");
                        return;
                    }
                }

                if(isCompany){
                    if (!((LegalPerson) account.owner).id.equals(companyId)) {
                        System.out.println("Account does not belong to this company id!");
                        return;
                    }
                }
            }
        }

        for (Account account : receiverBank.accountsList) {
            if(account.number.equals(receiverNum)){
                if(!(account instanceof CurrentAccount)){
                    System.out.println("Can not transfer to deposit account");
                    return;
                }
                receiverIndex = receiverBank.accountsList.indexOf(account);
            }
        }

        if(senderIndex == -1){
            System.out.println("Account not Found!");
            return;
        }

        if(receiverIndex == -1){
            System.out.println("Receiver Account Not Found!");
            return;
        }

        CurrentAccount sender = (CurrentAccount) senderBank.accountsList.get(senderIndex);
        CurrentAccount receiver = (CurrentAccount) receiverBank.accountsList.get(receiverIndex);

        if(sender.checkWithdrawBank(amount) == -2){
            System.out.println("Not Enough Balance!");
            return;
        }

        if(sender.checkWithdrawBank(amount) == -1){
            System.out.println("Owner has late loan instalment! No service!");
            return;
        }

        sender.send(amount, calender, receiver);
        receiver.receive(amount, calender, sender);
        System.out.println("Transfer Successful!\nAccount Balance: " + sender.balance);
    }

    static void receiveLoan(String command, boolean isCompany){
        Matcher matcher = InputCommands.RECEIVE_LOAN.matcher(command);
        if(isCompany)
            matcher = InputCommands.RECEIVE_LOAN_COMPANY.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);
        Long id = null;
        String companyId = matcher.group(2);
        if(!isCompany)
            id = Long.parseLong(matcher.group(2));
        Long accNum = Long.parseLong(matcher.group(3));
        Long amount = Long.parseLong(matcher.group(4));

        int bankIndex = -1;
        int accIndex = -1;
        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(bankIndex == -1)
        {
            System.out.println("Bank Not Found!");
            return;
        }
        
        Bank bank = CentralBank.banks.get(bankIndex);

        for (Account account : bank.accountsList) {
            if(account.number.equals(accNum)){
                if(account instanceof DepositAccount){
                    System.out.println("Can not receive loan on deposit account!");
                    return;
                }
                accIndex = bank.accountsList.indexOf(account);
                break;
            }
        }

        if(accIndex == -1) {
            System.out.println("Account Not Found!");
            return;
        }

        CurrentAccount currentAccount = (CurrentAccount) bank.accountsList.get(accIndex);

        if(!isCompany){
            if(currentAccount.owner instanceof LegalPerson){
                System.out.println("Account does not belong to this id!");
                return;
            }
            if(!currentAccount.owner.id.equals(id)){
                System.out.println("Account does not belong to this id!");
                return;
            }
        }

        if(isCompany){
            if(!(currentAccount.owner instanceof LegalPerson)){
                System.out.println("Account does not belong to this Company!");
                return;
            }
            if(!((LegalPerson) currentAccount.owner).id.equals(companyId)){
                System.out.println("Account does not belong to this company id!");
                return;
            }
        }

        Person person = bank.accountsList.get(accIndex).owner;

        switch (person.checkLoanQualification(amount, bank)) {
            case -1 -> {
                System.out.println("Account owner is not old Enough!");
                return;
            }

            case -2 -> {
                System.out.println("Company CEO is not old Enough!");
                return;
            }

            case -3 -> {
                System.out.println("Account owner has active loan!");
                return;
            }

            case -4 -> {
                System.out.println("Requested amount of loan is more than possible!");
                return;
            }

        }
        person.receiveLoan(bank, amount, currentAccount);
        System.out.println("Loan Received Successfully!");
        System.out.println("Account Balance: " + currentAccount.balance);
    }

    static void payOffLoan(String command, boolean isCompany){
        Matcher matcher = InputCommands.PAY_LOAN.matcher(command);
        if(isCompany)
            matcher = InputCommands.PAY_LOAN_COMPANY.matcher(command);

        String bankName = matcher.group(1);
        String companyId = matcher.group(2);
        Long id = 0L;
        if(!isCompany)
            id = Long.parseLong(matcher.group(2));
        Long amount = Long.parseLong(matcher.group(3));

        int bankIndex = -1;

        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                bankIndex = CentralBank.banks.indexOf(bank);
                break;
            }
        }

        if(bankIndex == -1){
            System.out.println("Bank not Found!");
            return;
        }

        Bank bank = CentralBank.banks.get(bankIndex);
        if(isCompany) {
            for (Loan loan : bank.loansList) {
                if(loan.borrower instanceof LegalPerson){
                    if(((LegalPerson) loan.borrower).id.equalsIgnoreCase(companyId)){
                        loan.payLoan(amount);
                        System.out.println("Payment Successful!");
                        System.out.println("Account Balance: " + loan.currentAccount.balance);
                        return;
                    }
                }
            }
            System.out.println("Loan not Found!");
            return;
        }

        for (Loan loan : bank.loansList) {
            if(loan.borrower.id.equals(id)){
                loan.payLoan(amount);
                System.out.println("Payment Successful!");
                System.out.println("Account Balance: " + loan.currentAccount.balance);
                return;
            }
        }
        System.out.println("Loan not Found!");
    }

    static void updateOneDay(){
        Admin.calender = Admin.calender.goForward(1, 2);

        for (Bank bank : CentralBank.banks) {
            bank.update();
            for (Account account : bank.accountsList) {
                if((account instanceof DepositAccount) && !((DepositAccount) account).isExpired)
                    ((DepositAccount) account).update();
            }
        }

        for (Loan loan : CentralBank.loans) {
            loan.update();
        }
    }

    static void update(Long days){
        for (int i = 0; i < days; i++) {
            updateOneDay();
        }
    }

    static void showDate(){
        System.out.println(Admin.calender.toString());
    }

    static void showAllBanks(){
        System.out.printf("%-18s  %-10s  %-15s  %-15s  %-20s\n", "bank name", "BIN", "Bank Balance", "Loan Interest", "Show Establish Date");
        for (Bank bank1 : CentralBank.banks) {
            System.out.printf("%-18s  %-10d  %-15d  %-15d  %-20s\n", bank1.name, bank1.bin, bank1.balance, bank1.interest, bank1.establishDate.getIranianDate());
        }
    }

    static void showAllPersons(){
        System.out.printf("%-12s  %-15s  %-15s  %-15s\n", "Id", "Name", "Last Name", "Birth Date");

        for (Person person : people) {
            System.out.printf("%-12d  %-15s  %-15s  %-15s\n", person.id, person.getFirstName(), person.getLastName(), person.birthDate.getIranianDate());
        }
    }

    static void showAllCompanies(){
        System.out.printf("%-20s  %-15s  %-15s  %-15s\n", "Id", "Ceo Id", "Name", "Establish Date");

        for (LegalPerson company : companiesList) {
            System.out.printf("%-20s  %-15s  %-15s  %-15s\n", company.id, company.getCeo().id, company.name, company.getEstablishDate().getIranianDate());
        }
    }

    static void showAllAccounts(){
        System.out.printf("%-20s  %-20s  %-15s  %-10s\n", "Owner Id", "Account number", "Balance", "Type");
        for (Person person : CentralBank.personAccountMap.keySet()) {
            for (Account account : person.accountsList) {
                String type = "";
                if(account instanceof DepositAccount)
                    type = "Deposit";
                else
                    type = "Current";

                if(person instanceof LegalPerson)
                    System.out.printf("%-20s  %-20d  %-15d  %-15s\n", ((LegalPerson) person).id, account.number, account.balance, type);
                else
                    System.out.printf("%-20s  %-20d  %-15d  %-15s\n", person.id, account.number, account.balance, type);
            }
        }
    }

    static void showAllLoans(){
        System.out.printf("%-12s  %-10s  %-15s  %-15s  %-15s  %-15s  %-15s  %-15s\n", "Borrower Id", "Bank", "Value", "Payed Value", "Remaining", "Monthly Pay", "Late Payments", "End Date");
        for (Loan loan : CentralBank.loans) {
            if(loan.borrower instanceof LegalPerson)
                System.out.printf("%-12s  %-10s  %-15d  %-15d  %-15d  %-15d  %-15d  %-15s\n", ((LegalPerson) loan.borrower).id, loan.bank.name, loan.value, loan.payed, loan.toPay, loan.instalmentAmount, loan.lateInstalment, loan.expiration.getIranianDate());
            else
                System.out.printf("%-12d  %-10s  %-15d  %-15d  %-15d  %-15d  %-15d  %-15s\n", loan.borrower.id, loan.bank.name, loan.value, loan.payed, loan.toPay, loan.instalmentAmount, loan.lateInstalment, loan.expiration.getIranianDate());
        }
    }

    static void showPersonAccounts(String command, boolean isCompany){
        Matcher matcher = null;
        String companyId = null;
        Long id = null;
        if(isCompany) {
            matcher = InputCommands.SHOW_ACCOUNTS_LEGAL_PERSON.matcher(command);
            matcher.matches();
            companyId = matcher.group(1);
            for (LegalPerson legalPerson : companiesList) {
                if(legalPerson.id.equals(companyId)){
                    System.out.printf("%-20s  %-20s  %-15s  %-15s\n", "Owner Id", "Account number", "Balance", "Type");

                    for (Account account : legalPerson.accountsList) {
                        String type = "";
                        if(account instanceof DepositAccount)
                            type = "Deposit";
                        else
                            type = "Current";

                            System.out.printf("%-20s  %-20d  %-15d  %-15s\n", companyId, account.number, account.balance, type);
                    }
                    return;
                }
            }
            System.out.println("Company not Found!");
        }

        else {
            matcher = InputCommands.SHOW_ACCOUNTS_PERSON.matcher(command);
            matcher.matches();
            id = Long.parseLong(matcher.group(1));

            for (Person person : people) {
                if(person.id.equals(id)){
                    System.out.printf("%-20s  %-20s  %-15s  %-15s\n", "Owner Id", "Account number", "Balance", "Type");
                    for (Account account : person.accountsList) {
                        String type = "";
                        if(account instanceof DepositAccount)
                            type = "Deposit";
                        else
                            type = "Current";

                        System.out.printf("%-20d  %-20d  %-15d  %-15s\n", id, account.number, account.balance, type);
                    }
                    return;
                }
            }
            System.out.println("Person not Found!");
        }


    }

    static void showLoansPerson(String command, boolean isCompany){
        Matcher matcher = null;
        Long id = null;
        String companyId = null;
        if(isCompany){
            matcher = InputCommands.SHOW_LOANS_LEGAL_PERSON.matcher(command);
            matcher.matches();
            companyId = matcher.group(1);
            for (LegalPerson legalPerson : companiesList) {
                if(legalPerson.id.equalsIgnoreCase(companyId)){
                    System.out.printf("%-12s  %-10s  %-15s  %-15s  %-15s  %-15s  %-15s  %-15s\n", "Borrower Id", "Bank", "Value", "Payed Value", "Remaining", "Monthly Pay", "Late Payments", "End Date");

                    for (Loan loan : legalPerson.loans) {
                        System.out.printf("%-12s  %-10s  %-15d  %-15d  %-15d  %-15d  %-15d  %-15s\n", ((LegalPerson) loan.borrower).id, loan.bank.name, loan.value, loan.payed, loan.toPay, loan.instalmentAmount, loan.lateInstalment, loan.expiration.getIranianDate());
                    }
                    return;
                }
            }
            System.out.println("Company not Found!");
        }

        else {
            matcher = InputCommands.SHOW_LOANS_PERSON.matcher(command);
            matcher.matches();
            id = Long.parseLong(matcher.group(1));
            for (Person person : people) {
                if(person.id.equals(id)){
                    System.out.printf("%-12s  %-10s  %-15s  %-15s  %-15s  %-15s  %-15s  %-15s\n", "Borrower Id", "Bank", "Value", "Payed Value", "Remaining", "Monthly Pay", "Late Payments", "End Date");
                    for (Loan loan : person.loans) {
                        System.out.printf("%-12d  %-10s  %-15d  %-15d  %-15d  %-15d  %-15d  %-15s\n", loan.borrower.id, loan.bank.name, loan.value, loan.payed, loan.toPay, loan.instalmentAmount, loan.lateInstalment, loan.expiration.getIranianDate());
                    }
                    return;
                }
            }
            System.out.println("Person Not Found");
        }
    }

    static void showBankInfo(String command){
        Matcher matcher = InputCommands.SHOW_BANK_INFO.matcher(command);
        matcher.matches();
        String bankName = matcher.group(1);

        for (Bank bank : CentralBank.banks) {
            if(bank.name.equalsIgnoreCase(bankName)){
                System.out.printf("%-18s  %-10s  %-15s  %-15s  %-15s  %-15s  %-15s  %-20s\n", "bank name", "BIN", "Bank Balance", "Loan Interest", "Profit Rate", "Long Deposit Interest", "Short Deposit Interest", "Show Establish Date");
                System.out.printf("%-18s  %-10s  %-15d  %-15d  %-15d  %-15d  %-15d  %-20s\n", bank.name, bank.bin, bank.balance, bank.getInterest(), bank.getIncomeRate(), bank.getLtAccProfRate(), bank.getStAccProfRate(), bank.establishDate.getIranianDate());
                return;
            }
        }
        System.out.println("Bank Not Found!");
    }

}
