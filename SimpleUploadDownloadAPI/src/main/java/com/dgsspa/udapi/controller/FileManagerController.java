package com.dgsspa.udapi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("/*")
public class FileManagerController {

	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> fileUploading(@RequestPart("file") MultipartFile file) {
		// Code to save the file to a database or disk
		return ResponseEntity.ok("Successfully uploaded the file");
	}

	@GetMapping(path = "/downloadTest", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public ResponseEntity<Byte[]> fileGenericoDownload() throws IOException {
		// Simula la logica di elaborazione del file (puoi sostituire questa parte con
		// la tua logica)
		File zippo = new File("C:\\temp\\stupidofile.txt");
		Byte[] fileContent = null;
		String filename = "";
		if (zippo.exists()) {
			InputStream in = new FileInputStream(zippo);
			return new ResponseEntity(org.apache.commons.io.IOUtils.toByteArray(in), HttpStatus.OK);

		}

		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "/downloadFile", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public ResponseEntity<Resource> fileDownload(HttpServletRequest request) throws IOException {
		// Simula la logica di elaborazione del file (puoi sostituire questa parte con
		// la tua logica)
		File zippo = new File("C:\\temp\\stupidofile.txt");
		Byte[] fileContent = null;
		String filename = "";
		if (zippo.exists()) {
			
			// Load file as Resource
			Resource resource = new UrlResource(zippo.toURI());

			// Try to determine file's content type
			String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}

		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

}
