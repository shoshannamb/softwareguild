/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.ui;

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
                System.out.print("Invalid input. Please enter an integer: ");
                inReader.nextLine();
            }
        }
        inReader.nextLine();
        return myInt;
    }

    public int promptIntInRange(String prompt, int min, int max) {
        String errorMessage = "Invalid input. Please enter an integer between " + min + " and " + max;
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
                System.out.print("Invalid input. Please enter a number: ");
                inReader.nextLine();
            }
        }
        inReader.nextLine();
        return userInput;
    }

    public float promptFloatInRange(String prompt, float min, float max) {
        String errorMessage = "Invalid input. Please enter a number (f) between " + min + " and " + max;
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
                System.out.print("Invalid input. Please enter a number: ");
                inReader.nextLine();
            }
        }
        inReader.nextLine();
        return userInput;
    }

    public double promptDoubleInRange(String prompt, double min, double max) {
        String errorMessage = "Invalid input. Please enter a number (d) between " + min + " and " + max;
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
    
    public void nl() {
        System.out.println();
    }

}
