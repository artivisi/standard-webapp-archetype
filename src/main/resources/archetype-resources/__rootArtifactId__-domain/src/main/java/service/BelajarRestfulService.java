#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.List;

import ${package}.domain.ApplicationConfig;

public interface BelajarRestfulService extends MonitoredService {
	void save(ApplicationConfig ac);
	void delete(ApplicationConfig ac);
	ApplicationConfig findApplicationConfigById(String id);
	List<ApplicationConfig> findAllApplicationConfigs(Integer page, Integer rows);
	Long countAllApplicationConfigs();
	Long countApplicationConfigs(String search);
	List<ApplicationConfig> findApplicationConfigs(String search, Integer page, Integer rows);
}
