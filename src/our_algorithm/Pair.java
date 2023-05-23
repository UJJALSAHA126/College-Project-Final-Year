package our_algorithm;

import java.util.Objects;

public class Pair<K, V> {
    K first;
    V second;

    public Pair(K k, V v) {
        this.first = k;
        this.second = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
