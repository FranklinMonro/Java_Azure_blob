package com.mycompany.blobupload.resources;

import com.azure.core.http.rest.PagedResponse;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.azure.identity.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;

/**
 *
 * @author 
 */
// http://localhost:8080/BlobUpload/resources/azure.blob/
@Stateless
@Path("azure.blob")
public class JakartaEE8Resource {
    
    @GET
    public String ping(){
        System.out.println("\nListing blobs...");
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
        System.out.println("\nListing blobs...");
//        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
//            System.out.println("\t" + blobItem.getName());
//        }
          
        return "s";
//        return Response
//                .ok("ping")
//                .build();
    }
}
