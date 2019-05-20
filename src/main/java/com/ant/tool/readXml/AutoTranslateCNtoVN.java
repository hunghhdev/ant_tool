package com.ant.tool.readXml;

import org.apache.poi.ss.usermodel.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class AutoTranslateCNtoVN {

    private JFrame frame;
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

    private NodeList getElementByKey(String key) throws Exception {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//*/@" + key);
        return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    }

    private File getAndValidateFile() {
        // true xml, false excel
        File file = null;
        JFileChooser fileDialog = new JFileChooser();
        int returnVal = fileDialog.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileDialog.getSelectedFile();
        }
        return file;
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        common = new Common();
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        JButton btnSelectXml = new JButton("Select XML");
        panel.add(btnSelectXml);

        JButton btnSelectExcel = new JButton("Select Excel");
        btnSelectExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                fileExcel = getAndValidateFile();
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
                try (Workbook workbook = WorkbookFactory.create(fileExcel)) {

                    Sheet sheet = workbook.getSheetAt(Integer.valueOf(txtSheetAt.getText()));
                    DataFormatter dataFormatter = new DataFormatter();
                    int colChina = 0;
                    int colVietnam = 0;
                    // Chỗ này để lấy trị trí của các cột
                    for (Cell cell : sheet.getRow(1)) {
                        if (cell.getAddress().toString().startsWith(txtColChinese.getText()))
                            colChina = cell.getColumnIndex();
                        if (cell.getAddress().toString().startsWith(txtColVietnamese.getText()))
                            colVietnam = cell.getColumnIndex();
                    }

                    NodeList nodeList = getElementByKey(txtAttributeName.getText());
                    int length = nodeList.getLength();
                    for (int i = 0; i < length; i++) {
                        // Rồi chỗ này thì đọc code đi =))
                        for (Row row : sheet) {
                            if (dataFormatter.formatCellValue(row.getCell(colChina)).equals(nodeList.item(i).getNodeValue())) {
                                nodeList.item(i).setNodeValue(dataFormatter.formatCellValue(row.getCell(colVietnam)));
                                break;
                            }
                        }
                    }
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(fileXml);
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.transform(source, result);
                    lblMessage.setText("XML file updated successfully");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    lblMessage.setText("Error! Call dev now =))");
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
            }
        });
        btnReset.setEnabled(false);
        panel.add(btnReset);

        lblMessage = new JLabel("lblMessage");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblMessage, BorderLayout.SOUTH);
        btnSelectXml.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                fileXml = getAndValidateFile();
                if (fileXml != null && fileXml.exists() && common.getFileExtension(fileXml).equals(".xml")) {
                    try {
                        btnSelectExcel.setEnabled(true);
                        document = common.parseXML(fileXml.getPath());
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
