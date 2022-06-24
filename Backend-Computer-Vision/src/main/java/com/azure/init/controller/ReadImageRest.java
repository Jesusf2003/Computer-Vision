package com.azure.init.controller;

import com.azure.init.domain.ReadImage;
import com.azure.init.service.ReadImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReadImageRest {
	
	@Autowired
	private ReadImageService service;
	
	@PostMapping
	public String sendImageUrl(@RequestBody ReadImage image) throws InterruptedException {
		return service.sendFromUrl(image);
	}
}