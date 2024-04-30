package com.sunl19ht.spring6.tx.service;

public interface CheckOutService {
    void checkOut(Integer[] bookIds, Integer userId);
}
