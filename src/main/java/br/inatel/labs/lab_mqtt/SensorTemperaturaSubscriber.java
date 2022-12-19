package br.inatel.labs.lab_mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.UUID;

public class SensorTemperaturaSubscriber {

    public static void main(String[] args) throws MqttException {

        //1. cria o subscriber
        var subscriberId = UUID.randomUUID().toString();
        var subscriber = new MqttClient( MyConstants.URI_BROKER, subscriberId);

        //2. conecta
        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        subscriber.connect(options);

        //3. recebe a mensagem
        subscriber.subscribe(MyConstants.TOPIC_1,
                (topic,msg) -> {
                    byte[] payload = msg.getPayload();
                    System.out.println("Payload recebido: " + payload);
                    System.out.println("Topico recebido: " + topic);
                });

        //4. desconecta
        subscriber.disconnect();
    }

}
