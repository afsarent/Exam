package com.example.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.HtmlGenerateException;
import com.example.service.storage.FileHandlerService;

@Service
public class HtmlServiceImpl implements HtmlService{

	public static final String header = "<html>\n"
			+ "<head>\n"
			+ "<title>Exam sheet</title>\n"
			+ "<style>\n"
			+ "ul {\n"
			+ "  display: table;\n"
			+ "  margin: 0 auto;\n"
			+ "}\n"
			+ "</style>\n"
			+ "</head>\n"
			+ "<body>\n"
			+ "<ul>";
	public static final String footer = "</ul>\n"
			+ "\n"
			+ "</body>\n"
			+ "</html>";
	
	@Autowired
	private FileHandlerService fileHandler;
	
	@Override
	public String generateHtmlString(List<String> imageFilePaths)throws HtmlGenerateException {
		if(imageFilePaths.size()==0) {
			throw new HtmlGenerateException("File paths can not be empty");
		}
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(header);
			for(String img: imageFilePaths) {
				StringBuilder temp = new StringBuilder();
				temp.append("<li>");
				temp.append("<img src=\""+img+"\">");
				temp.append("</li>");
				builder.append(temp.toString());
			}
			builder.append(footer);
			return builder.toString();
		}catch(Exception e) {
			e.printStackTrace();
			throw new HtmlGenerateException(e.getLocalizedMessage());
		}
	}

	@Override
	public File getHtmlFile(List<String> imageFilePaths,String fileName) {
		try {
			Path link =  Paths.get(fileHandler.getLocation() + "/" +fileName+".html");//ConverterModuleApplication.rootPath+"/"+id+".png";
			File file = link.toFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(generateHtmlString(imageFilePaths).getBytes());
			out.close();
			return file;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HtmlGenerateException(e.getLocalizedMessage());
		}
	}

}
