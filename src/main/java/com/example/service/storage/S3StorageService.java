package com.example.service.storage;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class S3StorageService implements StorageService {

	@Override
	public void store(MultipartFile file,String id) {
	}

	@Override
	public Resource getFile(String key,String id) {
		return null;
	}

	@Override
	public List<String> getFileNames(String id) {
		return null;
	}

	@Override
	public Resource getFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
