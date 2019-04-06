package com.bank.application.util;

import com.bank.application.cache.AccountCache;
import com.bank.application.model.Account;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountValidation {
    private final static Logger logger = Logger.getLogger(AccountValidation.class.getName());

    public static Account getAccount(String username) {
        String accountNumber;
        double amount;
        String accountType;
        Scanner keyboard = new Scanner(System.in);
        do {
            System.out.print("Insert Account Number: ");
            accountNumber = keyboard.next();
        } while (!checkAccountNumber(accountNumber));

        do {
            System.out.print("Insert Amount: ");
            amount = getAmount();
        } while (!checkAmount(amount));

        do {
            System.out.print("Insert Account Type: ");
            accountType = keyboard.next();
            accountType = accountType.toUpperCase().trim();
        } while (!checkAccountType(accountType));

        return new Account(accountNumber, username, new BigDecimal(amount), Currency.valueOf(accountType));
    }

    private static double getAmount() {
        Scanner keyboard = new Scanner(System.in);
        double amount = -1;
        System.out.print("Insert Amount: ");
        try {
            amount = keyboard.nextDouble();
        } catch (InputMismatchException e) {
            logger.finer(e.getMessage());
        }
        return amount;
    }

    private static boolean checkAccountType(String accountType) {
        String type = accountType.toUpperCase().trim();
        if (type.equals("RON") || type.equals("EUR")) {
            return true;
        }
        logger.warning("Invalid type.");
        return false;
    }

    private static boolean checkAmount(double amount) {
        if (amount < 0) {
            logger.warning("Invalid amount.");
            return false;
        }
        return true;
    }

    private static boolean checkAccountNumber(String accountNumber) {
        for (Account account : AccountCache.getAccountsFromFile()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                logger.warning("Account number already exists!\nPlease try again.. ");
                return false;
            }
        }
        if (!accountNumber.toUpperCase().startsWith("RO")) {
            logger.warning("Invalid account number: " + accountNumber + ". It should start with 'RO'");
            return false;
        } else if (accountNumber.length() != 24) {
            logger.warning("Invalid account number: " + accountNumber + ". Account number length should be 24.");
            return false;
        }
        return true;
    }
}
