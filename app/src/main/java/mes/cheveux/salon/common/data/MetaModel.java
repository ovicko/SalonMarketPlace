package mes.cheveux.salon.common.data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaModel {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("perPage")
    @Expose
    private Integer perPage;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

}