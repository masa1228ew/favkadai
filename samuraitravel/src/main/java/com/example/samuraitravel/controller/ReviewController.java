package com.example.samuraitravel.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
public class ReviewController{
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final HouseRepository houseRepository;
	
	public ReviewController(ReviewRepository reviewRepository,ReviewService reviewService,HouseRepository houseRepository) {
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
		this.houseRepository = houseRepository;
	}
	
//	 @GetMapping("/{id}/houses/review/edit")
//     public String reviewedit(@PathVariable(name="id") Integer id,Model model){
//    	 Review review= reviewRepository.getReferenceById(id);
//    	 ReviewEditForm reviewEditForm = new ReviewEditForm(review.getScore(),review.getContent());
    	 
//    	 model.addAttribute("reviewEditForm",reviewEditForm);
    	 
//    	 return "admin/houses/review/edit";
//   }
	
//	 @GetMapping("/{id}/houses/review/register")
//     public String register(Model model) {
//    	 model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
//    	 return "houses/review/register";
//     }
     
//     @PostMapping("/{id}/houses/review/create")
//     public String create(@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,BindingResult bindingResult,RedirectAttributes redirectAttributes ) {
//   	  if(bindingResult.hasErrors()) {
//   		  return "/{id}/houses/review/register";
//   	  }
//   	  reviewService.create(reviewRegisterForm);
//   	  redirectAttributes.addAttribute("successMessage","レビューを投稿しました。");
   	  
//   	  return "redirect:/houses/show";
//     }
//	@GetMapping
//	public String show(Model model) {
//		List<Review> newReview = reviewRepository.findTop10ByOrderByCreatedAtDesc(house);
//		model.addAttribute("newReview",newReview);
//		return "house/show";
//	}
	 @PostMapping("/houses/{houseId}/review/create")
	  public String create(@PathVariable(name = "houseId") Integer houseId,
             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
             @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
             BindingResult bindingResult,
             RedirectAttributes redirectAttributes,
             Model model)
	{    
		House house = houseRepository.getReferenceById(houseId);
		User user = userDetailsImpl.getUser();
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			model.addAttribute("house", house);
			
			return "review/register";
		}        
		reviewService.create(house, user, reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");    
		
		return "redirect:/houses/{houseId}";
	}
		
	@GetMapping("/houses/{houseId}/review/{reviewId}/edit")
     public String edit(      @PathVariable(name="houseId") Integer houseId,
             @PathVariable(name="reviewId") Integer reviewId, 
             Model model) {
        House house = houseRepository.getReferenceById(houseId);
         Review review = reviewRepository.getReferenceById(reviewId);
    	 ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(),review.getScore(),review.getContent());
    	
    	 model.addAttribute("house",house);
    	 model.addAttribute("review",review);
    	 model.addAttribute("reviewEditForm",reviewEditForm);
    	 
    	 
    	 return "review/edit";
   }
     
     @PostMapping("/houses/{houseId}/review/{reviewId}/delete")
     public String delete(@PathVariable(name = "houseId") Integer houseId,@PathVariable(name = "reviewId") Integer reviewId,RedirectAttributes redirectAttributes,Model model) {
    	 
    	 
    	 House house = houseRepository.getReferenceById(houseId);
//    	 Review review = reviewRepository.getReferenceById(reviewId);
    	 
//    	 model.addAttribute("review",review);
    	 reviewRepository.deleteById(reviewId);
    	 
    	 model.addAttribute("house", house);
    	 
    	  redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
    	  
    	  return "redirect:/houses/{houseId}";
     }
     
     @PostMapping("/houses/{houseId}/review/{reviewId}/update")
     public String update(@PathVariable(name = "houseId") Integer houseId,@PathVariable(name = "reviewId") Integer reviewId,
             @ModelAttribute @Validated ReviewEditForm reviewEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model) { 
    	 House house = houseRepository.getReferenceById(houseId);
    	 Review review = reviewRepository.getReferenceById(reviewId);
 		
         if (bindingResult.hasErrors()) {
        	 System.out.println(bindingResult.hasErrors());
 			model.addAttribute("house", house);
 			model.addAttribute("review", review);
             return "review/edit";
         }
         
         reviewService.update(reviewEditForm);
         redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");
         
         return "redirect:/houses/{houseId}";
     }    
     
//     @PostMapping("/review/create")
//     public String create(@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,BindingResult bindingResult,RedirectAttributes redirectAttributes ) {
//   	  if(bindingResult.hasErrors()) {
//   		  return "/review/register";
//   	  }
//   	  reviewService.create(reviewRegisterForm);
//   	  redirectAttributes.addAttribute("successMessage","レビューを投稿しました。");
   	  
//   	  return "redirect:/houses/show";
//     }
}