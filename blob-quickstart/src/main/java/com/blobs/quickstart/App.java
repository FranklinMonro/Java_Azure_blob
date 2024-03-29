package com.blobs.quickstart;

/**
 * Azure Blob Storage quick start
 */
import com.azure.identity.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;

public class App
{
    public static void main(String[] args) throws IOException
    {
        // Quickstart code goes here
        // Retrieve the connection string for use with the application. 
        String connectStr = "";
        
        // Create a BlobServiceClient object using a connection string
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .connectionString(connectStr)
            .buildClient();
        
        // Create a unique name for the container
        String containerName = "quickstartblobs";
        
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
        
        // List the blob(s) in the container.
        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            System.out.println("\t" + blobItem.getName());
        }
//        // Create the container and return a container client object
//        BlobContainerClient blobContainerClient = blobServiceClient.createBlobContainer(containerName);
//        
//        // Create a local file in the ./data/ directory for uploading and downloading
        String localPath = "./data/";
        String fileNameString = "Generator.docx";
//        String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
//
//        // Get a reference to a blob
        BlobClient blobClient = blobContainerClient.getBlobClient(fileNameString);
//
//        // Write text to the file
//        FileWriter writer = null;
//        try
//        {
//            writer = new FileWriter(localPath + fileName, true);
//            writer.write("Hello, World!");
//            writer.close();
//        }
//        catch (IOException ex)
//        {
//            System.out.println(ex.getMessage());
//        }
//
//        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
//
//        // Upload the blob
//        blobClient.uploadFromFile(localPath + fileName);
//        
//        System.out.println("\nListing blobs...");
//
//        // List the blob(s) in the container.
//        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
//            System.out.println("\t" + blobItem.getName());
//        }
//        
        // Download the blob to a local file
        
        
        // Append the string "DOWNLOAD" before the .txt extension for comparison purposes
        String downloadFileName = fileNameString.replace(".txt", "DOWNLOAD.txt");

        System.out.println("\nDownloading blob to\n\t " + localPath + downloadFileName);

        blobClient.downloadToFile(localPath + downloadFileName);
    }
}
