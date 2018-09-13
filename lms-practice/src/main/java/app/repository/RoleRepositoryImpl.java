package app.repository;

import org.springframework.beans.factory.annotation.Autowired;

import app.entity.Role;

public class RoleRepositoryImpl implements RoleRepositoryCustomized {
	
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public Role findOrCreate(int roleId) {
		Role role;
				
		if (roleRepo.existsById(roleId)) {
			role = roleRepo.findById(roleId);
		} else {
			role = new Role(Role.BORROWER);
			roleRepo.save(role);
		}

		return role;
	}
}
