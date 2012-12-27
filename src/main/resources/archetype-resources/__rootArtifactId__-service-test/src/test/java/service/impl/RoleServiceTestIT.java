#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import ${package}.domain.Menu;
import ${package}.domain.Permission;
import ${package}.domain.Role;
import ${package}.service.BelajarRestfulService;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:${packageInPathFormat}/**/applicationContext.xml")
public class RoleServiceTestIT {

    @Autowired
    private BelajarRestfulService service;

    @Test
    public void testFindById() {
        Role role = service.findRoleById("superuser");
        assertNotNull(role);
        assertEquals("Super User", role.getName());
        assertEquals("Full Access", role.getDescription());
        
        assertNotNull(role.getMenuSet());
        
        for (Menu menu : role.getMenuSet()) {
            assertNotNull(menu.getLabel());
        }
        
        assertNotNull(role.getPermissionSet());
        
        for (Permission perm : role.getPermissionSet()) {
            assertNotNull(perm.getValue());
        }
        
        assertNull(service.findRoleById(null));
        assertNull(service.findRoleById(""));
    }

    @Test
    public void testFindAll() {
        Page<Role> result = service.findAllRoles(new PageRequest(0, service.countAllRoles().intValue()));
        assertTrue(result.getTotalElements() > 0);
    }
}
