package dev.cele.asa_sm.dto.curseforge

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class ModDto {
    var id: Int? = null
    var gameId: Int? = null
    var name: String? = null
    var slug: String? = null
    var links: ModLinksDto? = null
    var summary: String? = null
    var status: Int? = null
    var downloadCount: Long? = null

    //public Boolean isFeatured;
    //public Integer primaryCategoryId;
    //public CategoryDto[] categories;
    //public Integer classId;
    var authors: List<ModAuthorDto> = listOf()

    //public ModAssetDto logo;
    //public ModAssetDto[] screenshots;
    //public Integer mainFileId;
    //public FileDto[] latestFiles;
    //public FileIndexDto[] latestFilesIndexes;
    //public FileIndexDto[] latestEarlyAccessFilesIndexes;
    //public String dateCreated;
    var dateModified: String? = null //public String dateReleased;
    //public Boolean allowModDistribution;
    //public Integer gamePopularityRank;
    //public Boolean isAvailable;
    //public Integer thumbsUpCount;
}
