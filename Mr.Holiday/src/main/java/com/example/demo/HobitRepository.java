package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobitRepository extends JpaRepository<HobitEntity, Integer> {
	public List<HobitEntity>findByGenreLikeOrMoodLike(String key,String key1);

}