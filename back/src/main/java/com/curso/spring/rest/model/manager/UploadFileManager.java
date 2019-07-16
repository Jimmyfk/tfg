package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.services.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileManager implements UploadFileService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final static String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path ruta = getPath(filename);
        log.info("Ruta: " + ruta);
        Resource recurso = null;

        recurso = new UrlResource(ruta.toUri());
        if (!recurso.exists() || !recurso.isReadable()) {
            throw new RuntimeException("No se puede cargar la imagen " + filename);
        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path root = getPath(uniqueFileName);

        log.info("rootPath: " + root);
        Files.copy(file.getInputStream(), root);
        return uniqueFileName;
    }

    @Override
    public boolean delete(String filename) {
        Path rutaAbsoluta = getPath(filename);
        File imagen = rutaAbsoluta.toFile();

        if (imagen.exists() && imagen.canRead())
            return imagen.delete();
        return false;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }

    private Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
