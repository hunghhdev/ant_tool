package com.ant.tool.readxml;

import org.w3c.dom.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FindFileHaveChinese extends JFrame {

    private JPanel contentPane;
    private File fileLocation;
    private Common common = new Common();
    private Document document;
    private int countFile = 0;
    private JPanel panelInfo;
    private JLabel lblMessage;

    public static java.util.List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<>();

        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        return resultList;
    }

    public boolean containsHanScript(String s) {
        if (s.equals("")) return false;
        return s.codePoints().anyMatch(
                codepoint -> Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }

    private Map<String, List<Integer>> getListAttribute() {
        Map<String, List<Integer>> mapList = new HashMap<>();
        NodeList nodeList = document.getElementsByTagName("*");
        List<Integer> integerList;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                int l = node.getAttributes().getLength();
                for (int j = 0; j < l; j++) {
                    NamedNodeMap namedNodeMap = node.getAttributes();
                    if (containsHanScript(namedNodeMap.item(j).getNodeValue())) {
                        if (mapList.get(namedNodeMap.item(j).getNodeName()) == null) {
                            integerList = new ArrayList<>();
                        } else {
                            integerList = mapList.get(namedNodeMap.item(j).getNodeName());
                        }
                        integerList.add(i + 2);
                        mapList.put(namedNodeMap.item(j).getNodeName(), integerList);
                    }
                }
            }
        }
        return mapList;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FindFileHaveChinese frame = new FindFileHaveChinese();
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
    public FindFileHaveChinese() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelControl = new JPanel();
        contentPane.add(panelControl, BorderLayout.NORTH);

        JButton btnChooseLocation = new JButton("Choose Folder");
        btnChooseLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileDialog = new JFileChooser();
                fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileDialog.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileLocation = fileDialog.getSelectedFile();
                }
            }
        });
        panelControl.add(btnChooseLocation);
        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<File> listOfFiles = listf(fileLocation.getPath());
                GridBagConstraints textFieldConstraints = new GridBagConstraints();
                textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
                textFieldConstraints.weightx = 1;
                textFieldConstraints.weighty = 1;
                JTextArea jTextArea = new JTextArea();
                try {
                    for (File file : listOfFiles) {
                        if (file.isFile() && common.getFileExtension(file).equals(".xml")) {
                            document = common.parseXML(file.getPath());
                            if (null != document) {
                                Map<String, List<Integer>> listDistinct = getListAttribute();
                                if (listDistinct.size() > 0) {
                                    jTextArea.append(file.getPath() + "  -<"+listDistinct.size()+">-  " + listDistinct + "\n");
                                    textFieldConstraints.gridx = 0;
                                    textFieldConstraints.gridy = countFile++;
                                    panelInfo.add(jTextArea, textFieldConstraints);
                                    panelInfo.updateUI();
                                }
                            }
                        }
                    }
                    lblMessage.setText("Count: " + countFile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    lblMessage.setText("error!");
                }
            }
        });
        panelControl.add(btnFind);

        JButton btnReset = new JButton("reset");
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelInfo.removeAll();
                lblMessage.setText("");
                fileLocation = null;
                countFile = 0;
                panelControl.updateUI();
            }
        });
        panelControl.add(btnReset);

        panelInfo = new JPanel(new GridBagLayout());
        JScrollPane scrollBar = new JScrollPane(panelInfo,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollBar, BorderLayout.CENTER);

        JPanel panelMessage = new JPanel();
        contentPane.add(panelMessage, BorderLayout.SOUTH);

        lblMessage = new JLabel("");
        panelMessage.add(lblMessage);
    }

}
