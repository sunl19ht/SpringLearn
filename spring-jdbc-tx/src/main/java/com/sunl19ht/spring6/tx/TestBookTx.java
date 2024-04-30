package com.sunl19ht.spring6.tx;

import com.sunl19ht.spring6.tx.controller.BookController;
import com.sunl19ht.spring6.tx.service.BookService;
import com.sunl19ht.spring6.tx.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:beans.xml")
public class TestBookTx {
    @Autowired
    private BookController bookController;

    @Test
    public void testBuyBook() {
//        bookController.buyBook(1, 1);
        Integer[] bookIds = {1, 2};
        bookController.checkOut(bookIds, 1);
    }
}
