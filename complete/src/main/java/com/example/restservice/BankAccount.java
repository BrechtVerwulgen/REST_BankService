package com.example.restservice;

import java.util.ArrayList;


public class BankAccount {

    public String accountName;
    public int saldo;

    public BankAccount(String accountName){
        this.accountName = accountName;
        this.saldo = 0;

    }

    public int depositMoney(int amount){
        return saldo += amount;
    }

    public int withdrawMoney(int amount){
        return saldo -= amount;
    }

    public int getSaldo(){
        return saldo;
    }

    public String getAccountName() {
        return accountName;
    }
}
