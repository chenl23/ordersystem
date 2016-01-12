package onlineshop.order.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import onlineshop.order.client.CustomerClient;
import onlineshop.order.client.ItemClient;
import onlineshop.order.client.MessageClient;
import onlineshop.order.models.Item;
import onlineshop.order.models.Order;
import onlineshop.order.models.Orderdao;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
    private Orderdao od;
	
	private CustomerClient customerClient;
	private ItemClient itemClient;
	private MessageClient messageClient;
	
	@Autowired
	private OrderController(CustomerClient customerClient,
			ItemClient itemClient,MessageClient messageClient) {
		super();
		this.customerClient = customerClient;
		this.itemClient = itemClient;
		this.messageClient=messageClient;
	}
	
	@RequestMapping(value="/order/add")
	  @ResponseBody
	 
	  public String create(long customerId, long itemId, int count) {
	    try {
	      Order o = new Order(customerId, itemId, count);
	      long id = od.save(o);
	      messageClient.send(MessageClient.ORDER_QUEUE_NAME, "Order create:" + id);
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
			Order o = new Order();
			od.delete(o);
		}
		catch(Exception ex){
			return "fail to delete order:"+ex.toString();
		}
		return "order delete success!!";
	}
	@RequestMapping(value="/order/list")
	@ResponseBody
	public List<Order> listAll(){
		logger.info("order controller enter getAll");
		return od.getAll();
	}
	@RequestMapping(value="/")
	public ModelAndView list(){
		return new ModelAndView("orderlist", "orders", od.getAll());
	}
}
