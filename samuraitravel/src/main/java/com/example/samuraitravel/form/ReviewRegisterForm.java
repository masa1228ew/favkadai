package com.example.samuraitravel.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
// @NotNull
// private Integer HouseId;
 @NotNull(message="評価を入力してください")
 @Min(value = 1, message = "評価を入力してください")
 private Integer score;
 
 @NotBlank(message="レビューを書き込んでください")
 private String content;
}