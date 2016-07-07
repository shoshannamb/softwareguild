/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.ui;

import com.sg.bankmanager.dto.Account;
import com.sg.bankmanager.dto.CDAccount;
import com.sg.bankmanager.dto.CheckingAccount;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class BankManagerUI {

    ConsoleIO con = new ConsoleIO();

    public int getID() {
        con.display("Welcome to Thrifty Bank!");
        return con.promptInt("Please enter your id# or 0 to exit: ");
    }

    public int getPIN() {
        return con.promptInt("Please enter your PIN: ");
    }

    public void showError(String error) {
        con.display(error);
    }

    public int showMenu() {
        con.display("Would you like to:");
        con.display("1. Check your account balances");
        con.display("2. Make a withdrawal");
        con.display("3. Make a deposit");
        con.display("4. See overdrawn accounts");
        con.display("5. See maturity information for CDAccounts");
        con.display("6. See total in your accounts");
        con.display("7. Quick withdraw from highest checkings account");
        con.display("Or enter 0 to go back");
        return con.promptIntInRange("> ", 0, 7);
    }

    public void showBalances(double[] accounts) {
        con.display("Checking account: $" + accounts[0]);
        con.display("Savings account: $" + accounts[1]);
    }

    public int showAccountOptions() {
        con.display("Would you like to:");
        con.display("1. Make a deposit");
        con.display("2. Make a withdrawal");
        con.display("Or enter 0 to go back");
        return con.promptIntInRange("> ", 0, 2);
    }

    public double getAmount(String action) {
        double amount;
        do {
            amount = con.promptDouble("How much would you like to " + action + "? ");
            if (amount <= 0) {
                con.display("Please enter a valid amount.");
            }
        } while (amount <= 0);
        return amount;
    }

    public void displayAccountsAndBalances(List<Account> clientAccounts) {
        for (Account a : clientAccounts) {
            if (a instanceof CheckingAccount) {
                con.display(a.getID() + ": $" + a.getBalance() + " (Checkings)");
            } else if (a instanceof CDAccount) {
                con.display(a.getID() + ": $" + a.getBalance() + " (CD Account)");
            } else {
                con.display(a.getID() + ": $" + a.getBalance() + " (Savings Account)");
            }
        }
        con.nl();
    }

    public int getAccountSelection() {
        return con.promptInt("Enter the account id: ");
    }

    public void displayAccountsAndTypes(List<Account> clientAccounts) {
        for (Account a : clientAccounts) {
            if (a instanceof CheckingAccount) {
                con.display(a.getID() + ": Checkings");
            } else if (a instanceof CDAccount) {
                con.display(a.getID() + ": CD Account");
            } else {
                con.display(a.getID() + ": Savings Account");
            }
        }
        con.nl();
    }

    public void displayCDAccountInfo(List<CDAccount>[] cdAccounts) {
        con.display("Immature accounts: ");
        for (Account a : cdAccounts[0]) {
            con.display(a.getID() + ": " + a.getBalance());
        }
        con.nl();
        con.display("Mature accounts:");
        for (Account a : cdAccounts[1]) {
            con.display(a.getID() + ": " + a.getBalance());
        }
        con.nl();
    }

    public void displayTotalMoney(double totalInAccounts) {
        con.display("Your total amount in this account is $" + totalInAccounts);
        con.display("Thank you for investing with Thrifty Bank!");
        con.nl();
    }
}
