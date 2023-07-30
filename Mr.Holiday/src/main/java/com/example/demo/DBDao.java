package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface DBDao extends  Serializable {

    public List<DB> search(String genre, String author, String title);
}