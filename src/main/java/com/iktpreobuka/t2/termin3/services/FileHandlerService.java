package com.iktpreobuka.t2.termin3.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileHandlerService {
  public String simpleFileUpload(MultipartFile file,RedirectAttributes redirectAttributes);
}
