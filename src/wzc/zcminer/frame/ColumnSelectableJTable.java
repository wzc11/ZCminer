package wzc.zcminer.frame;

import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import javax.swing.JTable;  
import javax.swing.table.JTableHeader;  

public class ColumnSelectableJTable extends JTable{  
	public int column; 
    public ColumnSelectableJTable(Object[][]items,Object[] headers){  
        super(items,headers);  
        setColumnSelectionAllowed(true);  
        setRowSelectionAllowed(false);  
        final JTableHeader header=getTableHeader();  
        header.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){    
                if(!e.isShiftDown()) clearSelection();  
                column=header.columnAtPoint(e.getPoint());  
                addColumnSelectionInterval(column,column);  
                ImportPanel.buttonGroup.clearSelection();
            }  
        });  
    }  
}  