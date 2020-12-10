package com.example.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface PdfGeneraterService {
	String generatePdf(String id) throws IllegalArgumentException, FileNotFoundException, IOException, URISyntaxException;
}
