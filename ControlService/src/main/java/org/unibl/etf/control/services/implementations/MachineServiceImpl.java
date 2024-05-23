package org.unibl.etf.control.services.implementations;

import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.unibl.etf.control.model.MachineStatus;
import org.unibl.etf.control.repositories.MachineStatusRepository;
import org.unibl.etf.control.services.MachineService;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MachineServiceImpl implements MachineService{
	
	private static final Logger infoLogger = LoggerFactory.getLogger(MachineServiceImpl.class);
    private static final Logger warnLogger = LoggerFactory.getLogger("WARN_FILE");
	

	private MachineStatusRepository machineStatusRepository;

	private MqttClient mqttMachineSubscriber;
	private MqttClient mqttControlPublisher;
	private Gson jsonParser;

	public MachineServiceImpl(MqttClient mqttMachineSubscriber, MachineStatusRepository machineStatusRepository,
			MqttClient mqttControlPublisher, Gson jsonParser) {
		this.machineStatusRepository = machineStatusRepository;
		this.mqttMachineSubscriber = mqttMachineSubscriber;
		this.mqttControlPublisher = mqttControlPublisher;
		this.jsonParser = jsonParser;
		this.startMqttSubscriptions();
	}
	
	@Override
	public List<MachineStatus> findAllStatus() {
		infoLogger.info("Request for all machines' status timestamps");
		return machineStatusRepository.findAll();
	}

	private void startMqttSubscriptions() {
		try {
			mqttMachineSubscriber.subscribe("machine_status", 0);
			infoLogger.info("Subscribed to topic machine_status");
			mqttMachineSubscriber.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String messageData = new String(message.getPayload());
					infoLogger.info("The following message arrived at topic " + topic + ": " + messageData);
					machineStatusRepository.saveAndFlush(jsonParser.fromJson(messageData, MachineStatus.class));
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
	
	public boolean publish(int machineId, String command) {
		infoLogger.info("Requested command " + command + " for machine with id " + machineId);
		if (!"CONT".equals(command) && !"PAUSE".equals(command) && !"STOP".equals(command))
			return false;
		String machine = "";
		switch (machineId) {
		case 1:
			machine = "control_first";
			break;
		case 2:
			machine = "control_second";
			break;
		case 3:
			machine = "control_third";
			break;
		default:
			return false;
		}
		try {
			this.mqttControlPublisher.publish(machine, new MqttMessage(command.getBytes()));
			infoLogger.info("Message " + command + " published at topic " +  machine);
			return true;
		} catch (Exception e) {
			warnLogger.warn(e.toString());
		}
		return false;
	}

}
