package org.unibl.etf.control.services.implementations;

import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.unibl.etf.control.model.PlateProcessed;
import org.unibl.etf.control.repositories.PlateProcessedRepository;
import org.unibl.etf.control.services.PlateService;

import com.google.gson.Gson;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PlateServiceImpl implements PlateService{

	private static final Logger infoLogger = LoggerFactory.getLogger(PlateServiceImpl.class);
    private static final Logger warnLogger = LoggerFactory.getLogger("WARN_FILE");
	
	private PlateProcessedRepository plateProcessedRepository;
	
	private MqttClient mqttPlateSubscriber;
	
	private Gson jsonParser;
	
	public PlateServiceImpl(PlateProcessedRepository plateProcessedRepository, MqttClient mqttPlateSubscriber, Gson jsonParser) {
		super();
		this.plateProcessedRepository = plateProcessedRepository;
		this.mqttPlateSubscriber = mqttPlateSubscriber;
		this.jsonParser = jsonParser;
		this.startMqttSubscriptions();
	}


	@Override
	public List<PlateProcessed> findAll() {
		infoLogger.info("Request for all processed plates' timestamps");
		return plateProcessedRepository.findAll();
	}
	
	private void startMqttSubscriptions() {
		try {
			mqttPlateSubscriber.subscribe("plate_status", 0);
			infoLogger.info("Subscribed to topic plate_status");
			mqttPlateSubscriber.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String messageData = new String(message.getPayload());
					infoLogger.info("The following message arrived at topic " + topic + ": " + messageData);
					plateProcessedRepository.saveAndFlush(jsonParser.fromJson(messageData, PlateProcessed.class));
					infoLogger.info("Saved: " + messageData);
					
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
				
			});
		} catch (Exception e) {
			warnLogger.warn(e.toString());
		}
	}

}
