package com.example.UploadTgz;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UploadTgzApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadTgzApplication.class, args);
		
		try {
            File f = new File("D:\\ups3");

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    // We want to find only .c files
                    return name.endsWith(".tgz");
                }
            };

            // Note that this time we are using a File class as an array,
            // instead of String
            File[] files = f.listFiles(filter);

            int count = 0;
            // Get the names of the files by using the .getName() method
            for (int i = 0; i < files.length; i++) {
                //System.out.println(files[i].getName());
            	
            	UploadTgzApplication.run(files[i]);
            	count= count+1;
            	System.out.println(count);
            	if(count==7000) {
            		break;
            	}
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
	}

	private static void run(File file) throws IOException {
		
		
         /*Path tempFile = Files.createTempFile("upload-test-file", ".txt");
         Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
         System.out.println("uploading: " + tempFile);
         File file = tempFile.toFile();*/
		
         Resource res=new FileSystemResource(file);
         MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
         bodyMap.add("file",res );
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.MULTIPART_FORM_DATA);
         HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);



         RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<String> response = restTemplate.exchange("https://crashupload-backend.dev.rdk.yo-digital.com/v1/crashUpload",
         HttpMethod.POST, requestEntity, String.class);
         System.out.println("response status: " + response.getStatusCode());
         System.out.println("response body: " + response.getBody());
		
	}

	//@Override
	/*public void run()... args) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();         
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";    
		URI uri = new URI(baseUrl);         
		Employee employee = new Employee(null, "Adam", "Gilly", "test@email.com");     
		ResponseEntity<String> result = restTemplate.postForEntity(uri, employee, String.class);         //Verify request succeed    
		Assert.assertEquals(201, result.getStatusCodeValue());
		
	}*/
	
	

}
