package com.example.service;

import java.io.File;
import java.util.List;


public interface HtmlService {
	public String generateHtmlString(List<String> imageFilePaths);
	public File getHtmlFile(List<String> imageFilePaths, String fileName);
}
