package our_algorithm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone(0,10,25,50,5660, 0.9, 0.8,100.5));
        drones.add(new Drone(1,60,45,20,3680, 0.8, 0.75,200.5));

        MyAlgorithm algo = new MyAlgorithm(drones);

        List<Cluster> clusters = algo.formCluster();
        for(Cluster cluster: clusters){
            cluster.printDrones();
        }
    }
}
