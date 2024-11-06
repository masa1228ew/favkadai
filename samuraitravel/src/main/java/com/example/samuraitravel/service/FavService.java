package com.example.samuraitravel.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.example.samuraitravel.entity.Fav;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.FavForm;
import com.example.samuraitravel.repository.FavRepository;
import com.example.samuraitravel.repository.HouseRepository;

import jakarta.transaction.Transactional;

@Service
public class FavService {
	private final FavRepository favRepository;
	private final HouseRepository houseRepository;
	
	public FavService(FavRepository favRepository,HouseRepository houseRepository) {
		this.favRepository = favRepository;
		this.houseRepository = houseRepository;
	}
	@Transactional
	public void create(
			House house,User user,
//			@PathVariable(name = "houseId") Integer houseId,
//			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
//			House house, 
//			UserDetailsImpl userDetailsImpl,
			 FavForm favForm
			 ) {
		 favForm.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

//		    User user = userDetailsImpl.getUser();
//		    House house = houseRepository.getReferenceById(houseId);
		    
		   
	  //すでにいいねしていた場合、いいねを取り消す
	  if(favRepository.existsByUserAndHouse(user, house) == true) {
	      favRepository.deleteByUserAndHouse(user, house);
	  }else {  //いいねしていなかった場合、投稿へのいいねを登録する
		 Fav fav = new Fav();
//	    fav.setFavId(favId);
	    fav.setUser(user);
	    fav.setHouse(house);
	    LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	    fav.setCreatedAt(ldt);
	    fav.setUpdatedAt(ldt);
	    favRepository.save(fav);
	  }
	}
//	    return "redirect:/postmain?postdetail";
//	}
	public boolean isFav(House house, User user) {
				
		 return favRepository.existsByUserAndHouse(user, house);
		
		
	}
	

}
