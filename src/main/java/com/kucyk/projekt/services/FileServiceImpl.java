package com.kucyk.projekt.services;

import com.kucyk.projekt.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@PropertySource("config.properties")
@Service("fileService")
public class FileServiceImpl
{
    @Autowired
    private FileRepository fileRepository;

    @Value("${files.location}")
    private String photoDir;

    public void saveFile(MultipartFile multipartFile, String name) throws IOException {
        new File(photoDir+"logos/").mkdirs();
        var path = Path.of(photoDir+"logos/", name+".png");
        var fos = new FileOutputStream(path.toFile());
        fos.write(multipartFile.getBytes());
        fos.close();
    }
}
