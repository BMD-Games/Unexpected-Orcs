package Utility;

public class GenericPair<K, V> {
    private K a;
    private V b;

    public GenericPair(K a, V b) {
        this.a = a;
        this.b = b;
    }

    public K a() {
        return this.a;
    }

    public V b() {
        return this.b;
    }

    public void setA(K a) {
        this.a = a;
    }

    public void setB(V b) {
        this.b = b;
    }
}
