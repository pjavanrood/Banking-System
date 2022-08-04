import java.util.ArrayList;

public class LegalPerson extends Person{
    final Person ceo;
    final CalendarTool establishDate;
    public String name;
    public String id;
    public LegalPerson(Person c, CalendarTool d, String n){
        super();
        ceo = c;
        establishDate = d;
        name = n;
        id = setId(d, n);
    }

    private String setId(CalendarTool d, String n){
        String id = "";
        id = Integer.toString(d.getIranianYear()) + Integer.toString(d.getIranianMonth()) + Integer.toString(d.getIranianDay()) + "_";
        for (String s : n.split(" ")) {
            id += s + "_";
        }
        return id.substring(0, id.length() - 1);
    }

    public Person getCeo() {
        return ceo;
    }

    public CalendarTool getEstablishDate() {
        return establishDate;
    }
}
