package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Profile {
    private int id;
    private String fullName;
    private String chatId;
    private String phone;
    private ProfileRole role;
    private Step step;
}