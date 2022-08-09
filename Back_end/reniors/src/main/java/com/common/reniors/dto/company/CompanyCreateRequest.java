package com.common.reniors.dto.company;

import com.common.reniors.domain.entity.Type.typeCompany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
public class CompanyCreateRequest {
    private String name;
    private String companyAppId;
    private String companyAppPwd;
    private String establishedAt;
    private String companyUrl;
    private String address;
    private String extraAddress;
    private String companyNum;
    private String companyPhone;
    private String representative;
    private String representativePhone;
    private typeCompany typeCompany;

}
