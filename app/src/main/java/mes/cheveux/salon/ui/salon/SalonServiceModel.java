package mes.cheveux.salon.ui.salon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalonServiceModel {
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("service_group")
    @Expose
    private Integer serviceGroup;
    @SerializedName("salon_id")
    @Expose
    private Integer salonId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("formated_price")
    @Expose
    private String formatedPrice;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(Integer serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public Integer getSalonId() {
        return salonId;
    }

    public void setSalonId(Integer salonId) {
        this.salonId = salonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFormatedPrice() {
        return formatedPrice;
    }

    public void setFormatedPrice(String formatedPrice) {
        this.formatedPrice = formatedPrice;
    }
}
