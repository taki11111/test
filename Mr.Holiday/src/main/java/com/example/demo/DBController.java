package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class DBController {

    @Autowired
    private DBService2 DBService2;

    @PersistenceContext
    EntityManager entityManager;

    //一覧表示処理
    @GetMapping
    public String DB2(Model model,@ModelAttribute("formModel") DB bookdata) {

        model.addAttribute("msg", "在庫管理");
        model.addAttribute("msg2", "検索条件を入力してください");
        List<DB> books = DBService2.findAll();
        model.addAttribute("books", books);

        return "DB2";
    }

    //検索結果の受け取り処理
    //@ModelAttributeでformからformModelを受け取り、
    //その型(BookData)と変数(bookdata)を指定する
    @PostMapping
    public String select(@ModelAttribute("formModel") DB bookdata, Model model) {

        model.addAttribute("msg", "検索結果");
        //bookdataのゲッターで各値を取得する
        List<DB> result = DBService2.search(bookdata.getGenre(),bookdata.getAuthor(), bookdata.getTitle());
        model.addAttribute("books", result);

        return "DB2";
    }

    //詳細画面処理
    //@PathVariableでURLから受け取った値を取得する
    @GetMapping("detail/{isbn}")
    public String detail(@PathVariable long isbn, Model model) {

        model.addAttribute("msg", "参照画面");
        Optional<DB> data = DBService2.findById(isbn);
        //Optionalを使用する際、値はget()で取得する
        model.addAttribute("form", data.get());

        return "detail";
    }


    //初期化処理
    @PostConstruct
    public void init() {

        DB d1 = new DB();
        d1.setAuthor("夏目漱石");
        d1.setTitle("こころ");
        d1.setGenre("文学");
        d1.setIsbn(11111);
        d1.setStock(100);
        d1.setStatus(false);
        DBService2.save(d1);

        DB d2 = new DB();
        d2.setAuthor("大野次郎");
        d2.setTitle("Spring入門");
        d2.setGenre("技術");
        d2.setIsbn(22222);
        d2.setStock(1);
        d2.setStatus(false);
        DBService2.save(d2);

        DB d3 = new DB();
        d3.setAuthor("田中太郎");
        d3.setTitle("ネットワークのしくみ");
        d3.setGenre("技術");
        d3.setIsbn(33333);
        d3.setStock(20);
        d3.setStatus(false);
        DBService2.save(d3);

        DB d4 = new DB();
        d4.setAuthor("吉川量");
        d4.setTitle("泥の銃弾");
        d4.setGenre("ミステリー");
        d4.setIsbn(44444);
        d4.setStock(99);
        d4.setStatus(false);
        DBService2.save(d4);

        DB d5 = new DB();
        d5.setAuthor("夏目漱石");
        d5.setTitle("草枕");
        d5.setGenre("文学");
        d5.setIsbn(55555);
        d5.setStock(40);
        d5.setStatus(false);
        DBService2.save(d5);

        DB d6 = new DB();
        d6.setAuthor("テスト敏郎");
        d6.setTitle("連続殺人事件簿");
        d6.setGenre("ミステリー");
        d6.setIsbn(66666);
        d6.setStock(40);
        d6.setStatus(false);
        DBService2.save(d6);

        DB d7 = new DB();
        d7.setAuthor("蛇場太郎");
        d7.setTitle("Java入門");
        d7.setGenre("技術");
        d7.setIsbn(77777);
        d7.setStock(40);
        d7.setStatus(false);
        DBService2.save(d7);
    }
}