package com.azure.init.service;

import java.util.UUID;

import com.azure.init.domain.ReadImage;
import com.microsoft.azure.cognitiveservices.vision.computervision.*;
import com.microsoft.azure.cognitiveservices.vision.computervision.implementation.ComputerVisionImpl;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.*;
import org.springframework.stereotype.Service;

@Service
public class ReadImageService {

	private static String subscription_key = "";
	private static String end_point = "";

	public String sendFromUrl(ReadImage image) throws InterruptedException {

		System.out.println("\nAzure Cognitive Services Computer Vision - Java Quickstart Sample");

		ComputerVisionClient client = Authenticate(subscription_key, end_point);
		
		System.out.println(ReadFromUrl(client, image.getImg_url()));

		return ReadFromUrl(client, image.getImg_url());
	}

	public String sendFromFile(ReadImage image) {
		return null;
	}

	public static ComputerVisionClient Authenticate(String subscriptionKey, String endpoint) {
		return ComputerVisionManager.authenticate(subscriptionKey).withEndpoint(endpoint);
	}

	private String ReadFromUrl(ComputerVisionClient client, String url) throws InterruptedException {

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