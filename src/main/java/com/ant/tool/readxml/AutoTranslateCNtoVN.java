package com.ant.tool.readxml;

import org.apache.poi.ss.usermodel.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoTranslateCNtoVN {

    public JFrame frame;
    private JTextField txtAttributeName;
    private JTextField txtColChinese;
    private JTextField txtColVietnamese;
    private JTextField txtSheetAt;
    private JButton btnConfirm;
    private JButton btnReset;
    private File fileXml;
    private File fileExcel;
    private Document document;
    private Common common;
    private JLabel lblMessage;
    private JButton btnSelectExcel;
    private JPanel panelNode;
    private JEditorPane dtrpnHdsdChnFile;

    private java.util.List<Element> getElementsByName(String name, Element parent, java.util.List<Element> elementList)
    {
        if (elementList == null)
            elementList = new ArrayList<>();

        for (Iterator i = parent.elementIterator(); i.hasNext(); ) {
            Element current = (Element) i.next();
            if (checkHasAttributeWithName(name,current))
            {
                elementList.add(current);
            }

            getElementsByName(name, current, elementList);
        }
        return elementList;
    }

    private boolean checkHasAttributeWithName(String attName, Element element) {
        List<Attribute> el = element.attributes();
        for (Attribute attribute : el) {
            if (attribute.getName().equals(attName)) return true;
        }
        return false;
    }

    private boolean checkTextfield() {
        return (txtColVietnamese.getText().equals("") || txtColChinese.getText().equals("")
                || txtSheetAt.getText().equals("") || txtAttributeName.getText().equals("")) ? true : false;
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AutoTranslateCNtoVN window = new AutoTranslateCNtoVN();
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
    public AutoTranslateCNtoVN() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 300);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        common = new Common();
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        JButton btnSelectXml = new JButton("Select XML");
        panel.add(btnSelectXml);

        btnSelectExcel = new JButton("Select Excel");
        btnSelectExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                fileExcel = common.getAndValidateFile();
                if (common.getFileExtension(fileExcel).equals(".xlsx")) {
                    try {
                        txtAttributeName.setEnabled(true);
                        txtSheetAt.setEnabled(true);
                        txtColChinese.setEnabled(true);
                        txtColVietnamese.setEnabled(true);
                        lblMessage.setText("validate file excel OK");
                    } catch (Exception e) {
                        e.printStackTrace();
                        lblMessage.setText("Error! Call dev now =))");
                    }
                } else {
                    lblMessage.setText("File extension invalid");
                }
            }
        });
        panel.add(btnSelectExcel);

        txtAttributeName = new JTextField();
        txtAttributeName.setEnabled(false);
        txtAttributeName.setColumns(10);
        panel.add(txtAttributeName);

        txtSheetAt = new JTextField();
        txtSheetAt.setEnabled(false);
        txtSheetAt.setColumns(2);
        txtSheetAt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // not use
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not use
            }
        });
        panel.add(txtSheetAt);

        txtColChinese = new JTextField();
        txtColChinese.setEnabled(false);
        txtColChinese.setColumns(2);
        txtColChinese.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // not use
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !checkTextfield()) {
                    btnConfirm.setEnabled(true);
                    btnReset.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not use
            }
        });
        panel.add(txtColChinese);

        txtColVietnamese = new JTextField();
        txtColVietnamese.setEnabled(false);
        txtColVietnamese.setColumns(2);
        txtColVietnamese.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // not use
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !checkTextfield()) {
                    btnConfirm.setEnabled(true);
                    btnReset.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not use
            }
        });
        panel.add(txtColVietnamese);

        btnConfirm = new JButton("Action");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fos = null;
                Workbook workbook = null;
                try (FileInputStream excelFile = new FileInputStream(fileExcel)) {
                    workbook = WorkbookFactory.create(excelFile);
                    Sheet sheet = workbook.getSheetAt(Integer.valueOf(txtSheetAt.getText()));
                    int colChina = 0;
                    int colVietnam = 0;
                    // Chỗ này để lấy trị trí của các cột
                    for (Cell cell : sheet.getRow(1)) {
                        if (cell.getAddress().toString().startsWith(txtColChinese.getText()))
                            colChina = cell.getColumnIndex();
                        if (cell.getAddress().toString().startsWith(txtColVietnamese.getText()))
                            colVietnam = cell.getColumnIndex();
                    }

                    DataFormatter dataFormatter = new DataFormatter();
                    List<Element> elementList = getElementsByName(txtAttributeName.getText(),document.getRootElement(),null);
                    for (Element element : elementList){
                        for (Row row : sheet) {
                            if (dataFormatter.formatCellValue(row.getCell(colChina)).equals(element.attribute(txtAttributeName.getText()).getValue())) {
                                element.attribute(txtAttributeName.getText()).setValue(dataFormatter.formatCellValue(row.getCell(colVietnam)));
                                break;
                            }
                        }
                    }
                    // Pretty print the document to System.out
                    fos = new FileOutputStream(fileXml);
                    OutputFormat format = OutputFormat.createPrettyPrint();
                    XMLWriter writer;
                    writer = new XMLWriter(fos, format );
                    writer.write(document);
                    lblMessage.setText("XML file updated successfully");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    lblMessage.setText("Error! Call dev now =))");
                } finally {
                    if (fos!=null){
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (workbook!=null){
                        try {
                            workbook.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        btnConfirm.setEnabled(false);
        panel.add(btnConfirm);

        btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileXml = null;
                fileExcel = null;
                lblMessage.setText("");
                txtAttributeName.setText("");
                txtAttributeName.setEnabled(false);
                txtSheetAt.setText("");
                txtSheetAt.setEnabled(false);
                txtColChinese.setText("");
                txtColChinese.setEnabled(false);
                txtColVietnamese.setText("");
                txtColVietnamese.setEnabled(false);
                btnConfirm.setEnabled(false);
                btnReset.setEnabled(false);
            }
        });
        btnReset.setEnabled(false);
        panel.add(btnReset);

        lblMessage = new JLabel("");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblMessage, BorderLayout.SOUTH);
        
        panelNode = new JPanel();
        frame.getContentPane().add(panelNode, BorderLayout.CENTER);
        
        dtrpnHdsdChnFile = new JEditorPane();
        dtrpnHdsdChnFile.setSize(200, 200);
        dtrpnHdsdChnFile.setText("HDSD: ch\u1ECDn file xml tr\u01B0\u1EDBc, r\u1ED3i ch\u1ECDn file excel, \u00F4 t1 nh\u1EADp t\u00EAn attribute c\u1EA7n s\u1EEDa, \u00F4 2 nh\u1EADp v\u1ECB tr\u00ED c\u1EE7a sheet(0,1,2,..), \u00F4 3 nh\u1EADp c\u1ED9t ti\u1EBFng trung, \u00F4 4 nh\u1EADp c\u1ED9t ti\u1EBFng vi\u1EC7t, nh\u1EADp xong \u00F4 4 nh\u1EA5n Enter \u0111\u1EC3 hi\u1EC7n n\u00FAt action");
        panelNode.add(dtrpnHdsdChnFile);
        btnSelectXml.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                fileXml = common.getAndValidateFile();
                if (fileXml != null && fileXml.exists() && common.getFileExtension(fileXml).equals(".xml")) {
                    try {
                        btnSelectExcel.setEnabled(true);

                        SAXReader saxBuilder = new SAXReader();
                        document = saxBuilder.read(fileXml);
                        lblMessage.setText("validate file xml OK");
                    } catch (Exception e) {
                        e.printStackTrace();
                        lblMessage.setText("Error! Call dev now =))");
                    }
                } else {
                    lblMessage.setText("File extension invalid");
                }
            }
        });
    }

}
