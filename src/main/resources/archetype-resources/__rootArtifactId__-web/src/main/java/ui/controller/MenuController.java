#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.controller;

import ${package}.domain.Menu;
import ${package}.service.BelajarRestfulService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

@Controller
public class MenuController {

    @Autowired
    private BelajarRestfulService belajarRestfulService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/menu/{id}")
    @ResponseBody
    public Menu findById(@PathVariable String id) {
        Menu x = belajarRestfulService.findMenuById(id);
        if (x == null) {
            throw new IllegalStateException();
        }
        return x;
    }
    
    @RequestMapping("/role/{id}/unselected-menu")
    @ResponseBody
    public List<Menu> findMenuNotInRole(@PathVariable String id) {
        return belajarRestfulService.findMenuNotInRole(belajarRestfulService.findRoleById(id));
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Menu x, HttpServletRequest request, HttpServletResponse response) {
        belajarRestfulService.save(x);
        String requestUrl = request.getRequestURL().toString();
        URI uri = new UriTemplate("{requestUrl}/{id}").expand(requestUrl, x.getId());
        response.setHeader("Location", uri.toASCIIString());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/menu/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody @Valid Menu x) {
        Menu a = belajarRestfulService.findMenuById(id);
        if (a == null) {
            logger.warn("Menu dengan id [{}] tidak ditemukan", id);
            throw new IllegalStateException();
        }
        x.setId(a.getId());
        belajarRestfulService.save(x);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/menu/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        Menu a = belajarRestfulService.findMenuById(id);
        if (a == null) {
            logger.warn("Menu dengan id [{}] tidak ditemukan", id);
            throw new IllegalStateException();
        }
        belajarRestfulService.delete(a);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> findAll(
            Pageable pageable,
            HttpServletResponse response) {

        return belajarRestfulService.findAllMenu(pageable).getContent();

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalStateException.class})
    public void handle() {
        logger.debug("Resource dengan URI tersebut tidak ditemukan");
    }
}
