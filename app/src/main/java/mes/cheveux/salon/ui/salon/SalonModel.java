package mes.cheveux.salon.ui.salon;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import mes.cheveux.salon.ui.salon.reviews.SalonReviewsModel;

public class SalonModel {

    @SerializedName("salon_id")
    @Expose
    private Integer salonId;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("average_rating")
    @Expose
    private Double average_rating;

    @SerializedName("review_count")
    @Expose
    private int review_count;

    @SerializedName("reviews")
    @Expose
    private List<SalonReviewsModel> reviews;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Double average_rating) {
        this.average_rating = average_rating;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public List<SalonReviewsModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<SalonReviewsModel> reviews) {
        this.reviews = reviews;
    }

    public Integer getSalonId() {
        return salonId;
    }

    public void setSalonId(Integer salonId) {
        this.salonId = salonId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public static final DiffUtil.ItemCallback<SalonModel> CALLBACK = new DiffUtil.ItemCallback<SalonModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SalonModel salon, @NonNull SalonModel salon1) {
            return salon.salonId == salon1.salonId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SalonModel salon, @NonNull SalonModel salon1) {
            return true;
        }
    };

}