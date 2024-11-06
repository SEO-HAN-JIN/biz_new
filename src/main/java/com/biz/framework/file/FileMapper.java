package com.biz.framework.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    void save(FileDto fileDto);
    List<FileDto> findByAtchFileIdAndUseAt(String fileId, String useAt);

    FileDto findByATchfileIdAndFileSnAndUseAt(String fileId, String fileSn, String y);
}
