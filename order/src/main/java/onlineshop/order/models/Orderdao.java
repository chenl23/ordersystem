package onlineshop.order.models;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
@Transactional
public class Orderdao {
	@Autowired
	  private SessionFactory _sessionFactory;
	private Session getSession() {
	    return _sessionFactory.getCurrentSession();
	  }
	public long save(Order o) {
	    getSession().save(o);
	    return o.getId();
	  }
	  
	  public void delete(Order o) {
	    getSession().delete(o);
	    return;
	  }
	  @SuppressWarnings("unchecked")
	public List<Order> getAll() {
		    return getSession().createQuery("from Order").list();//Order is the class not, not table name
		  }
	  
}
