package com.biz.framework.file;


import com.biz.framework.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileMapper fileMapper;

    @Value("${biz.file.storage.allowed-extensions}")
    private String allowedExtensions;

    @Value("${biz.file-size}")
    private Long maxFileSize;

    public String saveFile(String fileId, List<MultipartFile> multipartFiles) {
        String result = "";
        if (!CollectionUtils.isEmpty(multipartFiles)) {

            // 파일ID 생성
            if (fileId == null || fileId.trim().isEmpty()) {
                fileId = UUID.randomUUID().toString().replace("-", "");
            }

            for (MultipartFile multipartFile : multipartFiles) {
                if (multipartFile == null || multipartFile.isEmpty()) {
                    continue;
                }

                String originalFileName = multipartFile.getOriginalFilename();

                // 확장자 가져오기
                String extension = FilenameUtils.getExtension(originalFileName);
                if (!allowedExtensions.contains(extension.toLowerCase())) {
                    throw new ServiceException("허용되지 않은 확장자 파일입니다.");
                }

                // 파일 허용 크기 validation
                if (multipartFile.getSize() > maxFileSize) {
                    long maxFileSizeInMB = maxFileSize / (1024 * 1024);
                    throw new ServiceException("파일 크기는 " + maxFileSizeInMB + "MB 이하이어야 합니다.");
                }

                // dto 생성
                FileDto fileDto = new FileDto();
                fileDto.generateFileDto(multipartFile, fileId, originalFileName, extension);

                // 저장
                try {
                    // 폴더 경로 생성
                    String fileStreCours = fileDto.getFileStreFullCours();
                    Path path = Paths.get(fileStreCours);
                    Files.createDirectories(path.getParent());

                    Files.copy(multipartFile.getInputStream(), path);

                    fileMapper.saveFile(fileDto);
                } catch (Exception e) {
                    Path deleteTargetFilePath = Paths.get(fileDto.getFileStreCours(), fileDto.getStreFileNm());
                    try {
                        Files.deleteIfExists(deleteTargetFilePath);
                    } catch (IOException ioe) {
                        throw new ServiceException(ioe.getMessage());
                    }
                    throw new ServiceException("파일 생성 중 오류가 발행했습니다.");
                }
            }
            result = fileId;
        }
        return result;
    }

    public List<FileDto> findByFileId(String atchFileId) {
        FileDto fileDto = new FileDto(atchFileId, "Y");
        return fileMapper.findByAtchFileIdAndUseAt(fileDto);
    }

    public FileDto loadByFileSn(String fileId, String fileSn) {
        FileDto fileDto = new FileDto(fileId, fileSn, "Y");
        return fileMapper.findByATchfileIdAndFileSnAndUseAt(fileDto);
    }

    public int remove(String fileId, String fileSn) {
        int result = 0;

        FileDto fileDto = new FileDto(fileId, fileSn, "Y");
        FileDto findFileInfo = fileMapper.findByATchfileIdAndFileSnAndUseAt(fileDto);
        String uploadDir = findFileInfo.getFileStreCours();
        String fileName = findFileInfo.getStreFileNm();
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        try {
            Files.deleteIfExists(filePath);
            result += fileMapper.removeFile(fileDto);
        } catch (IOException e) {
            throw new ServiceException("파일 삭제 실패: " + filePath);
        }
        return result;
    }
}
