package com.tobeng.test.dao.book;


import com.tobeng.test.dao.BaseDao;
import com.tobeng.test.entity.book.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends BaseDao<Book> {
}