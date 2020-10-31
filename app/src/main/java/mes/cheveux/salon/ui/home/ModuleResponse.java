package mes.cheveux.salon.ui.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import mes.cheveux.salon.common.data.MetaModel;

public class ModuleResponse {

    @SerializedName("modules")
    @Expose
    private List<ModuleModel> modules = null;
    @SerializedName("_meta")
    @Expose
    private MetaModel meta;

    public List<ModuleModel> getModules() {
        return modules;
    }

    public void setModules(List<ModuleModel> modules) {
        this.modules = modules;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

}