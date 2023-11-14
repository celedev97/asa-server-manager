package dev.cele.asa_sm.dto.github;
/*
{
      "id": 135489296,
      "name": "asa_sm_setup.exe",
      "label": "",
      "content_type": "raw",
      "state": "uploaded",
      "size": 33788715,
      "browser_download_url": "https://github.com/celedev97/asa-server-manager/releases/download/v0.0.2/asa_sm_setup.exe"
    }
    */

import lombok.Data;

@Data
public class Asset {
    private Integer id;
    private String name;
    private String label;
    private String content_type;
    private String state;
    private Integer size;
    private String browser_download_url;
}
