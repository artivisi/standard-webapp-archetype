package ${package}.dao;

import ${package}.domain.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleDao extends PagingAndSortingRepository<Role, String> {
}