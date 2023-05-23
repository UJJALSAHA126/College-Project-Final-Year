package our_algorithm;

import java.util.Objects;

public class Drone {
    public final int id;
    private int clusterId;
    public double alt, lat, lon, energy, trustValue, PDR, transmissionDelay;

    public Drone(int id, double alt, double lat, double lon, double energy, double trustValue, double PDR, double transmissionDelay) {
        this.id = id;
        this.alt = alt;
        this.lat = lat;
        this.lon = lon;
        this.energy = energy;
        this.trustValue = trustValue;
        this.PDR = PDR;
        this.transmissionDelay = transmissionDelay;

        this.clusterId = -1;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id = " + id +
                ", clusterId = " + clusterId +
                ", alt = " + alt +
                ", lat = " + lat +
                ", lon = " + lon +
                ", energy = " + energy +
                ", trustValue = " + trustValue +
                ", PDR = " + PDR +
                ", transmissionDelay = " + transmissionDelay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drone drone = (Drone) o;
        return id == drone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
