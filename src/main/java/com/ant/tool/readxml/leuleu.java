package com.ant.tool.readxml;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class leuleu {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					leuleu window = new leuleu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public leuleu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] columnNames = {"First Name", "Last Name", ""};
		Object[][] data =
		{
		    {"Homer", "Simpson"},
		    {"Madge", "Simpson", "delete Madge"},
		    {"Bart",  "Simpson", "delete Bart"},
		    {"Lisa",  "Simpson", "delete Lisa"},
		};
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);
		panel.add(table);
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable)e.getSource();
			    int modelRow = Integer.valueOf( e.getActionCommand() );
			    ((DefaultTableModel)table.getModel()).removeRow(modelRow);
			}
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);
//		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

}
