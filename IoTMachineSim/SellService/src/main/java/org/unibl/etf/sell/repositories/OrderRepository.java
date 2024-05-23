package org.unibl.etf.sell.repositories;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.unibl.etf.sell.model.Item;
import org.unibl.etf.sell.model.Order;

import com.google.gson.Gson;

@Repository
public class OrderRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);
	
	@Value("${order.directory}")
    private String orderDirectory;
	private Integer nextFileNumber;
	private Gson jsonParser;
	
	public OrderRepository(Integer nextFileNumber, Gson jsonParser) {
		this.nextFileNumber = nextFileNumber;
		this.jsonParser = jsonParser;
	}

	@RabbitListener(queues = "${spring.rabbitmq.queue-name}")
    public void handleMessage(String message) {
		logger.info("Received message from Angular app: " + message);
        try {
        	Order order = jsonParser.fromJson(message, Order.class);
        	BufferedWriter bw = new BufferedWriter(new FileWriter(orderDirectory + "/order" + nextFileNumber));
        	bw.write("Buyer: " + order.getBuyer());
        	bw.newLine();
        	bw.write("Purchase date: " + order.getBuydate());
        	bw.newLine();
        	bw.write("Payment date: " + order.getPaydate());
        	bw.newLine();
        	bw.write("Address: " + order.getAddress());
        	bw.newLine();
        	bw.write("Items: ");
        	bw.newLine();
        	for (Item item : order.getItems()) {
        		bw.write(item.toString());
        		bw.newLine();
        	}
        	bw.close();
        	logger.info("Order successfully written in file");
        	nextFileNumber++;
        } catch (Exception e) {
        	logger.warn(e.toString());
        }
    }
}
