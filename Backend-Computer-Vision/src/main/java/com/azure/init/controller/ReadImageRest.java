package com.azure.init.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.azure.init.domain.ReadImage;
import com.azure.init.service.ReadImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReadImageRest {

	@Autowired
	private ReadImageService service;

	@PostMapping("/url")
	public ResponseEntity<Object> sendImageUrl(@RequestBody ReadImage image) throws InterruptedException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("text", service.sendFromUrl(image));
		return ResponseEntity.ok(response);
	}

	@PostMapping("/local")
	public ResponseEntity<Object> sendImageLocal(@RequestBody ReadImage image) throws InterruptedException, IOException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("text", service.sendFromLocal(image));
		return ResponseEntity.ok(response);
	}
}