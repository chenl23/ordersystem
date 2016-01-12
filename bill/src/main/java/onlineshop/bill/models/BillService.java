package onlineshop.bill.models;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import onlineshop.bill.client.CustomerClient;
import onlineshop.bill.client.ItemClient;
import onlineshop.bill.client.MessageClient;
import onlineshop.bill.client.OrderClient;

@Component
public class BillService extends Thread{
	private static final Logger logger = LoggerFactory.getLogger(BillService.class);
	@Autowired
	private BillDao bd;
	
	@Autowired
	private OrderClient oc;
	@Autowired
	private ItemClient ic;
	@Autowired
	private CustomerClient cc;
	@Autowired
	private MessageClient mgc;
	
	public BillService() {
		super();
	}
	private float calculateTotalPrice(int orderId){
		
		logger.info("calculate order total price");
		float total=0;
		Order o = oc.getById(orderId);
		List<OrderLine> its=o.getItems();
		for(OrderLine it:its){
			
			long id=it.getItemId();
			float p =ic.getById(id).getPrice();
			total += p*it.getCount();
			logger.debug("item {} price is {}", id, p);
		}
		
		return total;
	} 
	public boolean checkBalance(int orderId){
		
		long cid=oc.getById(orderId).getCustomerId();
		Customer c = cc.getById(cid);
		if ( c.getBalance()>=this.calculateTotalPrice(orderId))
		{
			logger.info("enough balance {}", c.getBalance());
			return true;
			
		}
		logger.info("need to add more balance");
		return false;
		
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("BillService running");
		while(true){
			try {
				if (oc == null)  {
					logger.error("order client is null");
					return;
				}
				
				
				String msg=mgc.recv(MessageClient.ORDER_QUEUE_NAME);
				if (msg.startsWith("Order create:"))
				{
					String id=msg.substring(msg.lastIndexOf(':')+1);
					logger.info("recv order event,order id {}",id);
					logger.info("debug print id{}",Integer.parseInt(id));
					processBill(Integer.parseInt(id));
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void processBill(int orderId){
		if (checkBalance(orderId))
		{
			Bill b = new Bill(orderId,oc.getById(orderId).getCustomerId(),calculateTotalPrice(orderId));
			bd.save(b);
		}
		else{
			logger.info("plz add more balance!!!!");
		}
	}
}
