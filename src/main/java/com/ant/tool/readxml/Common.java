package com.ant.tool.readxml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Common {

    public Document parseXML(String filePath) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(filePath);
            doc.getDocumentElement().normalize();
        } catch (Exception e){
            String path = "listFilesException.txt";
            File file = new File(path);
            if (!file.exists()){
                file.createNewFile();

            }
            BufferedWriter writer = new BufferedWriter(  new FileWriter(path, true));
            writer.newLine();   //Add new line
            writer.write(filePath+" - Create time: "+ new Date());
            writer.close();
        }
        return doc;
    }

    public String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

    public File getAndValidateFile() {
        File file = null;
        JFileChooser fileDialog = new JFileChooser();
        int returnVal = fileDialog.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileDialog.getSelectedFile();
        }
        return file;
    }
}
