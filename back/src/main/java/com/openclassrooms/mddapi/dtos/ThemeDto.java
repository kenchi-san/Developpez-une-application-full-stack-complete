package com.openclassrooms.mddapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ThemeDto {

    @Schema(description = "Id du thème")
    private long id;
    @Schema(description = "Nom du thème")
    private String name;
}
