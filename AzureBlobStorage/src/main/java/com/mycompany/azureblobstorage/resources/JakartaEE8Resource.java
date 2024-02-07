package com.mycompany.azureblobstorage.resources;

import com.mycompany.azureblobstorage.AzureService;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
/**
 *
 * @author 
 */
@Path("blob.storage")
public class JakartaEE8Resource {
    private AzureService azureService;
    
    @GET
    @Path("blobcontainerlist")
    public ArrayList<String> containerList(){
        azureService = new AzureService();
        ArrayList<String> myAzureFiles = azureService.listContainers();
        return myAzureFiles;
    }
    
    @POST
    @Path("createblobcontainer/{containerName}")
    public boolean createContainer(@PathParam("containerName") String containerName) {
        azureService = new AzureService();
        azureService.createBlobStorageContainer(containerName);
        return false;
    }
    
    @GET
    @Path("blobfilelist")
    public ArrayList<String> fileList(){
        azureService = new AzureService();
        ArrayList<String> myAzureFiles = azureService.listFiles();
        return myAzureFiles;
    }
    
    @GET
    @Path("blobdownload/{fileName}")
    public byte[] fileDownload(@PathParam("fileName") String fileName){
        azureService = new AzureService();
        return azureService.downloadBlobStorageFile(fileName).toByteArray();
    }
    
    @POST
    @Path("blobupload")
    public String uploadFile(HashMap parameters){
        String fileName = (String) parameters.get("fileName");
        String file = (String) parameters.get("file");
        byte[] bytes = Base64.getDecoder().decode(file);
        azureService = new AzureService();
        return azureService.uploadBlobStorageFile(fileName, bytes);
    }
    
    @DELETE
    @Path("blobdelete/{fileName}")
    public String deleteFile(@PathParam("fileName") String fileName) {
        azureService = new AzureService();
        return azureService.deleteBlobStorageFile(fileName);
    }
   
}
