package org.unibl.etf.machines;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.unibl.etf.data.MachineStatus;
import org.unibl.etf.data.PlateProcessed;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class Main {

	private volatile static int plateId = 1;
	private static final String URL = "tcp://broker.mqttdashboard.com:1883";
	private static final HashMap<Integer, PlateProcessed> plateProcessedMap = new HashMap<>();
	private static final Gson gson = new Gson();
	
	private static boolean pausedM1 = false;
	private static boolean pausedM2 = false;
	private static boolean pausedM3 = false;
	private static boolean shutdownM1 = false;
	private static boolean shutdownM2 = false;
	private static boolean shutdownM3 = false;
	private static final Object lockM1 = new Object();
	private static final Object lockM2 = new Object();
	private static final Object lockM3 = new Object();
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
	
			String plateProcessedPublisherId = UUID.randomUUID().toString();
			String machineStatusPublisherId = UUID.randomUUID().toString();
			
			MqttClient plateProcessedPublisher = new MqttClient(URL, plateProcessedPublisherId);
			MqttClient machineStatusPublisher = new MqttClient(URL, machineStatusPublisherId);
			
			String publisherIdM1 = UUID.randomUUID().toString();
			
			MqttClient platePublisherM1 = new MqttClient(URL, publisherIdM1);
			MqttClient controlSubscriberM1 = new MqttClient(URL, UUID.randomUUID().toString());
			
			MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
			
            platePublisherM1.connect(options);
            plateProcessedPublisher.connect(options);
            machineStatusPublisher.connect(options);
            controlSubscriberM1.connect(options);
            controlSubscriberM1.subscribe("control_first", 0);
            controlSubscriberM1.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					synchronized(lockM1) {
						if (!shutdownM1) {
							String messageData = new String(message.getPayload());
							if ("PAUSE".equals(messageData)) {
								pausedM1 = true;
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(1, "pause").getBytes()));
							}
							else if ("CONT".equals(messageData)) {
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(1, "resume").getBytes()));
								pausedM1 = false;
								lockM1.notifyAll();
							}
							else if ("STOP".equals(messageData)) {
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(1, "shutdown").getBytes()));
								controlSubscriberM1.unsubscribe("control_first");
								pausedM1 = false;
								shutdownM1 = true;
								lockM1.notifyAll();
							}
						}
					}
					
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            
            
            machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(1, "start").getBytes()));
            
            
            String publisherIdM2 = UUID.randomUUID().toString();
			String subscriberIdM2 = UUID.randomUUID().toString();
			
			MqttClient platePublisherM2 = new MqttClient(URL, publisherIdM2);
			MqttClient plateSubscriberM2 = new MqttClient(URL, subscriberIdM2);
			MqttClient controlSubscriberM2 = new MqttClient(URL, UUID.randomUUID().toString());
			
            platePublisherM2.connect(options);
            plateSubscriberM2.connect(options);
            controlSubscriberM2.connect(options);
            controlSubscriberM2.subscribe("control_second", 0);
            controlSubscriberM2.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					synchronized(lockM2) {
						if (!shutdownM2) {
							String messageData = new String(message.getPayload());
							if ("PAUSE".equals(messageData)) {
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(2, "pause").getBytes()));
								pausedM2 = true;
							}
							else if ("CONT".equals(messageData)) {
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(2, "resume").getBytes()));
								pausedM2 = false;
								lockM2.notifyAll();
							}
							else if ("STOP".equals(messageData)) {
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(2, "shutdown").getBytes()));
								plateSubscriberM2.unsubscribe("result_first");
								controlSubscriberM2.unsubscribe("control_second");
								pausedM2 = false;
								shutdownM2 = true;
								lockM2.notifyAll();
							}
						}
					}
					
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            
            machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(2, "start").getBytes()));
            plateSubscriberM2.subscribe("result_first", 0);
            plateSubscriberM2.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					synchronized(lockM2) {
						if (shutdownM2)
							return;
						if (pausedM2)
							lockM2.wait();
						
					}
					String plateId = new String(message.getPayload());
					System.out.println("Ploca sa ID = " + new String(message.getPayload()) + " se nalazi u masini za ravnanje");
					System.out.println("Zapocinje ravnanje ploce sa ID = " + plateId);
					Thread.sleep(4000);
					System.out.println("Ravnanje ploce sa ID = " + plateId + " zavrseno, ploca se salje u masinu za lakiranje");
					MqttMessage messageNext = new MqttMessage(String.valueOf(plateId).getBytes());
					messageNext.setQos(0);
					platePublisherM2.publish("result_second", messageNext);
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            
			String subscriberIdM3 = UUID.randomUUID().toString();
			
			MqttClient plateSubscriberM3 = new MqttClient(URL, subscriberIdM3);
			MqttClient controlSubscriberM3 = new MqttClient(URL, UUID.randomUUID().toString());
			
            plateSubscriberM3.connect(options);
            controlSubscriberM3.connect(options);
            controlSubscriberM3.subscribe("control_third", 0);
            controlSubscriberM3.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					synchronized(lockM3) {
						if (!shutdownM3) {
							String messageData = new String(message.getPayload());
							if ("PAUSE".equals(messageData)) {
//								machineStatusPublisher.publish("machine_status", new MqttMessage(getMachinePause(3).getBytes()));
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(3, "pause").getBytes()));
								pausedM3 = true;
							}
							else if ("CONT".equals(messageData)) {
//								machineStatusPublisher.publish("machine_status", new MqttMessage(getMachineResume(3).getBytes()));
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(3, "resume").getBytes()));
								pausedM3 = false;
								lockM3.notifyAll();
							}
							else if ("STOP".equals(messageData)) {
//								machineStatusPublisher.publish("machine_status", new MqttMessage(getMachineShutdown(3).getBytes()));
								machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(3, "shutdown").getBytes()));
								plateSubscriberM3.unsubscribe("result_second");
								controlSubscriberM3.unsubscribe("control_third");
								pausedM3 = false;
								shutdownM3 = true;
								lockM3.notifyAll();
							}
						}
					}
					
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            
//            machineStatusPublisher.publish("machine_status", new MqttMessage(getMachineStart(3).getBytes()));
            machineStatusPublisher.publish("machine_status", new MqttMessage(getStatus(3, "start").getBytes()));
            plateSubscriberM3.subscribe("result_second", 0);
            plateSubscriberM3.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					synchronized(lockM3) {
						if (shutdownM3)
							return;
						if (pausedM3)
							lockM3.wait();
					}
					String plateId = new String(message.getPayload());
					System.out.println("Ploca sa ID = " + new String(message.getPayload()) + " se nalazi u masini za lakiranje");
					System.out.println("Zapocinje lakiranje ploce sa ID = " + plateId);
					Thread.sleep(4000);
					System.out.println("Lakiranje ploce sa ID = " + plateId + " zavrseno, ploca se salje u skladiste");
					URL productURL = new URL("http://localhost:8082/api/products/plates/" + plateId);
					HttpURLConnection con = (HttpURLConnection)productURL.openConnection();
					con.setRequestMethod("POST");
					int responseCode = con.getResponseCode();
					if (responseCode == 200) {
						synchronized(plateProcessedMap) {
							PlateProcessed pp = plateProcessedMap.get(Integer.parseInt(plateId));
							pp.setFinished(LocalDateTime.now().toString().split("\\.")[0]);
							plateProcessedMap.remove(pp.getPlateId());
							plateProcessedPublisher.publish("plate_status", new MqttMessage(gson.toJson(pp).getBytes()));
						}
					}
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            
            Thread shutdownThread = new Thread(() -> {
        		shutdownCleanup("http://localhost:8081/api/control/machines/status/" + 1 + "/STOP");
        		shutdownCleanup("http://localhost:8081/api/control/machines/status/" + 2 + "/STOP");
        		shutdownCleanup("http://localhost:8081/api/control/machines/status/" + 3 + "/STOP");	
            });
            Runtime.getRuntime().addShutdownHook(shutdownThread);
            
            while (true) {
            	synchronized (lockM1) {
            		if (shutdownM1)
            			return;
            		if (pausedM1)
            			lockM1.wait();
            	}
            	System.out.println("Zapocinje oblikovanje ploce sa ID = " + plateId);
            	PlateProcessed plateProcessed = new PlateProcessed();
            	plateProcessed.setPlateId(plateId);
            	plateProcessed.setStarted(LocalDateTime.now().toString().split("\\.")[0]);
            	Thread.sleep(4000);
            	System.out.println("Oblikovanje ploce sa ID = " + plateId + " zavrseno, ploca se salje u masinu za ravnanje");
            	MqttMessage message = new MqttMessage(String.valueOf(plateId).getBytes());
            	message.setQos(0);
            	synchronized(plateProcessedMap) {
            		plateProcessedMap.put(plateId, plateProcessed);
            	}
            	platePublisherM1.publish("result_first", message);
            	plateId++;
            }
			
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
		
	private static String getStatus(int machineId, String status) {
		MachineStatus machineStatus = new MachineStatus();
		machineStatus.setMachineId(machineId);
		machineStatus.setStatus(status);
		machineStatus.setDatetime(LocalDateTime.now().toString().split("\\.")[0]);
		return gson.toJson(machineStatus);
	}
	
	private static int shutdownCleanup(String address){
		try {
			URL controlURL = new URL(address);
			HttpURLConnection con = (HttpURLConnection)controlURL.openConnection();
			con.setRequestMethod("PUT");
			con.connect();
			return con.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		};
		return -1;
	}
	
}
