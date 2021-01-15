/*
 * Copyright (c) 2019. AMWOLLO VICTOR <https://ovicko.com>
 */

package mes.cheveux.salon.common.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import mes.cheveux.salon.data.salon.SalonModel;

public class NearbySalonsResponse {

    @SerializedName("salons")
    @Expose
    private List<SalonModel> salons;

    @SerializedName("_meta")
    @Expose
    private MetaModel meta;

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public List<SalonModel> getSalons() {
        return salons;
    }

    public void setSalons(List<SalonModel> salons) {
        this.salons = salons;
    }
}
