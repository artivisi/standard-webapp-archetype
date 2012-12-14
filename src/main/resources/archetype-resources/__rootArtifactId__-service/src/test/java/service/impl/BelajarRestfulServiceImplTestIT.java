#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ${package}.domain.ApplicationConfig;
import ${package}.service.BelajarRestfulService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:${packageInPathFormat}/**/applicationContext.xml"})
public class BelajarRestfulServiceImplTestIT {
	@Autowired
    private BelajarRestfulService service;
    @Autowired
    private DataSource dataSource;

    @Before
    public void resetDatabase() throws Exception {
        IDataSet[] daftarDataset = new IDataSet[]{
                new FlatXmlDataSetBuilder()
                .build(new File("src/test/resources/sample-data.xml"))
        };

        Connection conn = dataSource.getConnection();
        DatabaseOperation.CLEAN_INSERT
                .execute(new DatabaseConnection(conn),
                        new CompositeDataSet(daftarDataset));
    }

    @Test
    public void testFindById() {
        ApplicationConfig ac = service.findApplicationConfigById("def456");
        assertNotNull(ac);
        assertEquals("applicationversion", ac.getName());
        assertEquals("Application Version", ac.getLabel());
        assertEquals("1.0", ac.getValue());
    }

    @Test
    public void testSaveNew() {
        ApplicationConfig ac = new ApplicationConfig();
        ac.setName("base.path");
        ac.setLabel("Installation Path");
        ac.setValue("/opt");
        Long countAll = service.countAllApplicationConfigs();
        service.save(ac);
        assertEquals(Long.valueOf(countAll + 1), service.countAllApplicationConfigs());
        assertNotNull(ac.getId());
    }

    @Test
    public void testSaveExisting() {
        ApplicationConfig ac = service.findApplicationConfigById("abc123");
        assertNotNull(ac);
        ac.setLabel("Versi Aplikasi");
        ac.setValue("2.0");
        service.save(ac);
        ApplicationConfig ac1 = service.findApplicationConfigById("abc123");
        assertNotNull(ac1);
        assertEquals("Versi Aplikasi", ac1.getLabel());
        assertEquals("2.0", ac1.getValue());
    }

    @Test
    public void testDeleteExisting() {
        ApplicationConfig ac = service.findApplicationConfigById("abc123");
        assertNotNull(ac);
        Long countAll = service.countAllApplicationConfigs();
        service.delete(ac);
        assertEquals(Long.valueOf(countAll - 1), service.countAllApplicationConfigs());
        ApplicationConfig ac1 = service.findApplicationConfigById("applicationversion");
        assertNull(ac1);
    }

    @Test
    public void testFindAll() {
        List<ApplicationConfig> result =
                service.findAllApplicationConfigs(new PageRequest(0, service.countAllApplicationConfigs().intValue())).getContent();
        assertTrue(result.size() > 0);
    }

    @Test
    public void testSearch() {
        List<ApplicationConfig> result = service.findApplicationConfigs("name", new PageRequest(0, 5)).getContent();
        assertTrue(result.size() == 1);
    }

    @Test
    public void testCountSearch() {
        Long result = service.countApplicationConfigs("name");
        assertTrue(result == 1);
    }
}
