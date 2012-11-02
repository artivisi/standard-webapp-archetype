#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import ${package}.domain.ApplicationConfig;
import ${package}.service.BelajarRestfulService;
import ${package}.ui.helper.Range;

@Controller
@RequestMapping("/config")
public class ApplicationConfigController {

    @Autowired private BelajarRestfulService belajarRestfulService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/{id}")
	@ResponseBody
	public ApplicationConfig findApplicationConfigById(@PathVariable String id){
		ApplicationConfig config = belajarRestfulService.findApplicationConfigById(id);
		if(config == null){
			throw new IllegalStateException();
		}
		return config;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ApplicationConfig config, HttpServletRequest request, HttpServletResponse response){
		belajarRestfulService.save(config);
		String requestUrl = request.getRequestURL().toString();
        URI uri = new UriTemplate("{requestUrl}{id}").expand(requestUrl, config.getId());
        response.setHeader("Location", uri.toASCIIString());
	}

	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable String id, @RequestBody ApplicationConfig config){
		ApplicationConfig a = belajarRestfulService.findApplicationConfigById(id);
		if(a == null){
			logger.warn("Config dengan id [{}] tidak ditemukan", id);
			throw new IllegalStateException();
		}
		config.setId(a.getId());
		belajarRestfulService.save(config);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id){
		ApplicationConfig a = belajarRestfulService.findApplicationConfigById(id);
		if(a == null){
			logger.warn("Config dengan id [{}] tidak ditemukan", id);
			throw new IllegalStateException();
		}
		belajarRestfulService.delete(a);
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public List<ApplicationConfig> findAll(@RequestHeader(value="Range", required=false) String range,
			@RequestParam(required=false) String search,
			HttpServletResponse response){
		logger.debug("Range : [{}]", range);
		
		Range requestRange = Range.fromRequestHeader(range);
		
		if(StringUtils.hasText(search)){
			Long countAll = belajarRestfulService.countApplicationConfigs(search);
			Range responseRange = new Range(requestRange.getFrom(), requestRange.getTo(), countAll);
			logger.debug("Response Range : {}", responseRange.toString());
			response.setHeader("Content-Range", responseRange.toResponseHeader());
			
			return belajarRestfulService.findApplicationConfigs(search, responseRange.getFrom(), 
					(responseRange.getTo() - responseRange.getFrom()+1));
		} else {
			Long countAll = belajarRestfulService.countAllApplicationConfigs();
			Range responseRange = new Range(requestRange.getFrom(), requestRange.getTo(), countAll);
			response.setHeader("Content-Range", responseRange.toResponseHeader());
			
			return belajarRestfulService.findAllApplicationConfigs(responseRange.getFrom() , 
					(responseRange.getTo() - responseRange.getFrom()+1));
		}
	}


	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalStateException.class})
    public void handle() {
		logger.debug("Konfig dengan nama tersebut tidak ditemukan");
    }
}
