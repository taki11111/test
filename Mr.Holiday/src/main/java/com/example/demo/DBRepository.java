package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DBRepository implements DBDao {

    //Entityを利用するために必要な機能を提供する
    @Autowired
    private EntityManager entityManager;

    public DBRepository() {
        super();
    }

    public DBRepository(EntityManager manager) {
        this();
        entityManager = manager;
    }

    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<DB> search(String genre, String author, String title) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b From DB b WHERE ");

        boolean genreFlg  = false;
        boolean authorFlg = false;
        boolean titleFlg  = false;
        boolean andFlg    = false;

        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(genre)) {
            sql.append("b.genre LIKE :genre");
            genreFlg = true;
            andFlg   = true;
        }

        //authorがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(author)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.author LIKE :author");
            authorFlg = true;
            andFlg    = true;
        }

        //titleがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(title)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.title LIKE :title");
            titleFlg = true;
            andFlg   = true;
        }

        /*
        QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
        entityManagerのcreateQueryメソッドを使用する
        sql変数を引数に渡す
        */
        Query query = entityManager.createQuery(sql.toString());

        //上記のif文でtrueになった場合は、各変数に値をセットする
        //今回、あいまい検索したいのでlike句を使用する
        if (genreFlg) query.setParameter("genre", "%" + genre + "%");
        if (authorFlg) query.setParameter("author", "%" + author + "%");
        if (titleFlg) query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }
}