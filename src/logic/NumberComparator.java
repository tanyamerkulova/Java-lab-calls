package logic;

import java.util.Comparator;

public class NumberComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        Integer i1 = Integer.valueOf(o1);
        Integer i2 = Integer.valueOf(o2);
        return i1.compareTo(i2);
    }
}
