package our_algorithm;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class MyAlgorithm1 {
    private Map<Drone, Map<Drone, Double>> distanceMatrix;
    private List<Drone> drones;

    public MyAlgorithm1(List<Drone> drones) {
        this.drones = drones;
    }

    // TODO
    private int getNumOfClusters(List<Drone> drones) {
        // TODO
        return 2;
    }

    // TODO
    private int getDronesPerCluster(int numOfClusters, List<Drone> drones) {
        // TODO
        return 1;
    }

    private void sortDrones(List<Drone> drones) {
        drones.sort((a, b) -> {
            if (a.lon != b.lon) return (int) (a.lon - b.lon);
            if (a.lat != b.lat) return (int) (a.lat - b.lat);
            return (int) (a.alt - b.alt);
        });
    }

    // Cluster formation algorithm
    public List<Cluster> formCluster() {
        // Sort the drones
        sortDrones(drones);

        // Choose how many clusters to be formed
        int numOfClusters = getNumOfClusters(drones);

        // Choose number of drones in each cluster
        int dronesPerCluster = getDronesPerCluster(numOfClusters, drones);

        // Constructing the distanceMatrix
        this.distanceMatrix = getDistanceMatrix(drones);

        // Formation Of Clusters
        List<Cluster> clusters = new ArrayList<>();
        int clusterId = 0;

        Set<Drone> processedDrones = new HashSet<>();
        for (Drone drone : drones) {
            // If the drone is already processed then skip this drone
            if (processedDrones.contains(drone)) continue;

            processedDrones.add(drone);

            // Create a new cluster
            Cluster cluster = new Cluster(clusterId++);
            cluster.addDrone(drone);
            drone.setClusterId(cluster.clusterId);

            // Store all the drones order by the distance between them
            PriorityQueue<Map.Entry<Drone, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
            pq.addAll(distanceMatrix.get(drone).entrySet().stream().toList());

            int addedDrones = 1;
            while (addedDrones < dronesPerCluster && !pq.isEmpty()) {
                addedDrones++;
                Drone curDrone = pq.remove().getKey();

                // Adding the current drone to the cluster and setting the clusterId fro the drone
                cluster.addDrone(curDrone);
                curDrone.setClusterId(cluster.clusterId);

                // Skipping the drone for future precesses
                processedDrones.add(curDrone);
            }

            // Adding the cluster to the list
            clusters.add(cluster);
        }

        return clusters;
    }

    // Cluster head selection algorithm
    public List<Drone> getClusterHead(Cluster cluster) {
        if (cluster == null || cluster.drones == null || cluster.drones.size() < 1)
            return null;

        Map<Drone, Map<Drone, Double>> distMat = getDistanceMatrix(cluster.drones);
//        List<Double> listOfSumOfDistances = new ArrayList<>();

        // Creating the BOPT list
        List<Pair<Drone, Double>> boptList = new ArrayList<>();

        for (Drone drone : cluster.drones) {
            double distSum = getSumOfDistances(drone, distMat);
//            listOfSumOfDistances.add(distSum);

            double boptVal = getBoptValue(drone, distSum);
            boptList.add(new Pair<>(drone, boptVal));
        }

        boptList.sort(Comparator.comparingDouble(o -> -o.second));

        Drone leader, subLeader = null;
        leader = boptList.get(0).first;
        cluster.setLeader(leader);

        if (boptList.size() > 1) {
            subLeader = boptList.get(1).first;
            cluster.setSubLeader(subLeader);
        }

        List<Drone> leaders = new ArrayList<>();
        leaders.add(leader);

        if (subLeader != null) leaders.add(subLeader);

        return leaders;
    }

    // Should be checked
    private double getBoptValue(Drone drone, double sumOfDistances) {
        double sqrtDist = Math.sqrt(sumOfDistances);
        return (drone.trustValue) / sqrtDist;
    }

    private double getSumOfDistances(Drone drone, Map<Drone, Map<Drone, Double>> dist) {
        try {
            return dist.get(drone)
                    .values()
                    .stream()
                    .mapToDouble(o -> o)
                    .sum();
        } catch (Exception e) {
            return -1.0;
        }
    }

    private Map<Drone, Map<Drone, Double>> getDistanceMatrix(List<Drone> drones) {
        this.distanceMatrix = new HashMap<>();
        int n = drones.size();

        for (int i = 0; i < n; i++) {
            Drone drone1 = drones.get(i);

            for (int j = i + 1; j < n; j++) {
                Drone drone2 = drones.get(j);
                double dist = euclideanDistanceOfDrones(drone1, drone2);

                distanceMatrix.putIfAbsent(drone1, new HashMap<>());
                distanceMatrix.putIfAbsent(drone2, new HashMap<>());

                distanceMatrix.get(drone1).put(drone2, dist);
                distanceMatrix.get(drone2).put(drone1, dist);
            }
        }

        return distanceMatrix;
    }

    private static double euclideanDistanceOfDrones(Drone drone1, Drone drone2) {
        double x1 = drone1.lat;
        double y1 = drone1.lon;
        double z1 = drone1.alt;

        double x2 = drone2.lat;
        double y2 = drone2.lon;
        double z2 = drone2.alt;
        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2) + pow(z2 - z1, 2));
    }
}
