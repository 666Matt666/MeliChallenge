package com.example.tc.dto;

import java.util.List;

public class ItemDTO {
    private String id;
    private String title;
    private String subtitle;
    private String description;
    private double price;
    private Double discountedPrice;
    private String currency;
    private int availableQuantity;
    private String condition;
    private String sellerName;
    private double sellerScore;
    private List<String> images;
    private double averageReview;
    private int totalReviews;
    private List<String> highlights;

    public ItemDTO() {}

    public ItemDTO(String id, String title, String subtitle, String description,
                   double price, Double discountedPrice, String currency, int availableQuantity,
                   String condition, String sellerName, double sellerScore,
                   List<String> images, double averageReview, int totalReviews,
                   List<String> highlights) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.currency = currency;
        this.availableQuantity = availableQuantity;
        this.condition = condition;
        this.sellerName = sellerName;
        this.sellerScore = sellerScore;
        this.images = images;
        this.averageReview = averageReview;
        this.totalReviews = totalReviews;
        this.highlights = highlights;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(Double discountedPrice) { this.discountedPrice = discountedPrice; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public double getSellerScore() { return sellerScore; }
    public void setSellerScore(double sellerScore) { this.sellerScore = sellerScore; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public double getAverageReview() { return averageReview; }
    public void setAverageReview(double averageReview) { this.averageReview = averageReview; }

    public int getTotalReviews() { return totalReviews; }
    public void setTotalReviews(int totalReviews) { this.totalReviews = totalReviews; }

    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }
}