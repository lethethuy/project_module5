package ra.service.impl.role;

import ra.model.entity.Role;
import ra.model.entity.RoleName;
import ra.repository.IRoleRepository;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
