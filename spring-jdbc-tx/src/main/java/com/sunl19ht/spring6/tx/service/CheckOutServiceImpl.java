package com.sunl19ht.spring6.tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckOutServiceImpl implements CheckOutService{
    @Autowired
    private BookService bookService;

    //买多本书
    @Override
    public void checkOut(Integer[] bookIds, Integer userId) {
        for (Integer bookId : bookIds) {
            bookService.buyBook(bookId, userId);
        }
    }
}
