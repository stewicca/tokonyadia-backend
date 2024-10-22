package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.constant.FileType;
import com.enigma.tokonyadia_api.dto.res.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    FileInfo storeFile(FileType fileType, String prefixDirectory, MultipartFile file, List<String> contentTypes);

    Resource readFile(String path);

    void deleteFile(String path);
}