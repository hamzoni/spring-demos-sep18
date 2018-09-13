package app.repository;

import app.entity.Role;

public interface RoleRepositoryCustomized {
	Role findOrCreate(int roleId);
}
