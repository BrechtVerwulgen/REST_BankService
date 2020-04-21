package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@RestController
public class BankController {
    //private ArrayList<BankAccount> allBankAccounts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    BankController() {
        addCustomer("brecht", "pw");
        addCustomer("stef", "pw");

        for (Customer c : customers) {
            c.createBankAccount("r1");
        }
    }

    private void addCustomer(String customerName, String customerPassword) {
        Customer customer = new Customer(customerName, customerPassword);
        customers.add(customer);
    }

    private Customer findCustomer(String customerName) {
        Customer customerFound = null;
        boolean searching = true;
        for (Customer c : customers) {
            if (searching == true) {
                if (c.getName().equals(customerName)) {
                    customerFound = c;
                    searching = false;
                }

            }
        }
        return customerFound;
    }

    @GetMapping("/addClient")
    public String addClient(@RequestParam(value = "customerName", defaultValue = "null") String customerName, @RequestParam(value = "pw", defaultValue = "null") String pw) {
        if (customerName != "null" && pw != "null") {
            addCustomer(customerName, pw);
            return "added client: " + customerName;
        } else
            return "Mislukt";
    }

    @GetMapping("/account")
    public ArrayList<BankAccount> getCustomer(@RequestParam(value = "customerName") String customerName) {
        Customer currCustomer = findCustomer(customerName);
        if (currCustomer != null) {
            return currCustomer.getAccountsToAccess();
        } else
            return null;
    }

    @GetMapping("/deposit")
    public String deposit(@RequestParam(value = "customerName", defaultValue = "null") String customerName, @RequestParam(value = "account", defaultValue = "null") String bankAccount, @RequestParam(value = "amount", defaultValue = "0") String amount) {
        Customer currCustomer = findCustomer(customerName);
        if (currCustomer != null) {
            BankAccount currBankAccount = currCustomer.findBankAccount(bankAccount);
            return currCustomer.addMoney(currBankAccount, Integer.parseInt(amount));
        } else
            return null;
    }

    @GetMapping("/withdraw")
    public String withdraw(@RequestParam(value = "customerName", defaultValue = "null") String customerName, @RequestParam(value = "account", defaultValue = "null") String bankAccount, @RequestParam(value = "amount", defaultValue = "0") String amount) {
        Customer currCustomer = findCustomer(customerName);
        if (currCustomer != null) {
            BankAccount currBankAccount = currCustomer.findBankAccount(bankAccount);
            return currCustomer.addMoney(currBankAccount, Integer.parseInt(amount));
        } else
            return null;
    }

    @GetMapping("addAccount")
    public RedirectView addAccount(@RequestParam(value = "customerName", defaultValue = "null") String customerName, @RequestParam(value = "accountName", defaultValue = "null") String accountName) {
        Customer currCustomer = findCustomer(customerName);
        if (currCustomer != null) {
            currCustomer.createBankAccount(accountName);
            return new RedirectView("/customers");
        } else
            return new RedirectView("/error/69");
    }

    @GetMapping("removeAccount")
    public RedirectView removeAccount(@RequestParam(value = "customerName", defaultValue = "null") String customerName, @RequestParam(value = "accountName", defaultValue = "null") String accountName) {
        Customer currCustomer = findCustomer(customerName);
        if (currCustomer != null) {
            BankAccount currBankAccount = currCustomer.findBankAccount(accountName);
            currCustomer.deleteBankAccount(currBankAccount);
            return new RedirectView("/customers");
        } else
            return new RedirectView("/error/69");
    }

    @GetMapping("removeClient")
    public RedirectView removeClient(@RequestParam(value = "customerName") String customerName){
        Customer currCustomer = findCustomer(customerName);
        if (currCustomer != null) {
            customers.remove(currCustomer);
            return new RedirectView("/customers");
        } else
            return new RedirectView("/error/420");
    }

    @GetMapping("/test")
    public String testMapping(){
        return "gelukt";
    }

    @GetMapping("/customers")
    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    @GetMapping("/error/69")
    public String error69(){
        return "This customer doesn't exist! Try adding the customer first and then creating an account";
    }

    @GetMapping("/error/420")
    public String error420(){
        return "Trying to delete a customer that doesn't exist is a bit weird... try again";
    }
}
