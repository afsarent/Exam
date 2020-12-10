package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.example.Application;
import com.example.exception.QRCodeAlreadyExistsException;
import com.example.model.QRCode;
import com.example.repository.QRCodeRepository;
import com.example.service.storage.FileHandlerService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;



@Service
public class QRCodeService {
	public static final String POINT_LINK = Application.BASE_URL;//"www.entrar.com";
	public static final int QR_CODE_SIZE = 256;
	
	@Autowired
	private QRCodeRepository repository;
	
	@Autowired
	private FileHandlerService fileHandler;
	
	public QRCode generateQRCode(String id) throws QRCodeAlreadyExistsException, WriterException,IOException {
		if(repository.existsById(id)) {
			throw new QRCodeAlreadyExistsException("QR Code already exists for this id : "+id);
		}
		Path link =  Paths.get(fileHandler.getLocation() + "/" +id+".png");//ConverterModuleApplication.rootPath+"/"+id+".png";
		File file = link.toFile();
		String fileType = "png";
		try {
			String qrData = POINT_LINK+"/upload.html?id="+id;
			createQRImage(file, qrData, QR_CODE_SIZE, fileType);
		} catch (WriterException | IOException e) {
			System.out.print("Error generating qr code");
			e.printStackTrace();
			throw e;
		}
		QRCode code = new QRCode();
		code.setId(id);
		code.setLink(link.getFileName().toString());
		repository.save(code);
		return code;
	}
	
	
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}
	
}
