#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import ${package}.domain.Menu;
import ${package}.domain.Role;
import ${package}.service.BelajarRestfulService;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:${packageInPathFormat}/**/applicationContext.xml")
public class MenuServiceTestIT {
    @Autowired
    private BelajarRestfulService belajarRestfulService;
    
    @Test
    public void testFindAll() {
        Page<Menu> result = belajarRestfulService.findAllMenu(new PageRequest(0, 
                belajarRestfulService.countAllMenu().intValue()));
        assertTrue(result.getTotalElements() > 0);
    }
    
    @Test
    public void testFindTopLevelMenu(){
        List<Menu> hasil = belajarRestfulService.findTopLevelMenu();
        assertNotNull(hasil);
        
        assertTrue(hasil.size() == 4);
        
        Menu system = hasil.get(0);
        assertEquals("system", system.getId());
        assertEquals("#", system.getAction());
        assertEquals("System", system.getLabel());
        assertEquals(Integer.valueOf(0), system.getLevel());
        assertNull(system.getOptions());
        assertEquals(Integer.valueOf(0), system.getOrder());
        assertNull(system.getParent());
        
        Menu laporan = hasil.get(3);
        assertEquals("laporan", laporan.getId());
        assertEquals("#", laporan.getAction());
        assertEquals("Laporan", laporan.getLabel());
        assertEquals(Integer.valueOf(0), laporan.getLevel());
        assertNull(laporan.getOptions());
        assertEquals(Integer.valueOf(3), laporan.getOrder());
        assertNull(laporan.getParent());
    }
    
    @Test
    public void testFindMenuByParent(){
        Menu m = belajarRestfulService.findMenuById("system");
        
        List<Menu> hasil = belajarRestfulService.findMenuByParent(m);
        assertNotNull(hasil);
        
        assertTrue(hasil.size() == 8);
        
        Menu header = hasil.get(0);
        assertEquals("system-header", header.getId());
        assertNull(header.getAction());
        assertEquals("Pengaturan Aplikasi", header.getLabel());
        assertEquals(Integer.valueOf(1), header.getLevel());
        assertEquals("{css-class:nav-header}", header.getOptions());
        assertEquals(Integer.valueOf(0), header.getOrder());
        assertNotNull(header.getParent());
        
        Menu permission = hasil.get(7);
        assertEquals("system-permission", permission.getId());
        assertEquals("#/system/permission", permission.getAction());
        assertEquals("Permission", permission.getLabel());
        assertEquals(Integer.valueOf(1), permission.getLevel());
        assertNull(permission.getOptions());
        assertEquals(Integer.valueOf(7), permission.getOrder());
        assertNotNull(permission.getParent());
    }
    
    @Test
    public void testFindNotInRole() {
        Role r = new Role();
        r.setId("staff");
        
        List<Menu> hasil = belajarRestfulService.findMenuNotInRole(r);
        assertEquals(new Integer(13), new Integer(hasil.size()));
        
        for (Menu menu : hasil) {
            if(menu.getId().equals("master")){
                Assert.fail("Seharusnya tidak ada menu master");
            }
        }
    }
}
