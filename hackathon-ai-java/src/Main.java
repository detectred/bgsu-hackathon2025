// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static void Request() throws IOException {
        String apiParams = "{\"url\": \"https://catalog.bgsu.edu/preview_program.php?catoid=23&poid=8642\", \"formats\": [\"markdown\"]}";
        String apiURL = "https://api.firecrawl.dev/v2/scrape";
        String apiKEY = "fc-9fcb6f29a01348119dbc2d22c0467b7c";
   /* HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create(apiURL))
            .POST(HttpRequest.BodyPublisher.ofString())
            .header("Authorization: ", apiKEY)
            .build();
*/
        URL obj = new URL(apiURL);
        HttpURLConnection pconnection = (HttpURLConnection)obj.openConnection();
        pconnection.setRequestMethod("POST");
        pconnection.setRequestProperty("Authorization", "Bearer " +apiKEY);
        pconnection.setRequestProperty("Content-Type", "application/json");


        pconnection.setDoOutput(true);
        OutputStream os = pconnection.getOutputStream();
        os.write(apiParams.getBytes());
        os.flush();
        os.close();
        int responseCode = pconnection.getResponseCode();
        System.out.println("Post Response Code: " + responseCode);
        System.out.println("Post Response Message: " + pconnection.getResponseMessage());
        BufferedReader br = new BufferedReader(new InputStreamReader(pconnection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = br.readLine())!=null){
            response.append(line);
        }
        br.close();
        //System.out.println(response.toString());
        int stStart = response.indexOf("BG Perspective (BGP) Requirements<br>");
        int stEnd = response.indexOf("Electives and Non-Credit Courses");
        String copied = response.substring(stStart, stEnd);
        copied = copied.replace("<br>", "\n");
        copied = copied.replace("\\\\","");
        copied = copied.replace("###", "");
        copied = copied.replace("* * *", "");
        System.out.println(copied);
    }


    public static void main(String[] args) {
        String linkBGP = "https://catalog.bgsu.edu/preview_program.php?catoid=23&poid=8681#bgsu-core-learning-objectives";
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