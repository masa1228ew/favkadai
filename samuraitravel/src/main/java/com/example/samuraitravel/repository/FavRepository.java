package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Fav;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

public interface FavRepository extends JpaRepository<Fav, Integer> {
 public Fav findByHouseAndUser(House house, User user);

public Fav getReferenceByFavId(Integer favId);

public boolean existsByUserAndFavId(User user, int fav);

public void deleteByUserAndFavId(User user, int fav);

public boolean existsByUserAndHouse(User user, House house);

public void deleteByUserAndHouse(User user, House house);

public Page<Fav> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

//public Page<Fav> findByHouseOrderByCreatedAtDesc(House house,Pageable pageable);
}
