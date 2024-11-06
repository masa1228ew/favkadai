package com.example.samuraitravel.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
  @NotNull
  private Integer id;
  
//  @NotNull
//  private Integer user;
  
//  @NotNull
//  private Integer house;
  
  @NotNull
  @Min(value = 1)
  private Integer score;
  
  @NotBlank
  private String content;
}
