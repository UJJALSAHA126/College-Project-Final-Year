import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> list  =  List.of(1, 2, 3, 10, 11, 15, 20, 23);
        int ind = Collections.binarySearch(list, 25);

        if (ind >= 0)
            System.out.println("Ind = " + ind);
        else System.out.println("Original Ind = " + ind + " Ind = " + (-(ind + 1)));
    }
}