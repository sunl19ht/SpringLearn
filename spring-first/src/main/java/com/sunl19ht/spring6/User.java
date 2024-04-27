package com.sunl19ht.spring6;

public class User {
    public User() {
        System.out.println("User无参构造执行...");
    }
    public void add() {
        System.out.println("add...");
    }

    public static void main(String[] args) {
        User user = new User();
        user.add();
    }
}
