package com.holdme.holdmeapi_restfull.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void inii();

    String store(MultipartFile file);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(String filename);
}
