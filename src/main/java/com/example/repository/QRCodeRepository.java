package com.example.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.QRCode;

public interface QRCodeRepository extends MongoRepository<QRCode, String> {

	
}
