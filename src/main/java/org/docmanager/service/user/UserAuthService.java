package org.docmanager.service.user;

import org.docmanager.dto.auth.JwtResponse;
import org.docmanager.dto.auth.UserLoginRequest;
import org.docmanager.dto.auth.UserSignUpRequest;
import org.docmanager.dto.auth.UserSignUpResponse;
import org.docmanager.exception.custom.AlreadyExistException;

public interface UserAuthService {

    /**
     * @param userSignUpRequest user signup request dto
     * @return @{@link UserSignUpResponse}
     * @throws AlreadyExistException if user email already exists
     */
    UserSignUpResponse registerUser(UserSignUpRequest userSignUpRequest) throws AlreadyExistException;

    /**
     * @param userLoginRequest user login request dto
     * @return @{@link JwtResponse} JWT if successful authentication occurred
     */
    JwtResponse authenticate(UserLoginRequest userLoginRequest);
}
