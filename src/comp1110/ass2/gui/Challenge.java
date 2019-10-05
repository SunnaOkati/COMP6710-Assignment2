package comp1110.ass2.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge {

    // Author: Victor
    // Task 8
    // This regex checker is added just to clean up the fileScraper method
    public static String regexChecker(String regex, String checkString){
        Pattern checkRegex = Pattern.compile(regex);
        Matcher regexMatcher = checkRegex.matcher(checkString);

        // I'm unsure how regex groups work, but this makes sure that the output is a String
        // Git commit comment
        String myStr = null;

        // While searching through the string
        while(regexMatcher.find()){
            // Ensures that no empty strings are passed through
            if(regexMatcher.group().length() != 0){

                // If the string is empty give it a value, else add to its previous value
                if(myStr == null){
                    myStr = regexMatcher.group();
                }else{
                    myStr = myStr + regexMatcher.group();
                }

            }
        }

        // Just for debugging
        System.out.println("myStr final " + myStr);
        return myStr;
    }


    // Author: Victor
    // Task 8
    // Scrapes the TestUtility.java file
    public static String fileScraper(String fileName){
        // References each line 1 by 1
        String line = null;
        String output = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            // BufferedReader is here for good practice
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                // While the line isn't empty, if it is one of the solutions,
                // which will be found out using the regex method
                // then print it to the terminal for testing
                if (regexChecker("[A,Z]{9}", line) != null) {
                    System.out.println(regexChecker("[A,Z]{9}", line));
                    if(output == null){
                        output = regexChecker("[A,Z]{9}", line);
                    }else{
                        output = output + regexChecker("[A,Z]{9}", line);
                    }
                }
            }


            bufferedReader.close();
        }
        // These catches were taken straight from the source material
        // FileReader does not compile without exception handling
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }
        return output;
    }

    // Just a series of if statements that decide which square is chosen
    public static String squareColour(Character square){
        if(square == 'B') {
            return "comp1110/ass2/gui/assets/sq-b.png";
        }
        if(square == 'G') {
            return "comp1110/ass2/gui/assets/sq-g.png";
        }
        if(square == 'R') {
            return "comp1110/ass2/gui/assets/sq-r.png";
        }
        if(square == 'W'){
            return "comp1110/ass2/gui/assets/sq-w.png";
        }else{
            System.out.println(square);
            System.out.println("Invalid input, learn how to throw errors");
            return null;
        }
    }
}
