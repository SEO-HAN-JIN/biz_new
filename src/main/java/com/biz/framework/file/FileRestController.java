package com.biz.framework.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileRestController {

    private final FileService fileService;

    @PostMapping("/save")
    public String save(@RequestParam(required = false, name = "fileId") String fileId, @RequestParam("files") List<MultipartFile> files) {
        return fileService.saveFile(fileId, files);
    }

    @GetMapping("/{fileId}")
    public List<FileDto> download(@PathVariable("fileId") String fileId) {
        return fileService.findByFileId(fileId);
    }

    @GetMapping("/{fileId}/{fileSn}/download")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId, @PathVariable("fileSn") String fileSn) throws IOException {
        return downloadInternal(fileService.loadByFileSn(fileId, fileSn));
    }

    private ResponseEntity<Resource> downloadInternal(FileDto fileDto) throws IOException {

        try {

            Path filePath = Paths.get(fileDto.getFileStreCours(), fileDto.getStreFileNm());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition
                    .builder("attachment")
                    .filename(fileDto.getOriginalFileNm(), Charset.forName("utf-8"))
                    .build()
            );
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileDto.getFileSize())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
