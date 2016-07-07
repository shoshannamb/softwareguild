/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bankmanager.dao;

import com.sg.bankmanager.dto.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class BankManagerDAO {

    private Map<Integer, Client> bankClients;

    // seed with clients and bank accounts
    public BankManagerDAO() {
        List<Account> accounts1 = new ArrayList<>();
        Account a1 = new CheckingAccount(100101, 500);
        Account a2 = new CheckingAccount(100102, 27.50);
        Account a3 = new SavingsAccount(100103, 2000, LocalDate.parse("2012-03-15", DateTimeFormatter.ISO_DATE));
        Account a4 = new CDAccount(100104, 1000, LocalDate.parse("2012-03-15", DateTimeFormatter.ISO_DATE), LocalDate.parse("2022-03-15", DateTimeFormatter.ISO_DATE));
        accounts1.add(a1);
        accounts1.add(a2);
        accounts1.add(a3);
        accounts1.add(a4);
        Client c1 = new Client(1001, 1234, accounts1);

        a1 = new CheckingAccount(100201, 22);
        a2 = new SavingsAccount(100202, 5000, LocalDate.parse("2002-01-02", DateTimeFormatter.ISO_DATE));
        List<Account> accounts2 = new ArrayList<>();
        accounts2.add(a1);
        accounts2.add(a2);
        Client c2 = new Client(1002, 5678, accounts2);
        
        // shares a savings account with 1001
        a1 = new CheckingAccount(100301, 20000);
        a2 = new SavingsAccount(100302, 100000, LocalDate.parse("1992-04-12", DateTimeFormatter.ISO_DATE));
        a4 = new CDAccount(100303, 5000, LocalDate.parse("2002-01-03", DateTimeFormatter.ISO_DATE), LocalDate.parse("2012-01-03", DateTimeFormatter.ISO_DATE));
        List<Account> accounts3 = new ArrayList<>();
        accounts3.add(a1);
        accounts3.add(a2);
        accounts3.add(a3);
        accounts3.add(a4);
        Client c3 = new Client(1003, 32486, accounts3);

        bankClients = new HashMap<>();
        bankClients.put(c1.getId(), c1);
        bankClients.put(c2.getId(), c2);
        bankClients.put(c3.getId(), c3);
    }

//    public BankManagerDAO(String file) throws FileNotFoundException {
//        Scanner fRead = new Scanner(new BufferedReader(new FileReader(file)));
//        bankClients = new HashMap<>();
//        while (fRead.hasNextLine()) {
//            String[] info = fRead.nextLine().split("::");
//            CheckingAccount ca = new CheckingAccount(Double.parseDouble(info[2]));
//            SavingsAccount sa = new SavingsAccount(Double.parseDouble(info[3]), Integer.parseInt(info[4]), Double.parseDouble(info[5]), 2016);
//            sa.setLastYearAccessed(2016);
//            Client c = new Client(Integer.parseInt(info[0]), Integer.parseInt(info[1]), sa, ca);
//            c.setIsLocked(Boolean.parseBoolean(info[6]));
//            bankClients.put(c.getId(), c);
//        }
//        fRead.close();
//    }
    public boolean clientExists(int id) {
        return bankClients.containsKey(id);
    }

    public boolean checkPIN(int id, int pin) {
        return pin == bankClients.get(id).getPin();
    }

    public void lockClient(int id) {
        bankClients.get(id).setIsLocked(true);
    }

    public boolean checkLockedStatus(int id) {
        return bankClients.get(id).isIsLocked();
    }

    public List<Account> getClientAccounts(int id) {
        return bankClients.get(id).getAccounts();
    }

    public void deposit(int clientID, int accountID, double money) {
        bankClients.get(clientID).getAccount(accountID).deposit(money);
    }

    public boolean withdraw(int clientID, int accountID, double money) {
        return bankClients.get(clientID).getAccount(accountID).withdrawal(money);
    }

    // return CDAccounts separated into mature and immature
    public List<CDAccount>[] getCDAccounts(int id) {
        List<CDAccount>[] cdAccounts = new List[2];
        List<CDAccount> allCDs = bankClients.get(id).getAccounts().stream().filter(a -> a instanceof CDAccount).map(a -> (CDAccount) a).collect(Collectors.toList());
        cdAccounts[0] = allCDs.stream().filter(a -> ChronoUnit.DAYS.between(LocalDate.now(), a.getMaturityDate()) > 0).collect(Collectors.toList());
        cdAccounts[1] = allCDs.stream().filter(a -> ChronoUnit.DAYS.between(LocalDate.now(), a.getMaturityDate()) < 0).collect(Collectors.toList());

        return cdAccounts;
    }
    
    public List<Account> getOverdrawnAccounts(int id) {
        return bankClients.get(id).getAccounts().stream().filter(a -> a.getBalance() < 0).collect(Collectors.toList());
    }
    
    public double getTotalInAccounts(int id) {
        return bankClients.get(id).getAccounts().stream().mapToDouble(Account::getBalance).sum();
    }
    
    public boolean withDrawFromHighestCheckings(int id, double money) {
        Account highest = bankClients.get(id).getAccounts().stream().filter(a -> a instanceof CheckingAccount).map(a -> (CheckingAccount) a).max(Comparator.comparing(a -> a.getBalance())).get();
        return highest.withdrawal(money);
    }

//    public void save() throws IOException {
//        PrintWriter out = new PrintWriter(new FileWriter("bank.txt"));
//        for (Client c : bankClients.values()) {
//            out.println(c.getId() + "::" + c.getPin() + "::"
//                    + c.getChecking().getBalance() + "::"
//                    + c.getSavings().getBalance() + "::" + c.getSavings().getLastYearAccessed() + "::" + c.getSavings().getYearlyInterestRate() + "::"
//                    + c.isIsLocked());
//        }
//        out.flush();
//        out.close();
//    }
}
