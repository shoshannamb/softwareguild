/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author SMB
 */
public class ConsoleIO {

    private Scanner inReader = new Scanner(System.in);

    public String promptString(String prompt) {
        System.out.print(prompt);
        String s = inReader.nextLine();
        return s;
    }

    public void display(Object o) {
        System.out.println(o);
    }

    public int promptInt(String prompt) {
        System.out.print(prompt);
        int myInt = 0;
        boolean needAnInt = true;
        while (needAnInt) {
            try {
                myInt = inReader.nextInt();
                needAnInt = false;
            } catch (InputMismatchException exception) {
                System.out.print("Please enter an integer: ");
                inReader.nextLine();
            }
        }
        inReader.nextLine();
        return myInt;
    }

    public int promptIntInRange(String prompt, int min, int max) {
        String errorMessage = "Please enter an integer between " + min + " and " + max;
        int userInput;
        do {
            userInput = promptInt(prompt);
            if (userInput < min || userInput > max) {
                System.out.println(errorMessage);
            }
        } while (userInput < min || userInput > max);
        return userInput;
    }

    public float promptFloat(String prompt) {
        System.out.print(prompt);
        float userInput = 0;
        boolean needAFloat = true;
        while (needAFloat) {
            try {
                userInput = inReader.nextFloat();
                needAFloat = false;
            } catch (InputMismatchException exception) {
                System.out.print("Please enter a number: ");
                inReader.nextLine();
            }
        }
        inReader.nextLine();
        return userInput;
    }

    public float promptFloatInRange(String prompt, float min, float max) {
        String errorMessage = "Please enter a number between " + min + " and " + max;
        float userInput;
        while (true) {
            userInput = promptFloat(prompt);
            if (userInput > min && userInput < max) {
                return userInput;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    public double promptDouble(String prompt) {
        System.out.print(prompt);
        double userInput = 0;
        boolean needADouble = true;
        while (needADouble) {
            try {
                userInput = inReader.nextDouble();
                needADouble = false;
            } catch (InputMismatchException exception) {
                System.out.print("Please enter a number: ");
                inReader.nextLine();
            }
        }
        inReader.nextLine();
        return userInput;
    }

    public double promptDoubleInRange(String prompt, double min, double max) {
        String errorMessage = "Please enter a number between " + min + " and " + max;
        double userInput;
        while (true) {
            userInput = promptDouble(prompt);
            if (userInput > min && userInput < max) {
                return userInput;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    public LocalDate promptDate(String prompt) {
        String userInput;
        while (true) {
            System.out.print(prompt + " (MM-DD-YYYY): ");
            userInput = inReader.nextLine();
            if (userInput.length() != 10) {
                System.out.println("Please enter a date with the given format.");
            } else {
                String localDateFormat = userInput.substring(6) + "-" + userInput.substring(0, 2) + "-" + userInput.substring(3, 5);
                try {
                    LocalDate validDate = LocalDate.parse(localDateFormat);
                    return validDate;
                } catch (DateTimeParseException e) {
                    System.out.println("Please enter a date with the given format.");
                }
            }
        }
    }

    public void nl() {
        System.out.println();
    }

    double promptPositiveDouble(String prompt) {
        double userInput;
        while (true) {
            userInput = promptDouble(prompt);
            if (userInput > 0 && userInput < Double.MAX_VALUE) {
                return userInput;
            } else {
                System.out.println("Please enter a positive number.");
            }
        }
    }

    // returns y or n
    boolean promptYesOrNo(String prompt) {
        String userInput;
        while (true) {
            userInput = promptString(prompt).toLowerCase();
            switch (userInput) {
                case "yes":
                case "y":
                    return true;
                case "no":
                case "n":
                    return false;
                default:
                    System.out.println(userInput + " is not one of the options.");
            }
        }
    }

    String promptStringWithoutRegex(String prompt, String regex) {
        String userInput;
        while(true) {
            userInput = promptString(prompt);
            if(userInput.contains(regex))
                System.out.println("'" + regex + "' not allowed.");
            else
                return userInput;
        }
    }
}
