package com.biz.framework.file;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {

    void saveFile(FileDto fileDto);
    List<FileDto> findByAtchFileIdAndUseAt(FileDto fileDto);

    FileDto findByATchfileIdAndFileSnAndUseAt(FileDto fileDto);

    int updateUseAtByFileIdAndFileSn(FileDto fileDto);

    int removeFile(FileDto fileDto);
}
