package com.common.jmark.dto.category;

import com.common.jmark.domain.entity.category.Sido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GugunUpdateRequest {

    @NotBlank(message = "구군 이름은 필수입니다.")
    private String name;

    @NotNull(message = "구군 분류코드는 필수입니다.")
    private Long code;

    @NotNull
    private Sido sido;
}