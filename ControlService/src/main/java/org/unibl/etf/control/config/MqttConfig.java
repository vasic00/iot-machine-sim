package org.unibl.etf.control.config;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class MqttConfig {
	
	@Value("${mqtt.server}")
	private String mqttServer;
	
	@Bean(name = "jsonParser")
	Gson jsonParser() {
		return new Gson();
	}

    @Bean
    MqttConnectOptions mqttConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

    @Bean(name = "mqttMachineSubscriber")
    MqttClient mqttMachineSubscriber() throws MqttException {
    	MqttClient client = new MqttClient(mqttServer, UUID.randomUUID().toString());
        client.connect(mqttConnectOptions());
        return client;
    }

    @Bean(name = "mqttPlateSubscriber")
    MqttClient mqttPlateSubscriber() throws MqttException {
        MqttClient client = new MqttClient(mqttServer, UUID.randomUUID().toString());
        client.connect(mqttConnectOptions());
        return client;
    }
    
    @Bean(name = "mqttControlPublisher")
    MqttClient mqttControlPublisher() throws MqttException {
    	MqttClient client = new MqttClient(mqttServer, UUID.randomUUID().toString());
        client.connect(mqttConnectOptions());
        return client;
    }
    
}
