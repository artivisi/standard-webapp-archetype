#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import ${package}.domain.User;
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
public class UserServiceTestIT {

    @Autowired
    private BelajarRestfulService service;

    @Test
    public void testFindById() {
        User ac = service.findUserById("endy");
        assertNotNull(ac);
        assertEquals("endy", ac.getUsername());
        assertEquals("Endy Muhardin", ac.getFullname());
        assertEquals("123", ac.getPassword());
        assertEquals(Boolean.TRUE, ac.getActive());
        assertEquals("Super User", ac.getRole().getName());
        
        assertNull(service.findUserById(null));
        assertNull(service.findUserById(""));
    }

    @Test
    public void testFindAll() {
        Page<User> result = service.findAllUsers(new PageRequest(0, service.countAllUsers().intValue()));
        assertTrue(result.getTotalElements() > 0);
    }
}
