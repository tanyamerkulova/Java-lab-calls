package gui;

import logic.DateComparator;
import logic.DateRowFilter;
import logic.FileHelper;
import logic.NumberComparator;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class Gui {

    private static final String PATH = "test.txt";

    private JFrame frame;

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        String[] columnHeaders = new String[] {"№", "от кого", "кому", "дата", "время", "длительность"};
        String[][] data = FileHelper.read(PATH);
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders) {
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };

        String lastNumberValue = model.getValueAt(model.getRowCount() - 1, 0).toString();
        AtomicInteger lastNumber = new AtomicInteger(Integer.parseInt(lastNumberValue));

        JTable table = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        sorter.setComparator(0, new NumberComparator());
        sorter.setComparator(3, new DateComparator());
        sorter.setComparator(5, new NumberComparator());
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        JLabel labelNewFrom = new JLabel("от кого: ");
        JTextField textFieldNewFrom = new JTextField(10);

        JLabel labelNewTo = new JLabel("кому: ");

        JTextField textFieldNewTo = new JTextField(10);
        JLabel labelNewDate = new JLabel("дата: ");
        JTextField newDate = new JTextField(10);
        JLabel labelNewTime = new JLabel("время: ");
        JTextField newTime = new JTextField(10);
        JLabel labelNewDuration = new JLabel("длительность: ");
        JTextField newDuration = new JTextField(10);
        JButton buttonAddNew = new JButton("Добавить");
        buttonAddNew.addActionListener(e -> {
            String[] newData = new String[] {String.valueOf(lastNumber.incrementAndGet()),
                    textFieldNewFrom.getText(),
                    textFieldNewTo.getText(),
                    newDate.getText(),
                    newTime.getText(),
                    newDuration.getText()
            };
            model.addRow(newData);
            FileHelper.writeWithAppend(String.join(" ", newData) + "\n", PATH);
            frame.repaint();
        });

        JLabel labelDeleteNumber = new JLabel("Удалить №: ");
        JTextField textFieldNumber = new JTextField(10);
        JButton buttonDelete = new JButton("Удалить");
        buttonDelete.addActionListener(e -> {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(textFieldNumber.getText())) {
                    model.removeRow(i);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < model.getRowCount(); j++) {
                        for (int k = 0; k < model.getColumnCount(); k++) {
                            stringBuilder.append(model.getValueAt(j, k)).append(" ");
                        }
                        stringBuilder.append("\n");
                    }
                    FileHelper.write(stringBuilder.toString(), PATH);
                    frame.repaint();
                }
            }
        });

        JLabel labelCallsSince = new JLabel("Звонки с: ");
        JTextField textFieldSinceDate = new JTextField(10);

        JLabel labelCallsTo = new JLabel("по: ");
        JTextField textFieldToDate = new JTextField(10);

        JButton buttonGetDate = new JButton("Получить");
        buttonGetDate.addActionListener(e -> {
            sorter.setRowFilter(new DateRowFilter(textFieldSinceDate.getText(), textFieldToDate.getText()));
            frame.repaint();
        });

        JLabel labelOnlyFrom = new JLabel("Только от: ");
        JTextField textFieldFrom = new JTextField(10);
        JButton buttonGetFrom = new JButton("Получить");
        buttonGetFrom.addActionListener(e -> setFilter(sorter, textFieldFrom.getText(), 1));

        JLabel labelOnlyTo = new JLabel("Только к: ");
        JTextField textFieldTo = new JTextField(10);
        JButton buttonGetTo = new JButton("Получить");
        buttonGetTo.addActionListener(e -> setFilter(sorter, textFieldTo.getText(), 2));

        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                        .addComponent(scrollPane)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(LEADING)
                                    .addComponent(labelNewFrom)
                                    .addComponent(labelDeleteNumber)
                                    .addComponent(labelCallsSince)
                                    .addComponent(labelOnlyFrom))
                                .addGroup(layout.createParallelGroup(LEADING)
                                    .addComponent(textFieldNewFrom)
                                    .addComponent(textFieldNumber)
                                    .addComponent(textFieldSinceDate)
                                    .addComponent(textFieldFrom))
                                .addGroup(layout.createParallelGroup(LEADING)
                                    .addComponent(labelNewTo)
                                    .addComponent(buttonDelete)
                                    .addComponent(labelCallsTo)
                                    .addComponent(buttonGetFrom))
                                .addGroup(layout.createParallelGroup(LEADING)
                                    .addComponent(textFieldNewTo)
                                    .addComponent(textFieldToDate)
                                    .addComponent(labelOnlyTo))
                                .addGroup(layout.createParallelGroup(LEADING)
                                    .addComponent(labelNewDate)
                                    .addComponent(buttonGetDate)
                                    .addComponent(textFieldTo))
                                .addGroup(layout.createParallelGroup(LEADING)
                                    .addComponent(newDate)
                                    .addComponent(buttonGetTo))
                                .addComponent(labelNewTime)
                                .addComponent(newTime)
                                .addComponent(labelNewDuration)
                                .addComponent(newDuration)
                                .addComponent(buttonAddNew))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(labelNewFrom)
                        .addComponent(textFieldNewFrom)
                        .addComponent(labelNewTo)
                        .addComponent(textFieldNewTo)
                        .addComponent(labelNewDate)
                        .addComponent(newDate)
                        .addComponent(labelNewTime)
                        .addComponent(newTime)
                        .addComponent(labelNewDuration)
                        .addComponent(newDuration)
                        .addComponent(buttonAddNew))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(labelDeleteNumber)
                        .addComponent(textFieldNumber)
                        .addComponent(buttonDelete))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(labelCallsSince)
                        .addComponent(textFieldSinceDate)
                        .addComponent(labelCallsTo)
                        .addComponent(textFieldToDate)
                        .addComponent(buttonGetDate))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(labelOnlyFrom)
                        .addComponent(textFieldFrom)
                        .addComponent(buttonGetFrom)
                        .addComponent(labelOnlyTo)
                        .addComponent(textFieldTo)
                        .addComponent(buttonGetTo))
        );

        frame.setSize(1100, 500);
        frame.setVisible(true);
    }

    private void setFilter(TableRowSorter<TableModel> sorter, String regex, int index) {
        sorter.setRowFilter(RowFilter.regexFilter(regex, index));
        frame.repaint();
    }

}
