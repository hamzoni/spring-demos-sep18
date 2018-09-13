package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.entity.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer>, RoleRepositoryCustomized {
	Role findById(int roleId);
}
