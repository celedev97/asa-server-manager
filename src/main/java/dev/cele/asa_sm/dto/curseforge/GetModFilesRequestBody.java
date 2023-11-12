package dev.cele.asa_sm.dto.curseforge;

import lombok.Data;

import java.util.List;

@Data
public class GetModFilesRequestBody {
    private List<Integer> modIds;

    public GetModFilesRequestBody(List<Integer> modIds) {
        this.modIds = modIds;
    }

    public GetModFilesRequestBody() {
    }
}
