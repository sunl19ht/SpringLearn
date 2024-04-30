package com.sunl19ht.spring6.tx.service;

import com.sunl19ht.spring6.tx.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Transactional
 * 1.加在类上，表示当前类所有方法都添加事务
 * 2.加在方法上，表示当前方法添加事务
 * 只读 只能查询查询操作
 * 超时时间 超过超时时间后，自动回滚
 * 回滚策略 设置哪些异常不回滚
 * 隔离级别 读问题
 * 传播行为 事务方法之间调用事务如何使用
 */
@Transactional(propagation = Propagation.REQUIRED) //添加事务
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public void buyBook(Integer bookId, Integer userId) {
        //图书id查询图书价格
        Integer price = bookDao.getBookPriceByBookId(bookId);
        //更新图书库存量-1
        bookDao.updateStock(bookId);
        //更新用户余额 - 图书价格
        bookDao.updateUserBalance(userId, price);
    }
}
