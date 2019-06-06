package com.ant.tool.readxml;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JTable;

public class testFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testFrame frame = new testFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public testFrame() throws Exception{
		File file = new File("/home/thinkpad/Documents/test.xlsx"); 
		  
        // Create a FileInputStream object 
        // for getting the information of the file 
        FileInputStream fip = new FileInputStream(file); 
  
        // Getting the workbook instance for XLSX file 
        XSSFWorkbook workbook = new XSSFWorkbook(fip); 
        DataFormatter dataFormatter = new DataFormatter();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("First Name");
		model.addColumn("Last Name");
        // Ensure if file exist or not 
        if (file.isFile() && file.exists()) { 
        	Sheet sheet = workbook.getSheetAt(0);
        	Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

        		model.addRow(new Object[] {row.getCell(0).toString(), row.getCell(1).toString()});
//                // Now let's iterate over the columns of the current row
//                Iterator<Cell> cellIterator = row.cellIterator();
//
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    String cellValue = dataFormatter.formatCellValue(cell);
//                    System.out.print(cellValue + "\t");
//                }
//                System.out.println();
            }
        } 
        else { 
            System.out.println("Geeks.xlsx either not exist"
                               + " or can't open"); 
        } 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		table = new JTable(model);
		contentPane.add(table, BorderLayout.NORTH);
	}

}
