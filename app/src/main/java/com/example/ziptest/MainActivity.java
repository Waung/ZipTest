package com.example.ziptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_content;
    Button b_save, b_unzip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        et_name = (EditText) findViewById(R.id.et_name);
        et_content = (EditText) findViewById(R.id.et_content);
        b_save = (Button) findViewById(R.id.b_save);
        b_unzip = (Button) findViewById(R.id.b_unzip);

        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = et_name.getText().toString();
                String content = et_name.getText().toString();

                saveTextAsFile(filename, content);
            }
        });

        b_unzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = et_name.getText().toString();

                unzipFolder(filename);
            }
        });
    }

    private void saveTextAsFile(String filename, String content) {
        String zipname = filename + ".zip";
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), zipname));

            // Folder to add
            File folderToAdd = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Zip/");

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();

            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            // Set the encryption flag to true
            // If this is set to false, then the rest of encryption properties are ignored
            parameters.setEncryptFiles(true);

            // Set the encryption method to Standard Zip Encryption
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);

            // Set password
            parameters.setPassword("123456");

            // Add folder to the zip file
            zipFile.addFolder(folderToAdd, parameters);
            FileUtils.deleteDirectory(folderToAdd);

        } catch (ZipException | IOException e) {
            e.printStackTrace();
        }
//        String fileName = filename + ".txt";
//
//        //create file
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
//
//        //write to file
//        try{
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(content.getBytes());
//            fos.close();
//            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Error Saving!", Toast.LENGTH_SHORT).show();
//        }

//        //create zip
//        String fileZiptest = filename + ".zip";
//
//        ZipOutputStream outputStream = null;
//        InputStream inputStream = null;
//
//        //write to zip
//        try {
//            File folderToAdd = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Zip/");
//            ArrayList filesToAdd = new ArrayList();
////            filesToAdd.add(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Zip/" + fileName));
//            filesToAdd.add(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Zip/a1.JPG"));
//
//            outputStream = new ZipOutputStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileZiptest)));
//            ZipParameters parameters = new ZipParameters();
//            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//            parameters.setEncryptFiles(true);
//            parameters.setRootFolderInZip("Zip/");
//            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
//            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
//            parameters.setPassword("123456");
//            for (int i = 0; i < filesToAdd.size(); i++) {
//                File coba = (File)filesToAdd.get(i);
//                outputStream.putNextEntry(coba,parameters);
////                outputStream.putNextEntry(folderToAdd,parameters);
//                if (coba.isDirectory()) {
//                    outputStream.closeEntry();
//                    continue;
//                }
//
//                //Initialize inputstream
//                inputStream = new FileInputStream(coba);
//                byte[] readBuff = new byte[4096];
//                int readLen = -1;
//
//                //Read the file content and write it to the OutputStream
//                while ((readLen = inputStream.read(readBuff)) != -1) {
//                    outputStream.write(readBuff, 0, readLen);
//                }
//
//                outputStream.closeEntry();
//
//                inputStream.close();
//            }
//
//            //ZipOutputStream now writes zip header information to the zip file
//            outputStream.finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private void unzipFolder(String filename) {
        String unzipname = filename + ".zip";
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), unzipname));

            File deletezip = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), unzipname);

            // Check to see if the zip file is password protected
            if (zipFile.isEncrypted()) {
                // if yes, then set the password for the zip file
                zipFile.setPassword("123456");
            }

            // Get the list of file headers from the zip file
            List fileHeaderList = zipFile.getFileHeaders();

            // Loop through the file headers
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
                // Extract the file to the specified destination
                zipFile.extractFile(fileHeader, Environment.getExternalStorageDirectory().getAbsolutePath());
            }

            deletezip.delete();

        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}