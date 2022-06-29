package com.azure.init.service;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import com.azure.init.domain.ReadImage;
import com.microsoft.azure.cognitiveservices.vision.computervision.*;
import com.microsoft.azure.cognitiveservices.vision.computervision.implementation.ComputerVisionImpl;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.*;
import org.springframework.stereotype.Service;

@Service
public class ReadImageService {

	private static String subscription_key = "";
	private static String end_point = "";

	public static ComputerVisionClient credentials(String subscriptionKey, String endpoint) {
		return ComputerVisionManager.authenticate(subscriptionKey).withEndpoint(endpoint);
	}

	public String sendFromUrl(ReadImage image) throws InterruptedException {

		System.out.println("\nAzure Cognitive Services Computer Vision");

		ComputerVisionClient client = credentials(subscription_key, end_point);

		return readFromUrl(client, image.getImg_url());
	}
	
	public String sendFromLocal(ReadImage image) throws InterruptedException, IOException {
		
		System.out.println("\nAzure Cognitive Services Computer Vision");

		ComputerVisionClient client = credentials(subscription_key, end_point);
		
		System.out.println(readFromLocal(client, image.getImg_url()));
		return readFromLocal(client, image.getImg_url());
	}

	private String readFromLocal(ComputerVisionClient client, String url) throws InterruptedException, IOException {

		String remoteTextImageURL = url;
		ComputerVisionImpl vision = (ComputerVisionImpl) client.computerVision();

		File rawImage = new File(remoteTextImageURL);
		byte[] imageByteArray = Files.readAllBytes(rawImage.toPath());

		ReadInStreamHeaders responseHeader = vision.readInStreamWithServiceResponseAsync(imageByteArray, null)
				.toBlocking().single().headers();

		String operationLocation = responseHeader.operationLocation();
		System.out.println("Operation Location:" + operationLocation);

		String rest = getResult(vision, operationLocation);

		return rest;
	}

	private String readFromUrl(ComputerVisionClient client, String url) throws InterruptedException {

		String remoteTextImageURL = url;
		ComputerVisionImpl vision = (ComputerVisionImpl) client.computerVision();

		ReadHeaders responseHeader = vision.readWithServiceResponseAsync(remoteTextImageURL, null).toBlocking().single()
				.headers();

		String operationLocation = responseHeader.operationLocation();
		System.out.println("Operation Location:" + operationLocation);

		String rest = getResult(vision, operationLocation);

		return rest;
	}

	private static String extractOprationId(String operationLocation) {
		if (operationLocation != null && !operationLocation.isEmpty()) {
			String[] split = operationLocation.split("/");

			if (split != null && split.length > 0) {
				return split[split.length - 1];
			}
		}
		throw new IllegalStateException(
				"Algo salió mal: No es posible extraer la operación desde la locación especificada.");
	}

	public String getResult(ComputerVision vision, String operationLocation) throws InterruptedException {
		System.out.println("Imprimiendo resultado ...");

		String operationId = extractOprationId(operationLocation);
		boolean pollForResult = true;
		ReadOperationResult readResult = null;

		while (pollForResult) {
			Thread.sleep(1000);
			readResult = vision.getReadResult(UUID.fromString(operationId));

			if (readResult != null) {
				OperationStatusCodes status = readResult.status();

				if (status == OperationStatusCodes.FAILED || status == OperationStatusCodes.SUCCEEDED) {
					pollForResult = false;
				}
			}
		}
		for (ReadResult pageResult : readResult.analyzeResult().readResults()) {
			System.out.println("");
			System.out.println("Printing Read results for page " + pageResult.page());
			StringBuilder builder = new StringBuilder();
			for (Line line : pageResult.lines()) {
				builder.append(line.text());
				builder.append("\n");
			}
			System.out.println(builder.toString());
			return builder.toString();
		}
		throw new IllegalStateException("Algo salió mal: No se pudo realizar la acción.");
	}
}