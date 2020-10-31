package mes.cheveux.salon.common.data;

public class SalonSearchModel {
    private Double latitude;
    private Double longitude;
    private int distance;


    public SalonSearchModel(Double latitude,
                            Double longitude, int distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
