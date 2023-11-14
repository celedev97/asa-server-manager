package dev.cele.asa_sm.dto.github;

import lombok.Data;

import java.util.Date;

@Data
public class Release {
    private String id;
    private String html_url;
    private String tag_name;
    private String name;
    private Date published_at;
    private Asset[] assets;
    private String body;
}
