package com.mycompany.azureblobstorage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiri
 */

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ws.rs.WebApplicationException;

public class AzureService {
    @Resource
    private SessionContext ctx;
    
    private  BlobServiceClient getBlobServiceClient() {
        System.out.println("Establish BlobServiceClient");
        try {
            // Retrieve the connection string for use with the application. 
             String connectStr = "DefaultEndpointsProtocol=https;AccountName=bluehookdocuments;AccountKey=WbQ6YXbemnGM2aZLZuU3bp4dMn3nCszw20ahtPhZPEAELytN51LgJadU0onS3xRX5ci8w2QMMZgi+AStNNsgzQ==;EndpointSuffix=core.windows.net";
            // Create a BlobServiceClient object using a connection string
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectStr)
                .buildClient();
            System.out.println("blobServiceClient" + blobServiceClient);
            return  blobServiceClient;
        } catch (Exception e) {
            throw new WebApplicationException("Error in BlobServiceClient:\t" + e);
        }
    }
     
    private BlobContainerClient getBlobContainerClient(String containerName) {
        System.out.println("Establish BlobContainerClient");
        try {
            BlobServiceClient blobServiceClient = getBlobServiceClient();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            return blobContainerClient;
        } catch (Exception e) {
            throw new WebApplicationException("Error in BlobContainerClient:\t" + e);
        }
    }
    
    public ArrayList<String> listContainers() {
        System.out.println("Get blob container list");
        ArrayList<String> containerList = new ArrayList<>();
        try {
            BlobServiceClient blobServiceClient = getBlobServiceClient();
            for (BlobContainerItem blobContainerItem : blobServiceClient.listBlobContainers(null, null)) {
                System.out.printf("Container name: %s%n", blobContainerItem.getName());
                containerList.add(blobContainerItem.getName());
            }
        } catch (Exception e) {
            throw new WebApplicationException("Error in getting container list:\t" + e);
        }
        return containerList;
    }
    
    public BlobContainerClient createBlobStorageContainer(String containerName) {
         System.out.println("Create blob storage container");
         try {
            BlobServiceClient blobServiceClient = getBlobServiceClient();
            BlobContainerClient blobContainerClient = blobServiceClient.createBlobContainer(containerName.toLowerCase());
            return blobContainerClient;
        } catch (Exception e) {
            throw new WebApplicationException("Error in creating new container:\t" + e);
        }
    }
    
    public ArrayList<String> listFiles() {
        System.out.println("Get blob file list");
        ArrayList<String> fileList = new ArrayList<>();
        try {
            BlobContainerClient container = getBlobContainerClient("quickstartblobs");
            for (BlobItem blobItem : container.listBlobs()) {
            System.out.println("\t" + blobItem.getName());
            fileList.add(blobItem.getName());
        }
        } catch (Exception e) {
            throw new WebApplicationException("Error in getting file list:\t" + e);
        }
        
        return  fileList;
    }
    
    public ByteArrayOutputStream downloadBlobStorageFile(String blobitem) {
        System.out.println("Download file:\t " + blobitem + " from blob storage");
        BlobContainerClient container = getBlobContainerClient("quickstartblobs");
        
        BlobClient blobClient = container.getBlobClient(blobitem);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        blobClient.downloadStream(os);
        
        return os;
    }
    
     public String uploadBlobStorageFile(String fileName, byte[] fileBytes) {
        System.out.println("Upload file:\t" + fileName + " to blob storage");
        BlobClient client = getBlobContainerClient("quickstartblobs").getBlobClient(fileName);    
        if (client.exists()) {
            System.out.println("File exist on Azure");
            return fileName + "File Exits";
        } else {
            try {
                ByteArrayInputStream dataStream = new ByteArrayInputStream(fileBytes);
                client.upload(dataStream);
                String fileUrl = client.getBlobUrl();
                System.out.println("fileUrl: " + fileUrl);
            } catch (Exception e) {
                System.out.println("Upload error: " + e);
                throw new WebApplicationException("Error in uploading file: " + fileName + " error:\t" + e);
            }
            
        }
        return fileName + " File Uploaded";
    }
    
    public String deleteBlobStorageFile(String fileName) {
        System.out.println("Delete file:\t" + fileName + " from blob storage");
        BlobClient client = getBlobContainerClient("quickstartblobs").getBlobClient(fileName);  
        boolean fileDeleted = false;
        try {
            fileDeleted = client.deleteIfExists();
        } catch (Exception e) {
            throw new WebApplicationException("Error in deleting file:\t" + fileName + " error:\t" + e);
        }
        
        if (!fileDeleted) {
            return fileName + " Not deleted!";
        }
        
        return fileName + " Has been deleted";
    }
}
