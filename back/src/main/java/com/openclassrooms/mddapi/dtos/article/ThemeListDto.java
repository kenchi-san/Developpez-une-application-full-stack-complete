package com.openclassrooms.mddapi.dtos.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeListDto {
    private long id;
    private String name;
    private String description;
}
