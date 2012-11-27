package ${package}.dao;

import ${package}.domain.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MenuDao extends PagingAndSortingRepository<Menu, String> {

    @Query("select m from Menu m " +
			"where m.parent is null " +
			"order by m.level, m.order")
    public List<Menu> findTopLevelMenu();

    @Query("select m from Menu m " +
			"where m.parent.id = :id " +
			"order by m.level, m.order")
    public List<Menu> findMenuByParent(@Param("id") String id);

    public List<Menu> findByIdNotIn(List<String> ids);

}
