/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager;

import com.sg.bankmanager.dao.BankManagerDAO;
import com.sg.bankmanager.dto.Account;
import com.sg.bankmanager.ui.BankManagerUI;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author apprentice
 */
class BankManagerController {

    BankManagerDAO bank;
    BankManagerUI ui = new BankManagerUI();
    final String LOCKED = "Your account is locked. Please contact customer service.";

    void run() {
        bank = new BankManagerDAO(); // load sample data
//        try {
//            bank = new BankManagerDAO("bank.txt");
//        } catch (FileNotFoundException e) {
//            ui.showError("Problem loading from file. Accessing sample accounts.");
//            bank = new BankManagerDAO(); // load sample data
//        }
        boolean stillRunning = true;
        while (stillRunning) {
            int id = ui.getID();
            if (id == 0) {
                return;
            } else if (validate(id)) {
                boolean loopMenu = true;
                while (loopMenu) {
                    int selection = ui.showMenu();
                    switch (selection) {
                        case 0:
                            // dao.save();
                            loopMenu = false;
                            break;
                        case 1:
                            getBalances(id);
                            break;
                        case 2:
                            withdraw(id);
                            break;
                        case 3:
                            deposit(id);
                            break;
                        case 4:
                            seeOverDrawnAccounts(id);
                            break;
                        case 5:
                            seeCDAccountInfo(id);
                            break;
                        case 6:
                            seeTotalInAccounts(id);
                            break;
                        case 7:
                            quickWithdraw(id);
                            break;
                    }
                }
            }
        }
    }

    public boolean validate(int id) {
        if (!bank.clientExists(id)) {
            ui.showError("That id# was not found.");
            return false;
        } else if (bank.checkLockedStatus(id)) {
            ui.showError(LOCKED);
            return false;
        } else {
            int tries = 5;
            int pin = -1;
            while (tries > 0 && !bank.checkPIN(id, pin)) { // 5 tries to get into account
                pin = ui.getPIN();
                if (!bank.checkPIN(id, pin)) {
                    ui.showError("Incorrect PIN.");
                }
                tries--;
            } 
            if (!bank.checkPIN(id, pin)) {
                bank.lockClient(id);
                ui.showError(LOCKED);
                return false;
            } else {
                return true;
            }
        }
    }

    private void deposit(int id) {
        ui.displayAccountsAndTypes(bank.getClientAccounts(id));
        int accountID = ui.getAccountSelection();
        double money = ui.getAmount("deposit");
        bank.deposit(id, accountID, money);
    }

    private void withdraw(int id) {
        ui.displayAccountsAndTypes(bank.getClientAccounts(id));
        int accountID = ui.getAccountSelection();
        double money = ui.getAmount("withdraw");
        boolean validWithdrawal = bank.withdraw(id, accountID, money);
        if (!validWithdrawal) {
            ui.showError("Insufficient funds");
        }
    }

    private void getBalances(int id) {
        ui.displayAccountsAndBalances(bank.getClientAccounts(id));
    }

    private void seeOverDrawnAccounts(int id) {
        ui.displayAccountsAndBalances(bank.getOverdrawnAccounts(id));
    }

    private void seeCDAccountInfo(int id) {
        ui.displayCDAccountInfo(bank.getCDAccounts(id));
    }

    private void seeTotalInAccounts(int id) {
        ui.displayTotalMoney(bank.getTotalInAccounts(id));
    }

    // withdraws from highest checkings account
    private void quickWithdraw(int id) {
        bank.withDrawFromHighestCheckings(id, ui.getAmount("withdraw"));
    }

}
