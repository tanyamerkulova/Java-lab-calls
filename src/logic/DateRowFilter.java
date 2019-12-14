package logic;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

public class DateRowFilter extends RowFilter<TableModel, Integer> {

    private final String since;
    private final String to;

    public DateRowFilter(String since, String to) {
        this.since = since;
        this.to = to;
    }

    @Override
    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
        String value = entry.getStringValue(3);
        DateComparator comparator = new DateComparator();
        if (comparator.compare(value, since) >= 0) {
            return comparator.compare(value, to) <= 0;
        }
        return false;
    }
}
