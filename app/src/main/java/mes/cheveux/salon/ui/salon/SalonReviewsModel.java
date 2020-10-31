package mes.cheveux.salon.ui.salon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalonReviewsModel {

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("date_added")
    @Expose
    private String dateAdded;

    @SerializedName("customer_id")
    @Expose
    private int customerId;

    @SerializedName("review_id")
    @Expose
    private int reviewId;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("salon_id")
    @Expose
    private int salonId;

    @SerializedName("average_rating")
    @Expose
    private  Double average_rating;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public Double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Double average_rating) {
        this.average_rating = average_rating;
    }
}
