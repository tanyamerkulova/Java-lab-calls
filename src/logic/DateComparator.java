package logic;

import java.util.Comparator;

public class DateComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        String[] first = o1.split("\\.");
        String[] second = o2.split("\\.");
        for (int i = first.length - 1; i >= 0; i--) {
            int comparison = first[i].compareTo(second[i]);
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }
}
