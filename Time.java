import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Time {
    public static void main(String[] args) {
        LocalDate myObj = LocalDate.now(); // Create a date object
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String formattedDate = myObj.format(myFormatObj);
        System.out.println(formattedDate); // Display the current date
    }

}
