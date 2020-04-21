package com.example.restservice;

import java.util.ArrayList;

public class Customer {
    private String name;
    private String pw;
    private ArrayList<BankAccount> accountsToAccess;

    public Customer(String name, String password){
        this.name = name;
        this.pw = password;
        accountsToAccess = new ArrayList<>();
    }

    public String addMoney(BankAccount bankAccount, int amount){
        String message;
        if(accountsToAccess.contains(bankAccount)){
            int newSaldo = bankAccount.depositMoney(amount);
            message = "deposit = successful, you have now $ "+newSaldo;
        }
        else
            message = "ERROR: you can't deposit money on an account that is not yours";

        return message;
    }

    public String removeMoney(BankAccount bankAccount, int amount){
        String message;
        if(accountsToAccess.contains(bankAccount)){
            int newSaldo = bankAccount.withdrawMoney(amount);
            message = "Withdraw = successful, you have now $ "+newSaldo;
        }
        else
            message = "ERROR: you can't remove money on an account that is not yours";

        return message;
    }

    public void createBankAccount(String accountName){
        BankAccount bankAccount = new BankAccount(accountName);
        accountsToAccess.add(bankAccount);
        }

    public String deleteBankAccount(BankAccount bankAccount){
        String message;
        if (accountsToAccess.contains(bankAccount)) {
            accountsToAccess.remove(bankAccount);
            message = "Deleted account " + bankAccount;
        }
        else
            message = "Cannot remove this account";
        return message;
    }


    public BankAccount findBankAccount(String accountName){
        BankAccount foudTheBackAccount = null;
        for (BankAccount b: accountsToAccess) {
            if (b.getAccountName().equals(accountName)){
                foudTheBackAccount = b;
            }
        }
        return foudTheBackAccount;
    }


    public String getName(){
        return name;
    }

    public ArrayList<BankAccount> getAccountsToAccess(){
        return accountsToAccess;
    }

}
