package com.ant.tool.readXml;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.JEditorPane;
import java.awt.Color;
import java.awt.Font;



/**
 *
 * @author hungm
 */
public class NewJFrame extends javax.swing.JFrame {
	private JTextField searchText;
	private JTextField txtMinPaiMing;
	private JTextField txtMaxPaiMing;
	private JTextField textField;
	private JTextField txtShengWang;
	private JTextField txtID;
	private JTextField txtGold;

    public NewJFrame() {
    	getContentPane().setBackground(new Color(230, 230, 250));
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        JButton importButton = new JButton("Import");
        importButton.setBackground(new Color(135, 206, 250));
        
        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(135, 206, 250));
        resetButton.setForeground(Color.BLACK);
        
        JLabel messagelable = new JLabel("Successed !");
        messagelable.setForeground(new Color(0, 128, 0));
        messagelable.setFont(new Font("Tahoma", Font.ITALIC, 13));
        
        searchText = new JTextField();
        searchText.setColumns(10);
        
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(135, 206, 250));
        
        JLabel lblMinPaiMing = new JLabel("MinPaiMing");
        
        txtMinPaiMing = new JTextField();
        txtMinPaiMing.setColumns(10);
        
        JLabel lbl_MaxPaiMing = new JLabel("MaxPaiMing");
        
        txtMaxPaiMing = new JTextField();
        txtMaxPaiMing.setColumns(10);
        
        JLabel lblGoods = new JLabel("Goods");
        
        textField = new JTextField();
        textField.setColumns(10);
        
        JLabel lblShengWang = new JLabel("ShengWang");
        
        txtShengWang = new JTextField();
        txtShengWang.setColumns(10);
        
        JLabel lblID = new JLabel("ID");
        
        txtID = new JTextField();
        txtID.setColumns(10);
        
        JLabel lblGold = new JLabel("Gold");
        
        txtGold = new JTextField();
        txtGold.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\searchicon.jpg"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(37)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(messagelable, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(6)
        							.addComponent(searchText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lblNewLabel)
        							.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
        							.addComponent(saveButton))
        						.addGroup(layout.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblShengWang)
        								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        									.addComponent(lblMinPaiMing)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(lblGoods)
        										.addComponent(lbl_MaxPaiMing)))
        								.addComponent(lblID)
        								.addComponent(lblGold))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(txtMaxPaiMing)
        								.addComponent(textField)
        								.addComponent(txtShengWang, 197, 197, Short.MAX_VALUE)
        								.addComponent(txtID)
        								.addComponent(txtMinPaiMing, 194, 194, Short.MAX_VALUE)
        								.addComponent(txtGold)))))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(23)
        					.addComponent(importButton)
        					.addPreferredGap(ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
        					.addComponent(resetButton)))
        			.addGap(37))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(16)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(importButton)
        				.addComponent(resetButton))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(messagelable)
        			.addGap(1)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblID)
        				.addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblMinPaiMing)
        				.addComponent(txtMinPaiMing, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lbl_MaxPaiMing)
        				.addComponent(txtMaxPaiMing, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblGoods)
        				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblShengWang)
        				.addComponent(txtShengWang, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblGold)
        				.addComponent(txtGold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(searchText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(saveButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);
        
        
        
        
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
}
