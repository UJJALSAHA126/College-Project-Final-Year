import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

class Drone {
    public int id;
    public double lat, lon, alt, energy, velocity;

    public Drone(int id, double lat, double lon, double alt, double energy) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.energy = energy;
    }

    public Drone() {
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id = " + id +
                ", lat = " + lat +
                ", lon = " + lon +
                ", alt = " + alt +
                ", energy = " + energy +
                '}';
    }
}

class Cluster {
    private final List<Drone> drones;

    public Cluster() {
        drones = new ArrayList<>();
    }

    public void addDrone(Drone drone) {
        drones.add(drone);
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void sortDronesByEnergy() {
        drones.sort(Comparator.comparingDouble(d -> d.energy));
    }
}

public class BOLDAlgorithm {
    public static List<Cluster> clusterDrones(List<Drone> drones) {
        List<Cluster> clusters = new ArrayList<>();
        int N = drones.size();

        for (int i = 0; i < N / 4; i++) {
            Cluster cluster = new Cluster();

            for (int j = i * 4; j < (i + 1) * 4; j++) {
                if (j >= drones.size()) {
                    break;
                }
                cluster.addDrone(drones.get(j));
            }

            cluster.sortDronesByEnergy();

            if (!cluster.getDrones().isEmpty()) {
                clusters.add(cluster);
            }
        }

        for (int i = N / 4; i < N / 2; i++) {
            Cluster cluster = new Cluster();
            for (int j = i * 4; j < (i + 1) * 4; j++) {
                if (j >= drones.size()) {
                    break;
                }
                cluster.addDrone(drones.get(j));
            }
            cluster.sortDronesByEnergy();

            if (!cluster.getDrones().isEmpty()) {
                clusters.add(cluster);
            }
        }

        for (int i = N / 2; i < N - N / 4; i++) {
            Cluster cluster = new Cluster();
            for (int j = i * 4; j < (i + 1) * 4; j++) {
                if (j >= drones.size()) {
                    break;
                }
                cluster.addDrone(drones.get(j));
            }
            cluster.sortDronesByEnergy();
            if (!cluster.getDrones().isEmpty()) {
                clusters.add(cluster);
            }
        }

        return clusters;
    }

    public Drone runBOLDAlgorithm(List<Drone> drones, int iterations) {
        drones.sort(Comparator.comparingDouble(d -> d.energy));

        int N = drones.size();

        for (int i = 0; i < N / 4; i++) {
            List<Drone> arr1 = drones.subList(i * 4, (i + 1) * 4);
            arr1.sort(Comparator.comparingDouble(d -> d.energy));
        }

        for (int i = N / 4; i < N / 2; i++) {
            int start = i * 4, end = (1 + 1) * 4;
            if (start >= drones.size() || end > drones.size()) continue;

            List<Drone> arr2 = drones.subList(start, end);
            arr2.sort(Comparator.comparingDouble(d -> d.energy));
        }

        for (int i = N / 2; i < N - N / 4; i++) {
            int start = i * 4, end = (1 + 1) * 4;
            if (start >= drones.size() || end > drones.size()) continue;

            List<Drone> arr3 = drones.subList(i * 4, end);
            arr3.sort(Comparator.comparingDouble(d -> d.lat));
        }

        for (int i = N - N / 4; i < N; i++) {
            int start = i * 4, end = (1 + 1) * 4;
            if (start >= drones.size() || end > drones.size()) continue;

            List<Drone> arr4 = drones.subList(i * 4, end);
            arr4.sort((drone1, drone2) -> Double.compare(drone2.lat, drone1.lat));
        }

        double gbest = Double.NEGATIVE_INFINITY;
        Drone leader = null;

        for (int it = 0; it < iterations; it++) {
            for (int i = 0; i < N; i++) {
                List<Double> distances = new ArrayList<>();

                for (int j = 0; j < N; j++) {
                    if (i == j) {
                        continue;
                    }

                    double d = euclideanDistanceOfDrones(drones.get(i), drones.get(j));
                    distances.add(d);
                }

                Drone drone = drones.get(i);
                drone.lat = (drone.lat + (new Random().nextDouble() * 2 - 1) * Collections.min(distances));
                drone.lon = (drone.lon + (new Random().nextDouble() * 2 - 1) * Collections.min(distances));
                drone.alt = (drone.alt + (new Random().nextDouble() * 2 - 1) * Collections.min(distances));

                double energySum = drones.stream().mapToDouble(d -> d.energy).sum();
                double Bopt = drone.energy / energySum;

                if (Bopt >= gbest) {
                    gbest = Bopt;
                    leader = drone;
                }
            }

            List<Cluster> clusters = clusterDrones(drones);

            for (Cluster cluster : clusters) {
                List<Drone> clusterDrones = cluster.getDrones();
                if (leader == null) continue;

                for (Drone drone : clusterDrones) {
                    drone.lat = (leader.lat);
                    drone.lon = (leader.lon);
                    drone.alt = (leader.alt);
                }
            }
        }

        System.out.println("Leader Drone: " + leader);
        return leader;
    }

