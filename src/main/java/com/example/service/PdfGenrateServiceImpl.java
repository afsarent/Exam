package com.example.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Data;
import com.example.service.storage.FileHandlerService;
import com.itextpdf.html2pdf.HtmlConverter;

@Service
public class PdfGenrateServiceImpl implements PdfGeneraterService{
	
	@Autowired
	private FileHandlerService fileService;
	
	@Autowired 
	private HtmlService html;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public String generatePdf(String id) throws IllegalArgumentException, FileNotFoundException, IOException, URISyntaxException{
		List<String> images = new ArrayList<String>();
		Path link =  Paths.get(fileService.getLocation() + "/" +id);//ConverterModuleApplication.rootPath+"/"+id+".png";
		File file = link.toFile();
		System.out.println("File status : "+file.exists());
		System.out.println("Directory status : "+file.isDirectory());
		if(file.exists() && file.isDirectory()) {
			File files[] = file.listFiles();
			for(File f:files) {
				String path = f.getAbsolutePath();
				if(path.endsWith(".png") || path.endsWith(".jpg")) {
					images.add(path);
				}
			}
			String htmlString = html.generateHtmlString(images);
			Path linkpdf =  Paths.get(fileService.getLocation() + "/" +id+".pdf");
			File outfile = linkpdf.toFile();//html.getHtmlFile(images, id);
			HtmlConverter.convertToPdf(htmlString, new FileOutputStream(outfile));	
			
			FileInputStream input = new FileInputStream(outfile);
			MultipartFile multipartFile = new MockMultipartFile("file",outfile.getName(), "text/plain", IOUtils.toByteArray(input));
			
			// call api to send pdf file
			String[] ids = id.split("-");
			URI uri;
		      uri = new URI("https://entrar.in/ST87587b451a3b6d91c193cb29e6786338/save_student_ans_sheet.php");

		      Data data =new Data();
		      data.setStudent_id(ids[0]);
		      data.setClasstest_id(ids[1]);
		      data.setSchool_id(ids[2]);
		      data.setFile(multipartFile);
		      
		      HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity<Data> request = new HttpEntity<Data>(data,headers);
		      restTemplate.exchange(uri, HttpMethod.POST, request, Data.class).getBody();
			
			return outfile.getName();
		}
		else {
			System.out.println("No directory found for "+id);
			throw new IllegalArgumentException("No directory found for "+id);
		}
		
	}
	

}
