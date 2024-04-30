package com.sunl19ht.spring6.tx.controller;

import com.sunl19ht.spring6.tx.service.BookService;
import com.sunl19ht.spring6.tx.service.BookServiceImpl;
import com.sunl19ht.spring6.tx.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {
    @Autowired
    private BookService service;

//    public void buyBook(Integer bookId, Integer userId) {
//        service.buyBook(bookId, userId);
//    }

    @Autowired
    private CheckOutService checkOutService;
    public void checkOut(Integer[] bookIds, Integer userId) {
        checkOutService.checkOut(bookIds, userId);
    }
}
