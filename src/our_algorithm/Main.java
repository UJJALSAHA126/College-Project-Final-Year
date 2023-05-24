package our_algorithm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Drone> drones = new ArrayList<>();

        drones.add(new Drone(0, 10, 25, 50, 5660, 0.9, 0.8, 100.5));
        drones.add(new Drone(1, 60, 45, 20, 3680, 0.8, 0.75, 200.5));
        drones.add(new Drone(3, 10, 25, 50, 5660, 0.9, 0.8, 100.5));
        drones.add(new Drone(4, 60, 45, 20, 3680, 0.8, 0.75, 200.5));
        drones.add(new Drone(5, 10, 25, 50, 5660, 0.9, 0.8, 100.5));
        drones.add(new Drone(6, 60, 45, 20, 3680, 0.8, 0.75, 200.5));
        drones.add(new Drone(7, 10, 25, 50, 5660, 0.9, 0.8, 100.5));
        drones.add(new Drone(8, 60, 45, 20, 3680, 0.8, 0.75, 200.5));

        MyAlgorithm algo = new MyAlgorithm(drones);

        List<Cluster> clusters = algo.formCluster();
        for (Cluster cluster : clusters) {
            List<Drone> leaders = algo.selectClusterHead(cluster);
            System.out.println("For Cluster ID " + cluster.clusterId);
            for(Drone d: leaders) System.out.println(d);
            System.out.println();
        }
    }
}
