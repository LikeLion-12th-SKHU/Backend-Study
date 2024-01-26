package com.rommmu.model;

import com.rommmu.validation.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentEdit {
    int id;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Size(min=8, max=12, groups = ValidationGroups.SizeGroup.class)
    String studentNo;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Size(min=2, max=20, groups = ValidationGroups.SizeGroup.class)
    String name;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp="010-[0-9]{3,4}-[0-9]{4}",
            message="휴대폰 번호를 입력하세요 예:010-000-0000",
            groups = ValidationGroups.PatternGroup.class)
    String phone;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @Email
    String email;

    @NotEmpty(groups = ValidationGroups.NotEmptyGroup.class)
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp="남|여", message="남, 여 중 하나를 입력하세요.",
            groups = ValidationGroups.PatternGroup.class)
    String sex;

    @Min(value=1, message="학과를 선택하세요.",
            groups = ValidationGroups.MinGroup.class)
    int departmentId;
}
