package br.inatel.labs.lab_mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Random;
import java.util.UUID;

public class SensorTemperaturaPublisher {

    public static void main(String[] args) throws MqttException {

        // 1. cria o publisher
        var publisherId = UUID.randomUUID().toString();
        var publisher = new MqttClient( MyConstants.URI_BROKER, publisherId);

        // 2. conecta
        var msg = getTempSolo();
        msg.setQos(0);
        msg.setRetained(true);

        // 3. recebe a mensagem
        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);

        // 4. publica a mensagem
        publisher.publish(MyConstants.TOPIC_1, msg);

        // 5. desconecta
        publisher.disconnect();
    }

    private static MqttMessage getTempSolo(){
        var r = new Random();
        var temp = 80 + r.nextDouble() * 20.0;
        var payload = String.format("T:%04.2f", temp).getBytes();
        return new MqttMessage(payload);
    }
}
