#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ${package}.dao.ApplicationConfigDao;
import ${package}.domain.ApplicationConfig;
import ${package}.service.BelajarRestfulService;

@SuppressWarnings("unchecked")
@Service("belajarRestfulService")
@Transactional
public class BelajarRestfulServiceImpl implements BelajarRestfulService {

	@Autowired private ApplicationConfigDao applicationConfigDao;

	@Override
	public void save(ApplicationConfig ac) {
		applicationConfigDao.save(ac);
	}

	@Override
	public void delete(ApplicationConfig ac) {
		if(ac == null || ac.getId() == null) {
			return;
		}
		applicationConfigDao.delete(ac);
	}

	@Override
	public ApplicationConfig findApplicationConfigById(String id) {
		if(!StringUtils.hasText(id)) {
			return null;
		}
		return applicationConfigDao.findOne(id);
	}

	@Override
	public List<ApplicationConfig> findAllApplicationConfigs(Integer page, Integer rows) {
		if(page == null || page < 0){
			page = 0;
		}
		
		if(rows == null || rows < 1){
			rows = 20;
		}
		
		return applicationConfigDao.findAll(new PageRequest(page, rows)).getContent();
	}

	@Override
	public Long countAllApplicationConfigs() {
		return applicationConfigDao.countAll();
	}
	
	@Override
	public List<ApplicationConfig> findApplicationConfigs(String search, Integer page, Integer rows) {
		if(page == null || page < 0){
			page = 0;
		}
		
		if(rows == null || rows < 1){
			rows = 20;
		}
		
		if(!StringUtils.hasText(search)) {
			return findAllApplicationConfigs(page, rows);
		}
		
		return applicationConfigDao.search("%"+search+"%", new PageRequest(page, rows));
	}

	@Override
	public Long countApplicationConfigs(String search) {
		if(!StringUtils.hasText(search)) {
			return countAllApplicationConfigs();
		}
		return applicationConfigDao.count("%"+search+"%");
	}

}
