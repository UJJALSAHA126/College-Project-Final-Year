package our_algorithm;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    public List<Drone> drones;
    public final int clusterId;
    private Drone leader, subLeader;

    public Cluster(int clusterId) {
        this.drones = new ArrayList<>();
        this.clusterId = clusterId;

        this.leader = this.subLeader = null;
    }

    public boolean addDrone(Drone drone) {
        return drones.add(drone);
    }

    public boolean removeDrone(Drone drone) {
        return drones.remove(drone);
    }

    public Drone getLeader() {
        return leader;
    }

    public void setLeader(Drone leader) {
        this.leader = leader;
    }

    public Drone getSubLeader() {
        return subLeader;
    }

    public void setSubLeader(Drone subLeader) {
        this.subLeader = subLeader;
    }

    public int size() {
        return drones.size();
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "clusterId = " + clusterId +
                ", Number Of Drones = " + drones.size() +
                '}';
    }

    public void printDrones() {
        System.out.println("Cluster " + clusterId + " Contains");
        for (Drone drone : drones) {
            System.out.println(drone);
        }
    }
}
