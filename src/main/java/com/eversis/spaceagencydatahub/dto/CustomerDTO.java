package com.eversis.spaceagencydatahub.dto;


import com.eversis.spaceagencydatahub.dictionary.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String login;
    private String password;
    private Role role;
}
