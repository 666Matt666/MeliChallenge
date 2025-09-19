package com.example.tc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String id;
    private String title;
    private String subtitle;
    private String description;
    private Double price;
    private Double original_price;
    private String currency;
    private String condition;
    private int available_quantity;
    private int sold_quantity;
    private Seller seller;
    private List<Picture> pictures;
    private List<String> images;
    private List<Attribute> attributes;
    private Shipping shipping;
    private List<Map<String, Object>> payment_methods;
    private Review reviews;
    private Map<String, String> warranty;
    private List<String> categories;
    private List<String> tags;
    private List<String> highlights;
    private String created_at;
    private Double discountedPrice;

    // Getters and Setters for all fields

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getOriginal_price() { return original_price; }
    public void setOriginal_price(Double original_price) { this.original_price = original_price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public int getAvailable_quantity() { return available_quantity; }
    public void setAvailable_quantity(int available_quantity) { this.available_quantity = available_quantity; }

    public int getSold_quantity() { return sold_quantity; }
    public void setSold_quantity(int sold_quantity) { this.sold_quantity = sold_quantity; }

    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }

    public List<Picture> getPictures() { return pictures; }
    public void setPictures(List<Picture> pictures) { this.pictures = pictures; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<Attribute> getAttributes() { return attributes; }
    public void setAttributes(List<Attribute> attributes) { this.attributes = attributes; }

    public Shipping getShipping() { return shipping; }
    public void setShipping(Shipping shipping) { this.shipping = shipping; }

    public List<Map<String, Object>> getPayment_methods() { return payment_methods; }
    public void setPayment_methods(List<Map<String, Object>> payment_methods) { this.payment_methods = payment_methods; }

    public Review getReviews() { return reviews; }
    public void setReviews(Review reviews) { this.reviews = reviews; }

    public Map<String, String> getWarranty() { return warranty; }
    public void setWarranty(Map<String, String> warranty) { this.warranty = warranty; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public Double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(Double discountedPrice) { this.discountedPrice = discountedPrice; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Seller {
        private String id;
        private String name;
        private Rating rating;
        private Map<String, String> location;
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Rating getRating() { return rating; }
        public void setRating(Rating rating) { this.rating = rating; }
        public Map<String, String> getLocation() { return location; }
        public void setLocation(Map<String, String> location) { this.location = location; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rating {
        private String level;
        private double score;
        private int total_ratings;

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
        public double getScore() { return score; }
        public void setScore(double score) { this.score = score; }
        public int getTotal_ratings() { return total_ratings; }
        public void setTotal_ratings(int total_ratings) { this.total_ratings = total_ratings; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Picture {
        private String id;
        private String url;
        private String secure_url;
        private String size;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getSecure_url() { return secure_url; }
        public void setSecure_url(String secure_url) { this.secure_url = secure_url; }
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Attribute {
        private String id;
        private String name;
        private String value_name;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getValue_name() { return value_name; }
        public void setValue_name(String value_name) { this.value_name = value_name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Shipping {
        private boolean free_shipping;
        private boolean store_pickup;
        private String logistic_type;
        private int estimated_delivery_days;
        private Map<String, Integer> dimensions;
        private double weight_kg;
        private Double shipping_cost;

        public boolean isFree_shipping() { return free_shipping; }
        public void setFree_shipping(boolean free_shipping) { this.free_shipping = free_shipping; }
        public boolean isStore_pickup() { return store_pickup; }
        public void setStore_pickup(boolean store_pickup) { this.store_pickup = store_pickup; }
        public String getLogistic_type() { return logistic_type; }
        public void setLogistic_type(String logistic_type) { this.logistic_type = logistic_type; }
        public int getEstimated_delivery_days() { return estimated_delivery_days; }
        public void setEstimated_delivery_days(int estimated_delivery_days) { this.estimated_delivery_days = estimated_delivery_days; }
        public Map<String, Integer> getDimensions() { return dimensions; }
        public void setDimensions(Map<String, Integer> dimensions) { this.dimensions = dimensions; }
        public double getWeight_kg() { return weight_kg; }
        public void setWeight_kg(double weight_kg) { this.weight_kg = weight_kg; }
        public Double getShipping_cost() { return shipping_cost; }
        public void setShipping_cost(Double shipping_cost) { this.shipping_cost = shipping_cost; }
    }
}