package com.biz.framework.file;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    @GetMapping("/{fileId}/{fileSn}/view")
    public ResponseEntity<Resource> fileView(@PathVariable("fileId") String fileId, @PathVariable("fileSn") String fileSn) throws IOException {
        return viewInternal(fileService.loadByFileSn(fileId, fileSn));
    }

    @DeleteMapping("/{fileId}/{fileSn}")
    public int delete(@PathVariable("fileId") String fileId, @PathVariable("fileSn") String fileSn) {
        return fileService.remove(fileId, fileSn);
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

    private ResponseEntity<Resource> viewInternal(FileDto fileDto) throws IOException {
        if (fileDto == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(fileDto.getFileStreCours(), fileDto.getStreFileNm());
        Resource resource = new UrlResource(path.toUri());

        // 파일 확장자 기반 이미지 타입 추론
        String ext = FilenameUtils.getExtension(fileDto.getOriginalFileNm()).toLowerCase();
        MediaType mediaType = switch (ext) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.APPLICATION_OCTET_STREAM; // fallback
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .inline() // <-- 여기가 포인트
                .filename(fileDto.getOriginalFileNm(), StandardCharsets.UTF_8)
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileDto.getFileSize())
                .contentType(mediaType)
                .body(resource);
    }
}
