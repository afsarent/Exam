package com.example.service.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.ResourceNotFoundException;

@Service
public class FileHandlerService implements FileStorageService {

	@Value("${storage.location.folder.name}")
	private String location;

	Logger logger = LoggerFactory.getLogger(FileHandlerService.class);
	
	@Override
	public Resource getFile(String fileName) {
		logger.debug("FileHandlerService : getFileNames()");
		Path file = Paths.get(location,fileName);
		Resource resource = null;
		try {
			resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				logger.error("FileHandlerService : File is Not Available.");
				throw new ResourceNotFoundException("File is not available or permission denied.");
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}
	@Override
	public Resource getFile(String filename,String id) {
		logger.debug("FileHandlerService : getFileNames()");
		Path file = Paths.get(location+"/"+id, filename);
		Resource resource = null;
		try {
			resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				logger.error("FileHandlerService : File is Not Available.");
				throw new ResourceNotFoundException("File is not available or permission denied.");
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getLocation() {
		return this.location;
	}
	
	@Override
	public List<Path> getFiles() {
		logger.debug("FileHandlerService : getFiles()");
		List<Path> files = new ArrayList<>();
		try {
			Files.walk(Paths.get(location+"/"+121)).filter(Files::isRegularFile).forEach(path -> files.add(path));

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return files;
	}
	public List<Path> getFiles(String id) {
		logger.debug("FileHandlerService : getFiles()");
		List<Path> files = new ArrayList<>();
		try {
			Files.walk(Paths.get(location+"/"+id)).filter(Files::isRegularFile).forEach(path -> files.add(path));

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return files;
	}
	
	@Override
	public List<String> getFileNames(String id) {
		logger.debug("FileHandlerService : getFileNames()");
		List<String> fileNames = new ArrayList<>();
		try {
			getFiles(id).forEach(path -> fileNames.add(path.getFileName().toString()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return fileNames;
	}
	
	@Override
	public void store(MultipartFile file,String id) {
		
		String fileName = String.valueOf(System.currentTimeMillis())+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		logger.debug("FileHandlerService : store() " + fileName);
		
		try (InputStream inputStream = file.getInputStream()) {
			 File directory = new File(location + "/" + id);
			    if (!directory.exists()){
			    	directory.mkdir();
			    }
			Files.copy(inputStream, Paths.get(location + "/" + id + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to store file " + fileName, e);
		}
	}
}
