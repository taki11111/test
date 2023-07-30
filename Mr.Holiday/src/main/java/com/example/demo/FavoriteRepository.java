package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
	public List<FavoriteEntity>findByGenreLikeOrMoodLike(String key,String key1);
}
