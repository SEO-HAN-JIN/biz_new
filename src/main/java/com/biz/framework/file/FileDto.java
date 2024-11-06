package com.biz.framework.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Getter
@Setter
public class FileDto {
    private String fileSn;
    private String atchFileId;
    private String fileExtsn;
    private Long fileSize;
    private String fileStreCours;
    private String originalFileNm;
    private String streFileNm;
    private String useAt;
    private String fileStreFullCours;

    public void generateFileDto(MultipartFile multipartFile, String fileId, String originalFileNm, String extension) {
        this.fileSn = generateFileSn();
        this.atchFileId = fileId;
        this.fileExtsn = extension;
        this.fileSize = multipartFile.getSize();
        this.originalFileNm = getOriginalFileName(originalFileNm);
        this.streFileNm = atchFileId + "-" + fileSn + "." + fileExtsn;
        this.fileStreCours = generateStreCours();
        this.useAt = "Y";
        this.fileStreFullCours = Paths.get(fileStreCours, streFileNm).toString();
    }

    // 1000000 ~ 9999999 사이의 숫자 생성
    private String generateFileSn() {
        Random random = new Random();
        return String.valueOf(1000000 + random.nextInt(9000000));
    }

    private String getOriginalFileName(String originalFileName) {
        String sFileNm;
        // 파일이름에 백슬래쉬가 있는 경우 백슬래쉬 이후의 문자열만 추출
        if (originalFileName.indexOf("\\" ) >= 0) {
            sFileNm = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1, originalFileName.length());
        } else {
            sFileNm = originalFileName;
        }

        return sFileNm;
    }

    private String generateStreCours () {
        Calendar now = Calendar.getInstance();

        List<String> cours = new ArrayList<>();
        cours.add(String.valueOf(now.get(Calendar.YEAR)));
        cours.add(String.valueOf(now.get(Calendar.MONTH) + 1));
//        cours.add(streFileNm);

        String rootPath = Paths.get(System.getProperty("user.home"), "biz", "file").toString();

        return Paths.get(rootPath, cours.toArray(new String[cours.size()])).toString();
    }

}