    public static double euclideanDistanceOfDrones(Drone drone1, Drone drone2) {
        double x1 = drone1.lat;
        double y1 = drone1.lon;
        double z1 = drone1.alt;
        double x2 = drone2.lat;
        double y2 = drone2.lon;
        double z2 = drone2.alt;
        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2) + pow(z2 - z1, 2));
    }

    public static void main(String[] args) {
        List<Drone> drones = new ArrayList<>();
//        drones.add(new Drone(1, 0.0, 0.0, 0.0, 100.0));
//        drones.add(new Drone(2, 1.0, 1.0, 1.0, 200.0));
//        drones.add(new Drone(3, 2.0, 2.0, 2.0, 150.0));
//        drones.add(new Drone(4, 3.0, 3.0, 3.0, 180.0));
//        drones.add(new Drone(5, 4.0, 4.0, 4.0, 120.0));
//        drones.add(new Drone(6, 5.0, 5.0, 5.0, 250.0));
//        drones.add(new Drone(7, 6.0, 6.0, 6.0, 190.0));
//        drones.add(new Drone(8, 7.0, 7.0, 7.0, 170.0));
//        drones.add(new Drone(9, 8.0, 8.0, 8.0, 220.0));
//        drones.add(new Drone(10, 9.0, 9.0, 9.0, 140.0));
//        drones.add(new Drone(11, 0.0, 0.0, 0.0, 100.0));
//        drones.add(new Drone(12, 1.0, 1.0, 1.0, 200.0));
//        drones.add(new Drone(13, 2.0, 2.0, 2.0, 150.0));
//        drones.add(new Drone(14, 3.0, 3.0, 3.0, 180.0));
//        drones.add(new Drone(15, 4.0, 4.0, 4.0, 120.0));
//        drones.add(new Drone(16, 5.0, 5.0, 5.0, 250.0));
//        drones.add(new Drone(17, 6.0, 6.0, 6.0, 190.0));
//        drones.add(new Drone(18, 7.0, 7.0, 7.0, 170.0));
//        drones.add(new Drone(19, 8.0, 8.0, 8.0, 220.0));
//        drones.add(new Drone(20, 9.0, 9.0, 9.0, 140.0));

        drones.add(new Drone(1, 7.9426, 81.1366, 0.301, 2095));
        drones.add(new Drone(2, 12.9427, 80.1366, 0.4983, 2146));
        drones.add(new Drone(3, 14.9428, 81.1367, 0.3999, 2493));
        drones.add(new Drone(4, 11.9429, 81.1365, 0.4121, 2575));
        drones.add(new Drone(5, 9.9425, 80.1364, 0.35, 2032));

        int iterations = 5;
        BOLDAlgorithm bold = new BOLDAlgorithm();

        Drone leader = bold.runBOLDAlgorithm(drones, iterations);

        System.out.println("Leader = " + leader);


        drones.sort((a, b) -> {
            if (a.lat != b.lat) return (int) (a.lat - b.lat);
            if (a.lon != b.lon) return (int) (a.lon - b.lon);
            return (int) (a.alt - b.alt);
        });
    }
}
