package com.example.service.storage;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	  
//	void store(MultipartFile file);

	void store(MultipartFile file,String id);

	Resource getFile(String filename,String id);
	
	Resource getFile(String filename);
	
	List<String> getFileNames(String id);
	
}
