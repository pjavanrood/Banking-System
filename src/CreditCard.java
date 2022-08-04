public class CreditCard {
    Account account;
    boolean active;
    int pin;
    int cvv2;
    int secondPin;
    boolean secondPinActivation;
    CalendarTool expirationDate;

    public CreditCard(Account acc){
        account = acc;
        active = true;
        pin = (int) (Math.abs(Math.random()) * (9999 - 1000) + 1000);
        cvv2 = (int) (Math.abs(Math.random()) * (999 - 100) + 100);
        secondPin = -1;
        secondPinActivation = false;
        expirationDate = new CalendarTool();
        expirationDate.setGregorianDate(acc.openDate.getGregorianYear() + 4, acc.openDate.getGregorianMonth(), acc.openDate.getGregorianDay());
    }

    public void extendExpirationDate(){
        expirationDate = expirationDate.goForward(4, 0);
    }

}
