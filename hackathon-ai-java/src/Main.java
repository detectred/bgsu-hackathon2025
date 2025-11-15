// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.*;

enum Major {
    CS, SE
}
enum BachType{
    S, A
}
public class Main {
    private static void Intro(){
        System.out.println("Major Options");
        System.out.println("---------------");
        System.out.println("Computer Science (CS)");
        System.out.println("Software Engineering (SE)");
        System.out.println("---------------");
    }
    private static Major MajorValidation(Scanner userInput){
        String tempMajor;
        //Validation of user input
        boolean validated = false;
        Major userMajor = null;
        do {
            System.out.print("\nWhat is your major? Enter 2 letters: ");
            tempMajor = userInput.next();
            tempMajor = tempMajor.toUpperCase();
            try {
                userMajor = Major.valueOf(tempMajor);
            } catch (IllegalArgumentException e1) {
                continue;
            }
            if (!userMajor.equals(null)){
                validated = true;
            }
        } while (!validated);
        return userMajor;
    }

    private static BachType BachTypeValidation(Scanner userInput){
        //Scanner userInputBach = new Scanner(System.in);
        String tempBachType;
        //Validation of user input
        boolean validated = false;
        BachType userBachType = null;
        do {
            System.out.print("\nWhat is your Degree? Arts (A) or Science (S) Enter 1 Letter: ");
            tempBachType = userInput.next();
            tempBachType = tempBachType.toUpperCase();
            try {
                userBachType = BachType.valueOf(tempBachType);
            } catch (IllegalArgumentException e1) {
                System.out.println("Illegal Argument IAE");
                continue;
            }
            catch (NoSuchElementException e2) {
                System.out.println("Illegal Argument NSEE");
                continue;
            }

            if (!userBachType.equals(null)){
                validated = true;
            }
        } while (!validated);
        return userBachType;
    }



    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Intro();
        Major userMajor = MajorValidation(userInput);
        String majorURL = null;
        String minorURL;
        BachType userBachType = BachTypeValidation(userInput);
        userInput.close();


        System.out.print("\nMajor Selected: ");

        //Selects the URL based on the major and if that major offers a Bach degree in both A&S or just A or just S.
        switch (userMajor) {
            case Major.CS:
                System.out.print("Computer Science");
                switch (userBachType) {
                    case BachType.S:
                            majorURL = "https://catalog.bgsu.edu/preview_program.php?catoid=23&poid=8642&hl=%22computer+science%22&returnto=search";
                            System.out.print(" of Science.");
                            break;
                    case BachType.A:
                            majorURL = "https://catalog.bgsu.edu/preview_program.php?catoid=23&poid=8579&hl=%22computer+science%22&returnto=search";
                            System.out.print(" of Arts.");
                            break;
                }
                break;
            case Major.SE:
                System.out.print("Software Engineering");
                majorURL = "https://catalog.bgsu.edu/preview_program.php?catoid=20&poid=7029&returnto=1517";
                break;
            case null:
                System.out.print("NULL POINTER CATCH");
        }
        
        System.out.println("\nMajor Website: " + majorURL);
    }
}