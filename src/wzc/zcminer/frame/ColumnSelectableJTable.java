package wzc.zcminer.frame;

import javax.swing.JTable;

public class ColumnSelectableJTable extends JTable {
	public int column;

	public ColumnSelectableJTable(Object[][] items, Object[] headers) {
		super(items, headers);
		setColumnSelectionAllowed(true);
		setRowSelectionAllowed(false);
	}
}