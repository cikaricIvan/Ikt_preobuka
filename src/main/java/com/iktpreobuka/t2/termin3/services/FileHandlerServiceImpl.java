package com.iktpreobuka.t2.termin3.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {
	private static String UPLOAD_FOLDER ="D:\\SLike\\";

	@Override
	public String simpleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
	if(file.isEmpty()) {
		redirectAttributes.addFlashAttribute("message","Please select a file to upload");
		return "redirect:uploadStatus";
	}
	try {
		byte[] bytes=file.getBytes();
		Path path=Paths.get(UPLOAD_FOLDER +file.getOriginalFilename());
		Files.write(path, bytes);
		redirectAttributes.addFlashAttribute("message","you seccsessfully" +file.getOriginalFilename());
	} catch (IOException e) {
		
		e.printStackTrace();
	}
		return UPLOAD_FOLDER ="D:\\SLike\\";
	}

}
