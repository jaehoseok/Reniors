package com.common.jmark.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobChildCategoryUpdateRequest {
    @NotBlank(message = "소분류 카테고리 이름은 필수입니다.")
    private String name;
}