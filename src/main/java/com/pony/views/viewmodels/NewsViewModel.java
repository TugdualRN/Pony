package com.pony.views.viewmodels;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.*;

public class NewsViewModel {

	@NotNull
	@Size(min = 3, max = 100)
	private String title;

	@NotNull
	@Size(min = 10)
	private String content;

	@NotNull
	@Size(min = 3, max = 250)
	private String description;

	private String img;

	public void setImg( MultipartFile myFile){
		InputStream inputStream = null;
		OutputStream outputStream = null;

		String fileName = myFile.getOriginalFilename().replace(' ', '_');
		String imgfilePath = "/images/imported_files/";
		File newFile = new File("src/main/resources/static" + imgfilePath + fileName);
		try {
			inputStream = myFile.getInputStream();

			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[5096];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.img = imgfilePath + fileName;
	}

	public String getImg() {
		return img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}