package onlineshop.order.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import onlineshop.order.client.CustomerClient;
import onlineshop.order.client.ItemClient;
import onlineshop.order.models.Order;
import onlineshop.order.models.Orderdao;
import onlineshop.order.models.OrderRepository;

import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	//@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    private Orderdao od;
	
	private CustomerClient customerClient;
	private ItemClient itemClient;
	
	@Autowired
	private OrderController(OrderRepository orderRepository, 
			CustomerClient customerClient,
			ItemClient itemClient) {
		super();
		this.orderRepository = orderRepository;
		this.customerClient = customerClient;
		this.itemClient = itemClient;
	}
	
	@RequestMapping(value="/order/add")
	  @ResponseBody
	 
	  public String create(long customerId, long itemId, int count) {
	    try {
	    	 logger.info("enter order");
	      Order o = new Order(customerId, itemId, count);
	      od.save(o);
	    }
	    catch (Exception ex) {
	      return "Error creating the order: " + ex.toString();
	    }
	    return "order succesfully created!";
	  }
	
	
	@RequestMapping(value="/order/addOrderLine")
	  @ResponseBody
	  public String addOrderLine(long id, long itemId, int count) {
	    try {
	      Orderdao od = new Orderdao();
	      Order o = new Order();
	      od.save(o);
	    }
	    catch (Exception ex) {
	      return "Error creating the order: " + ex.toString();
	    }
	    return "order succesfully created!";
	  }
	
	@RequestMapping(value="/oreder/delete/")
	@ResponseBody
	public String delete(long id){
		try{
			Orderdao od = new Orderdao();
			Order o = new Order();
			od.delete(o);
		}
		catch(Exception ex){
			return "fail to delete order:"+ex.toString();
		}
		return "order delete success!!";
	}
	@RequestMapping(value="/")
	public ModelAndView list(){
		return new ModelAndView("orderlist", "orders", od.getAll());
	}
}