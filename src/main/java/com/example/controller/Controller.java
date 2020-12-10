package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Application;
import com.example.model.QRCode;
import com.example.service.PdfGeneraterService;
import com.example.service.QRCodeService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/gen")
public class Controller {

	@Autowired
	private QRCodeService service;
	
	@Autowired
	private PdfGeneraterService pdf;
	@GetMapping("ktest")
	public ResponseEntity<?> generateTest(){
		return ResponseEntity.ok("Test pass");
	}
	
	@GetMapping("qr")
	public ResponseEntity<?> generate(@RequestParam("id")String id){
		try {
			QRCode code = service.generateQRCode(id);
			String fileUrl = Application.BASE_URL+"/file/"+code.getLink();
			return new ResponseEntity<String>(fileUrl,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(""+e.toString());
		}
	}
	@GetMapping("pdf")
	public ResponseEntity<?> generatePdf(@RequestParam("pdf")String id){
		try {
			String name = pdf.generatePdf(id);
			return new ResponseEntity<String>(name,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(""+e.toString());
		}
	}
	
}
