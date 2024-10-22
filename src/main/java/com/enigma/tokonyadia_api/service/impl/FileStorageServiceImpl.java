package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.constant.FileType;
import com.enigma.tokonyadia_api.dto.res.FileInfo;
import com.enigma.tokonyadia_api.service.FileStorageService;
import com.enigma.tokonyadia_api.util.LogUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Integer MAX_SIZE;
    private final Path directoryPath;

    @Autowired
    public FileStorageServiceImpl(
            @Value("${app.tokonyadia.root-path-directory}") String directoryPath,
            @Value("${app.tokonyadia.file-max-size}") Integer maxSize
    ) {
        MAX_SIZE = maxSize;
        this.directoryPath = Paths.get(directoryPath).normalize();
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
                Files.setPosixFilePermissions(directoryPath, PosixFilePermissions.fromString("rwxr-x-r-x"));
            } catch (IOException e) {
                LogUtil.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.ERROR_WHILE_CREATING_DIRECTORY);
            }
        }
    }

    @Override
    public FileInfo storeFile(FileType fileType, String prefixDirectory, MultipartFile multipartFile, List<String> contentTypes) {
        try {
            validate(multipartFile, contentTypes);
            String prefix = fileType.equals(FileType.FILE) ? "files" : "images";

            String filename = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

            Path dirPath = directoryPath.resolve(prefix).normalize();
            Path newDirPath = dirPath.resolve(prefixDirectory).normalize();

            if (!Files.exists(newDirPath)) {
                Files.createDirectories(newDirPath);
            }

            Path filePath = newDirPath.resolve(filename).normalize();
            Files.copy(multipartFile.getInputStream(), filePath);
            Files.setPosixFilePermissions(filePath, PosixFilePermissions.fromString("rw-r--r--"));

            Path savedPath = Paths.get(prefix).resolve(prefixDirectory).resolve(filename).normalize();

            return FileInfo.builder()
                    .filename(filename)
                    .path(savedPath.toString())
                    .build();
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.ERROR_WHILE_SAVE_FILE);
        }
    }

    @Override
    public Resource readFile(String path) {
        try {
            Path filePath = directoryPath.resolve(path);
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_FILE_NOT_FOUND);
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void deleteFile(String path) {
        try {
            Path filePath = directoryPath.resolve(path);
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_FILE_NOT_FOUND);
            Files.delete(filePath);
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void validate(MultipartFile multipartFile, List<String> contentTypes) {
        if (multipartFile.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image cannot be empty");

        if (multipartFile.getOriginalFilename() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "filename cannot be empty");

        if (multipartFile.getSize() > MAX_SIZE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file size exceed limit");
        }

        if (!contentTypes.contains(multipartFile.getContentType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid extensions type");
        }
    }
}