package com.azure.init.domain;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ReadImage {

	private String img_url;

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
}