package org.docmanager.dto.auth;


import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserSignUpResponse {

    private Set<String> roles;

    private String email;

    private boolean success;

}
