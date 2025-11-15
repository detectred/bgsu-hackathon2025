// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import com.sun.net.httpserver.Request;

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

    public static String DegreeRequest(String url) throws IOException {
        String apiParams = "{\"url\": \""+ url +"\", \"formats\": [\"markdown\"]}";
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
    //    System.out.println("Post Response Code: " + responseCode);
  //      System.out.println("Post Response Message: " + pconnection.getResponseMessage());
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
        //System.out.println(copied);
        return copied;
    }

    public static Map<String, List<String>> BGPRequest() throws IOException {
        String apiParams = "{\"url\": \"https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#bg-perspective-course-listing\", \"formats\": [\"markdown\"]}";
        String apiURL = "https://api.firecrawl.dev/v2/scrape";
        String apiKEY = "fc-9fcb6f29a01348119dbc2d22c0467b7c";
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
 //       System.out.println("Post Response Code: " + responseCode);
  //      System.out.println("Post Response Message: " + pconnection.getResponseMessage());
        BufferedReader br = new BufferedReader(new InputStreamReader(pconnection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = br.readLine())!=null){
            response.append(line);
        }
        br.close();
        System.out.println(response.toString());
        int stStart = response.indexOf("English Composition and Oral Communication<br>* * *<br>- [COMM");
        int stEnd = response.indexOf("(https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)\\n\\n[Close]");
        String copied = response.substring(stStart, stEnd);
      // copied = copied.replace("<br>", "\n");
       // copied = copied.replace("###", "");
        //copied = copied.replace("* * *", "");

        String resultString = copied;
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)", "");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt2752))", "");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt1797))","");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt3826))","");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt1008))","");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt8371))","");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt771))","");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt5322))","");
        resultString = resultString.replace("](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt2360))","");
        resultString = resultString.replace("<br>-","");
        resultString = resultString.replace("#", "");
        String[] starr = resultString.split("\\[");
        for (int i = 0; i < starr.length; i++) {
            System.out.println(starr[i]);
        }
        Map<String, List<String>> stmap = new HashMap<>();
        String key = null;
        List<String> value = new ArrayList<>();
        for (int i = 0; i < starr.length; i++) {

            if(starr[i].contains("also listed as")){

                value.add(starr[i].replace('[',' ') + starr[i+1]);
                i++;
            }
            if(starr[i].contains("<br>")){
                if(starr[i].contains("-")){
                    String[] temp = starr[i].split("<br>");
                    value.add(temp[0]);

                    stmap.put(key,value);
                    value = new ArrayList<>();
                    key = temp[1];

                }
                else {
                    key = " " + starr[i];
                }

            } else{
                value.add(starr[i]);
            }
        }

        for (Map.Entry<String,List<String>> entry : stmap.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());

        }



        return stmap;
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
        String requestResult = null;
        Map<String,List<String>> bgpString;
        if(majorURL!=null){
            try {
                requestResult = DegreeRequest(majorURL);
                try {
                   bgpString= BGPRequest();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    }

// English Composition and Oral Communication<br>* * *<br>- [COMM 1020 - Introduction to Public Speaking](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [WRIT 1110 - Seminar in Academic Writing](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [WRIT 1120 - Seminar in Research Writing](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>## Quantitative Literacy<br>* * *<br>- [BA 1600 - Business Analytics, Quantitative Analysis for Business Applications I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [BA 1700 - Business Analytics II; Quantitative Analysis for Business Applications II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [BA 2110 - Business Analytics III: Descriptive Analytics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [BA 2120 - Business Analytics IV: Predictive Analytics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MATH 1150 - Introduction to Statistics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MATH 1190 - Real World Math Skills](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MATH 1220 - College Algebra](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4)<br>- [MATH 1230 - Mathematics for Architecture/Construct](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5)<br>- [MATH 1260 - Basic Calculus](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5)<br>- [MATH 1280 - Precalculus Mathematics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5)<br>- [MATH 1310 - Calculus and Analytic Geometry](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5)<br>- [MATH 1340 - Calculus and Analytic Geometry IA](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MATH 1350 - Calculus and Analytic Geometry IB](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POLS 2900 - Statistics and Research Methods](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PSYC 2700 - Quantitative Methods I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4)<br>- [SOC 2690 - Introductory Statistics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [STAT 2000 - Using Statistics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>## Humanities and the Arts<br>* * *<br>- [ACS 2000 - Introduction to American Culture Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ACS 2500 - Cultural Pluralism in the United States](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ARCH 2330 - History of Architecture I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ARCH 2340 - History of Architecture II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ART 1010 - Introduction to Art](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ARTH 1450 - Western Art I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ARTH 1460 - Western Art II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ARTH 2700 - Survey of World Art](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [AS 1100 - Arts BG](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [CLCV 2410 - Great Greek Minds](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [CLCV 2420 - Great Roman Minds](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [CLCV 3800 - Classical Mythology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 1500 - Literature and Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2010 - Introduction to Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2110 - African-American Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ENG 2120 - Native American Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ENG 2610 - World Literature from Ancient Times to 1700](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ENG 2620 - World Literature from 1700 to Present](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ENG 2640 - British Literature Survey to 1660](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2650 - British Literature Survey, 1660-1945](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2740 - Survey of American Literature to 1865](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2750 - Survey of American Literature, 1865-1945](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 2200 - Introduction to African Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\\\* (also listed as [ROCS 2200](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt8371))<br>- [FREN 2010 - Intermediate French I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [FREN 2020 - Intermediate French II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [FREN 2220 - French Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [GERM 2150 - German Culture and Civilization](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [GERM 2160 - Contemporary Germany](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [HNRS 2020 - Critical Thinking about Great Ideas](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ITAL 2620 - Italian-American Experience: Mafia, Migration, and the Movies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [MUCT 1010 - Exploring Music](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MUCT 1250 - Exploring Music of World Cultures](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [MUCT 2220 - Turning Points: Arts and Humanities in Context](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MUCT 2610 - Music History I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 1010 - Introduction to Philosophy](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 1020 - Introduction to Ethics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 1030 - Introduction to Logic](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 1250 - Contemporary Moral Issues](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 2190 - Philosophy of Death and Dying](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [PHIL 2320 - Environmental Ethics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [PHIL 2420 - Medical Ethics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POPC 1600 - Introduction to Popular Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POPC 1650 - Popular Culture and Media](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POPC 1700 - Black Popular Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [POPC 2200 - Introduction to Folklore and Folklife](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ROCS 2200 - Introduction to African Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\\\* (also listed as [ETHN 2200](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt771))<br>- [RUSN 2150 - Russian Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [RUSN 2160 - Post-Communist Russia](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [SPAN 2010 - Intermediate Spanish I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [SPAN 2020 - Intermediate Spanish II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [SPAN 2030 - Intermediate Spanish for the Professions](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [SPAN 2700 - Hispanic Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [THFM 1410 - The Theatre Experience](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [THFM 1610 - Introduction to Film](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [THFM 2020 - Performance in Life & on Stage](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [THFM 2150 - Exploring Cultural Diversity Through Performance](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [WS 2000 - Introduction to Women’s Studies: Perspectives on Gender, Class and Ethnicity](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>## Social and Behavioral Sciences<br>* * *<br>- [AFRS 2000 - Introduction to Africana Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [ASIA 1800 - Asian Civilizations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\\\* (also listed as [HIST 1800](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt5322))<br>- [ASIA 2000 - Introduction to Asian Religion](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [CAST 2010 - Introduction to Canadian Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [CDIS 1230 - Introduction to Communication Disorders](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ECON 2000 - Introduction to Economics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ECON 2020 - Principles of Microeconomics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ECON 2030 - Principles of Macroeconomics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [EDFI 2980 - Schools, Society, and Cultural Diversity](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [EIEC 2210 - Cultural and Linguistic Diversity in Early Childhood Education](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ENVS 1010 - Introduction to Environmental Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 1010 - Introduction to Ethnic Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ETHN 1100 - Introduction to Latina/o Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ETHN 1200 - Introduction to African American Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ETHN 1300 - Introduction to Asian American Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ETHN 1600 - Introduction to Native American Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ETHN 2010 - Ethnicity and Social Movements](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [ETHN 2600 - Contemporary Issues in Native America](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [GEOG 1210 - World Geography: Eurasia and Africa](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [GEOG 1220 - World Geography: Americas and the Pacific](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [GEOG 2300 - Cultural Geography](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GEOG 2630 - Geography of China, People, Place and Environment](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [GERO 1010 - Aging, the Individual and Society](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HDFS 1930 - Lifespan Human Development](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HDFS 2020 - Contemporary Marriages and Families](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HIST 1250 - Early America](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [HIST 1260 - Modern America](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HIST 1510 - World Civilizations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [HIST 1520 - The Modern World](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [HIST 1800 - Asian Civilizations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\\\* (also listed as [ASIA 1800](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt1008))<br>- [HNRS 2010 - Introduction to Critical Thinking](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [INST 2000 - Introduction to International Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [MDIA 1030 - Media and the Information Society](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MDIA 3520 - Social Media and Society](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POLS 1100 - American Government: Processes and Structure](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POLS 1710 - Introduction to Comparative Government](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [POLS 1720 - Introduction to International Relations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [PSYC 1010 - General Psychology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4)<br>- [SOC 1010 - Principles of Sociology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SOC 2020 - Social Problems](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SOC 2120 - Population and Society](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SOC 2160 - Race, Ethnicity & Inequality](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*\\*<br>- [SOC 2310 - Cultural Anthropology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>- [TECH 3020 - Technology Systems in Societies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) \\*<br>## Natural Sciences<br>* * *<br>**an “L” denotes the course fulfills the Lab Science requirement**<br>- [ASTR 1010 - Experimental Astronomy](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (L)<br>- [ASTR 2010 - Modern Astronomy](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ASTR 2120 - The Solar System](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [BIOL 1010 - Environment of Life](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (L)<br>- [BIOL 1040 - Introduction to Biology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4) (L)<br>- [BIOL 1080 - Life in the Sea](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [BIOL 2040 - Concepts in Biology I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4) (L)<br>- [BIOL 2050 - Concepts in Biology II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4) (L)<br>- [CHEM 1000 - Introduction to Chemistry](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [CHEM 1090 - Elementary Chemistry](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (3)<br>- [CHEM 1100 - Elementary Chemistry Laboratory](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (1) (L)<br>- [CHEM 1230 - General Chemistry I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4)<br>- [CHEM 1240 - General Chemistry I Laboratory](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (1) (L)<br>- [CHEM 1350 - General Chemistry](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5) (L)<br>- [FN 2070 - Introduction to Human Nutrition](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [FN 2080 - Introduction to Human Nutrition Laboratory](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (1) (L)<br>- [GEOG 1250 - Weather and Climate](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GEOG 1260 - Weather Studies Laboratory](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (L)<br>- [GEOL 1000 - Introduction to Geology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GEOL 1040 - Earth Environments](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4) (L)<br>- [GEOL 1050 - Life Through Time](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (4) (L)<br>- [GEOL 2150 - Geologic History of Dinosaurs](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (L)<br>- [PHYS 1010 - Basic Physics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (L)<br>- [PHYS 2010 - College Physics I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5) (L)<br>- [PHYS 2020 - College Physics II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5) (L)<br>- [PHYS 2110 - University Physics I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5) (L)<br>- [PHYS 2120 - University Physics II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (5) (L)<br>- [SEES 2220 - Water Resources and Issues](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>## Cultural Diversity in the United States<br>* * *<br>### Cultural Diversity In The United States Courses Approved To Also Fulfill A Humanities And The Arts Requirement<br>* * *<br>- [ACS 2500 - Cultural Pluralism in the United States](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2120 - Native American Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2110 - African-American Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ITAL 2620 - Italian-American Experience: Mafia, Migration, and the Movies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 2320 - Environmental Ethics](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POPC 1700 - Black Popular Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [THFM 2150 - Exploring Cultural Diversity Through Performance](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [WS 2000 - Introduction to Women’s Studies: Perspectives on Gender, Class and Ethnicity](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>### Cultural Diversity In The United States Courses Approved To Also Fulfill A Social And Behavioral Sciences Requirement<br>* * *<br>- [EDFI 2980 - Schools, Society, and Cultural Diversity](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [EIEC 2210 - Cultural and Linguistic Diversity in Early Childhood Education](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 1010 - Introduction to Ethnic Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 1100 - Introduction to Latina/o Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 1200 - Introduction to African American Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 1300 - Introduction to Asian American Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 1600 - Introduction to Native American Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 2010 - Ethnicity and Social Movements](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 2600 - Contemporary Issues in Native America](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HIST 1250 - Early America](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SOC 2160 - Race, Ethnicity & Inequality](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>## International Perspective<br>* * *<br>- [GERM 2010 - Intermediate German I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GERM 2020 - Intermediate German II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>### International Perspective Courses Approved To Also Fulfill Humanities And The Arts Requirement<br>* * *<br>- [ARCH 2330 - History of Architecture I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ARCH 2340 - History of Architecture II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ARTH 1450 - Western Art I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ARTH 1460 - Western Art II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ARTH 2700 - Survey of World Art](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [CLCV 2410 - Great Greek Minds](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [CLCV 2420 - Great Roman Minds](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2610 - World Literature from Ancient Times to 1700](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ENG 2620 - World Literature from 1700 to Present](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ETHN 2200 - Introduction to African Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (also listed as [ROCS 2200](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt2360))<br>- [FREN 2010 - Intermediate French I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [FREN 2020 - Intermediate French II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [FREN 2220 - French Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GERM 2150 - German Culture and Civilization](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [MUCT 1250 - Exploring Music of World Cultures](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [PHIL 2190 - Philosophy of Death and Dying](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ROCS 2200 - Introduction to African Literature](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (also listed as [ETHN 2200](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt1797))<br>- [RUSN 2150 - Russian Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [RUSN 2160 - Post-Communist Russia](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SPAN 2010 - Intermediate Spanish I](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SPAN 2020 - Intermediate Spanish II](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SPAN 2030 - Intermediate Spanish for the Professions](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SPAN 2700 - Hispanic Culture](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>### International Perspective Courses Approved To Also Fulfill A Social And Behavioral Sciences Requirement<br>* * *<br>- [AFRS 2000 - Introduction to Africana Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [ASIA 1800 - Asian Civilizations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (also listed as [HIST 1800](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt2752))<br>- [ASIA 2000 - Introduction to Asian Religion](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [CAST 2010 - Introduction to Canadian Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GEOG 1210 - World Geography: Eurasia and Africa](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GEOG 1220 - World Geography: Americas and the Pacific](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [GEOG 2630 - Geography of China, People, Place and Environment](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HIST 1510 - World Civilizations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HIST 1520 - The Modern World](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [HIST 1800 - Asian Civilizations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) (also listed as [ASIA 1800](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#tt3826))<br>- [INST 2000 - Introduction to International Studies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POLS 1710 - Introduction to Comparative Government](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [POLS 1720 - Introduction to International Relations](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [SOC 2310 - Cultural Anthropology](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#)<br>- [TECH 3020 - Technology Systems in Societies](https://catalog.bgsu.edu/preview_program.php?catoid=21&poid=8058&hl=%22BG+perspective%22&returnto=search#) |\n\n[Close]