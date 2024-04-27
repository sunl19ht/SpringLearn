package com.sunl19ht.spring6.iocxml.di;

public class Book {
    private String bName; //书名
    private String author; //作者
    private String others;

    public Book() {
        System.out.println("无参构造执行...");
    }

    //有参构造器
    public Book(String bName, String author) {
        System.out.println("有参构造执行...");
        this.bName = bName;
        this.author = author;
    }

    public String getbName() {
        return bName;
    }

    public String getAuthor() {
        return author;
    }

    public String getOthers() {
        return others;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bName='" + bName + '\'' +
                ", author='" + author + '\'' +
                ", others='" + others + '\'' +
                '}';
    }

    public static void main(String[] args) {
        //set方法注入
        Book book = new Book();
        book.setbName("java");
        book.setAuthor("尚硅谷");

        //构造器注入
        Book book1 = new Book("c++", "尚硅谷");
    }
}
