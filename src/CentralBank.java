import java.util.ArrayList;
import java.util.HashMap;

public class CentralBank {
    public static ArrayList<Bank> banks;
    public static HashMap<Person, ArrayList<Account> > personAccountMap;
    public static ArrayList<Loan> loans;
    public CentralBank(){
        banks = new ArrayList<>();
        personAccountMap = new HashMap<>();
        loans = new ArrayList<>();
    }
    public static Long generateAccountNumber(int bin, int count){
        String accNum = Integer.toString(bin);
         int zeoLen = 7 - Integer.toString(count).length();
         int rand = (int) (Math.abs(Math.random()) * (99 - 10) + 10);
         accNum += Integer.toString(rand);
         for(int i = 0; i < zeoLen; i++)
             accNum += "0";
         accNum += Integer.toString(count);
         int x = 0;
         for(int i = 0; i < 14; i+=2) {
             int c = 2*(accNum.charAt(i) - '0');
             if(c > 10)
                 c -= 9;
             x += c;
         }
         int y = 0;
         for(int i = 1; i < 14; i+=2)
             y += accNum.charAt(i) - '0';
         int sum = x + y;
         if((sum/10 + 1)*10 - sum == 10){
             accNum += "0";
             return Long.parseLong(accNum);
         }
         accNum += Integer.toString((sum/10 + 1)*10 - sum);
         return Long.parseLong(accNum);
    }
}
