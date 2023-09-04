package com.example.ggapplication.data;

import java.util.List;

public class SellData {
   private String imageCode;
   private List<String> imageUrlList;

   public String getImageCode() {
      return imageCode;
   }

   public List<String> getImageUrlList() {
      return imageUrlList;
   }

   public void setImageUrlList(List<String> imageUrlList) {
      this.imageUrlList = imageUrlList;
   }

   public void setImageCode(String imageCode) {
      this.imageCode = imageCode;
   }
}
