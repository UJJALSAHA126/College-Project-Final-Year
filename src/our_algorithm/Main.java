package our_algorithm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Drone> drones = new ArrayList<>();

        drones.add(new Drone(1, 0.70, 25.50, 95.5, 3600, 0.98, 98, 0.74));
        drones.add(new Drone(2, 0.60, 21.50, 91.5, 3200, 0.82, 90, 0.86));
        drones.add(new Drone(3, 0.50, 17.75, 87.75, 2900, 0.46, 80, 1.1));
        drones.add(new Drone(4, 0.40, 14.50, 83.5, 2575, 0.36, 65, 1.9));
        drones.add(new Drone(5, 0.30, 10.00, 80, 2032, 0.35, 50, 2.73));
        drones.add(new Drone(6, 0.54, 10.50, 94.25, 2565, 0.92, 88, 0.78));
        drones.add(new Drone(7, 0.36, 10.96, 90.85, 3600, 0.47, 65, 1.58));
        drones.add(new Drone(8, 0.65, 16.52, 81.26, 2595, 0.9, 95, 0.75));
        drones.add(new Drone(9, 0.36, 20.36, 80.24, 2052, 0.42, 65, 1.02));
        drones.add(new Drone(10, 0.56, 11.25, 84.57, 2636, 0.44, 58, 1.25));
        drones.add(new Drone(11, 0.60, 17.6, 83.7, 3736, 0.49, 60, 0.9));
        drones.add(new Drone(12, 0.70, 11.65, 87.65, 2900, 0.89, 92, 1.65));
        drones.add(new Drone(13, 0.46, 11.22, 81.00, 2912, 0.46, 74, 1.3));
        drones.add(new Drone(14, 0.50, 20.36, 83.00, 3000, 0.46, 75, 2));
        drones.add(new Drone(15, 0.65, 17.10, 94.03, 2130, 0.43, 73, 1.2));
        drones.add(new Drone(16, 0.85, 24.25, 83.90, 2224, 0.43, 86, 1.88));
        drones.add(new Drone(17, 0.68, 23.25, 93.78, 3100, 0.46, 65, 1.36));
        drones.add(new Drone(18, 0.44, 12.4, 82.00, 2431, 0.43, 70, 1.8));
        drones.add(new Drone(19, 0.70, 13.39, 90.36, 3535, 0.47, 55, 1.86));
        drones.add(new Drone(20, 0.30, 10.3, 86.26, 3584, 0.48, 69, 1.25));

//        System.out.println("------------------------");
//        for (Drone d : drones)
//            System.out.println(d);
//
//        System.out.println("------------------------");

        MyAlgorithm algo = new MyAlgorithm(drones);

        List<Cluster> clusters = algo.formCluster();
        for (Cluster cluster : clusters) {
            cluster.printDrones();
            System.out.println();

            List<Drone> leaders = algo.selectClusterHead(cluster);
            System.out.println("For Cluster ID " + cluster.clusterId + " Leader Drones Are : ");
            for (Drone d : leaders) System.out.println(d);

            System.out.println("\n");
        }

//        System.out.println("------------------------");
//        for (Drone d : drones)
//            System.out.println(d);
    }
}
