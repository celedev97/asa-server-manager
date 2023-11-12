package dev.cele.asa_sm.dto.curseforge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class ModDto {
    private Integer id;
    private Integer gameId;
    private String name;
    private String slug;
    private ModLinksDto links;
    private String summary;
    private Integer status;
    private Long downloadCount;

    //public Boolean isFeatured;
    //public Integer primaryCategoryId;
    //public CategoryDto[] categories;
    //public Integer classId;
    private List<ModAuthorDto> authors;

    //public ModAssetDto logo;
    //public ModAssetDto[] screenshots;
    //public Integer mainFileId;
    //public FileDto[] latestFiles;
    //public FileIndexDto[] latestFilesIndexes;
    //public FileIndexDto[] latestEarlyAccessFilesIndexes;
    //public String dateCreated;
    private String dateModified; //public String dateReleased;
    //public Boolean allowModDistribution;
    //public Integer gamePopularityRank;
    //public Boolean isAvailable;
    //public Integer thumbsUpCount;
}
