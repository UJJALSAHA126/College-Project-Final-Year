package our_algorithm;

import java.util.*;
import java.lang.Math;

public class MyAlgorithm {
    private Map<Drone, Map<Drone, Double>> distanceMatrix;
    private final List<Drone> drones;

    public MyAlgorithm(List<Drone> drones) {
        this.drones = drones;
    }

    // TODO
    private int getNumOfClusters(List<Drone> drones) {
        // TODO
        return 2;
    }

    // TODO
    private int getDronesPerCluster(List<Drone> drones) {
        // TODO
        int n = drones.size();
//        Drone firstDrone = drones.get(0);
//        List<Double> distanceList = new ArrayList<>(this.distanceMatrix.get(firstDrone).values().stream().toList());
//
//        distanceList.sort(Comparator.comparingDouble(o -> o));

        int nSqrt = (int) Math.sqrt(n);
        if (nSqrt * nSqrt < n) nSqrt++;

        return nSqrt;
    }

    private void sortDrones(List<Drone> drones) {
        drones.sort((a, b) -> {
            if (a.lon != b.lon) return Double.compare(a.lon, b.lon);
            if (a.lat != b.lat) return Double.compare(b.lat, a.lat);
            return Double.compare(b.alt, a.alt);
        });
    }

    // Cluster formation algorithm By Ujjal
    public List<Cluster> formCluster() {
        // Sort the drones
        sortDrones(drones);

        // Constructing the distanceMatrix
        this.distanceMatrix = getDistanceMatrix(drones);

        // Choose how many clusters to be formed
        // int numOfClusters = getNumOfClusters(drones);

        // Choose number of drones in each cluster
        // TODO
        int dronesPerCluster = getDronesPerCluster(drones);

        // Formation Of Clusters
        List<Cluster> clusters = new ArrayList<>();
        int clusterId = -1;

        for (Drone drone : drones) {
            // If the drone is already processed then skip this drone
            if (drone.isInCluster()) continue;

            // Create a new cluster
            clusterId++;

            Cluster cluster = new Cluster(clusterId);
            cluster.addDrone(drone);
            drone.setClusterId(cluster.clusterId);

            // Store all the drones order by the distance between them
            PriorityQueue<Map.Entry<Drone, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));

            pq.addAll(
                    distanceMatrix.get(drone).entrySet()
                            .stream()
                            .filter(o -> !o.getKey().isInCluster())
                            .toList()
            );

            int addedDrones = 1;
            while (addedDrones < dronesPerCluster && !pq.isEmpty()) {
                addedDrones++;
                Drone curDrone = pq.remove().getKey();

                // Adding the current drone to the cluster and setting the clusterId fro the drone
                cluster.addDrone(curDrone);
                curDrone.setClusterId(cluster.clusterId);
            }

            // Adding the cluster to the list
            clusters.add(cluster);
        }

        return clusters;
    }

    // Cluster Head Selection By Sourav
    public List<Drone> selectClusterHead(Cluster cluster) {
        int n = cluster.size();
        double[][] distanceMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distanceMatrix[i][j] = euclideanDistanceOfDrones(cluster.drones.get(i), cluster.drones.get(j));
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }

        List<Pair<Drone, Double>> boptList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double sumOfAllDistance = 0.0;

            for (int j = 0; j < n; j++) {
                sumOfAllDistance += distanceMatrix[i][j];
            }

            double boptVal = getBoptValue(cluster.drones.get(i), sumOfAllDistance);
            boptList.add(new Pair<>(cluster.drones.get(i), boptVal));
        }

        // sort by their bopt value
        boptList.sort(Comparator.comparingDouble(a -> -a.second));

        Drone clusterHead, clusterSubLeader = null;

        clusterHead = boptList.get(0).first;
        cluster.setLeader(clusterHead);

        if (boptList.size() > 1) {
            clusterSubLeader = boptList.get(1).first;
            cluster.setSubLeader(clusterSubLeader);
        }

        // add top two drones which we calculate by their value
        List<Drone> headOfCluster = new ArrayList<>();
        headOfCluster.add(clusterHead);

        if (clusterSubLeader != null) headOfCluster.add(clusterSubLeader);

        return headOfCluster;
    }

    private double getBoptValue(Drone drone, double sumOfAllDistance) {
        return (drone.trustValue / sumOfAllDistance);
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
        double x1 = drone1.lon;
        double y1 = drone1.lat;
        double z1 = drone1.alt;

        double x2 = drone2.lon;
        double y2 = drone2.lat;
        double z2 = drone2.alt;

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }
}
