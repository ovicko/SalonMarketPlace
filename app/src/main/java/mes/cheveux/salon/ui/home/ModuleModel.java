package mes.cheveux.salon.ui.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import mes.cheveux.salon.data.salon.SalonModel;

public class ModuleModel {

    @SerializedName("module_id")
    @Expose
    private Integer moduleId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("salons")
    @Expose
    private List<SalonModel> salons = null;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SalonModel> getSalons() {
        return salons;
    }

    public void setSalons(List<SalonModel> salons) {
        this.salons = salons;
    }

}