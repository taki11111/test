package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService2 {

    @Autowired
    private DBRepository2 DBRepository2;

    @Autowired
    private DBRepository DBRepository;


    //全件検索
    public List<DB> findAll(){
        return DBRepository2.findAll();
    }

    //該当のID見つける
    public Optional<DB> findById(long isbn) {
        return DBRepository2.findById(isbn);
    }

    //保存
    public DB save(DB bookData) {
        return DBRepository2.saveAndFlush(bookData);
    }

    //検索
    public List<DB> search(String genre, String author, String title){

        List<DB> result = new ArrayList<DB>();

        //すべてブランクだった場合は全件検索する
        if ("".equals(genre) && "".equals(author) && "".equals(title)){
            result = DBRepository2.findAll();
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
            result = DBRepository.search(genre, author, title);
        }
        return result;
    }
}
