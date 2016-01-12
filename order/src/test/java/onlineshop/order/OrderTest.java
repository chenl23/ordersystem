package onlineshop.order;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.junit.Assert;
import onlineshop.order.controllers.OrderController;
import onlineshop.order.models.Order;
import onlineshop.order.models.Orderdao;

import static org.mockito.Mockito.*;

public class OrderTest {

	@InjectMocks
	OrderController oc;
	@Mock
	Orderdao od;
	
	
	

	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
	}


	@Test
	public void testOrderList() {
		
		
	}
	@Test
	public void testAddOder() {
	Order o = new Order(5,11,3);
	Mockito.when(od.save(o)).thenReturn(9l);
	//Assert.assertEquals(oc.create(101, 101, 3), "order succesfully created!");
	}
}
