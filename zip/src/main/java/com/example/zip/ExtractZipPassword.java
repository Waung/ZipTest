package com.example.zip;

import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class ExtractZipPassword {

    public ExtractZipPassword() {

        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile("c:\\ZipTest\\AddFolderPassword.zip");

            // Check to see if the zip file is password protected
            if (zipFile.isEncrypted()) {
                // if yes, then set the password for the zip file
                zipFile.setPassword("123");
            }

            // Get the list of file headers from the zip file
            List fileHeaderList = zipFile.getFileHeaders();

            // Loop through the file headers
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
                // Extract the file to the specified destination
                zipFile.extractFile(fileHeader, "c:\\ZipTest\\");
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new ExtractZipPassword();

    }

}
