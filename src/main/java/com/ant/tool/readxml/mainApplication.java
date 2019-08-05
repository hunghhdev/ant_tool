package com.ant.tool.readxml;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainApplication extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainApplication frame = new mainApplication();
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
	public mainApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("=))");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JButton btnEditXML = new JButton("edit XML");
		btnEditXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new Application();
				frame.setVisible(true);
			}
		});
		panel.add(btnEditXML);
		
		JButton btnAutoEditXml = new JButton("Auto Edit Xml");
		btnAutoEditXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutoTranslateCNtoVN autoTranslateCNtoVN = new AutoTranslateCNtoVN();
				autoTranslateCNtoVN.frame.setVisible(true);
			}
		});
		panel.add(btnAutoEditXml);

		JButton btnFindFileChina = new JButton("Tìm tiếng tàu =))");
		btnFindFileChina.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindFileHaveChinese findFileHaveChinese = new FindFileHaveChinese();
				findFileHaveChinese.setVisible(true);
			}
		});
		panel.add(btnFindFileChina);
	}

}
