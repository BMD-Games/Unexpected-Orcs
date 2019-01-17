package Utility;

import processing.core.PVector;

import java.util.Comparator;

public class PVectorZComparator implements Comparator<PVector> {
    @Override
    public int compare(PVector a, PVector b) {
        if (a.z < b.z) return 1;
        if (a.z > b.z) return -1;
        return 0;
    }
}