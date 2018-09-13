package app.service;

import app.dto.LoginDto;
import app.dto.UserDto;
import app.entity.User;
import app.exception.ExceptionDuplicateRecord;

public interface AuthService {
	// UC2
	User register(UserDto accountDto) throws ExceptionDuplicateRecord;
	
	// UC1
	Object login(LoginDto loginDto);
}
