package controllers

import akka.actor.ActorRef
import org.eclipse.paho.client.mqttv3.{IMqttClient, IMqttMessageListener, MqttMessage}

class QueuStreamer(mqtt: IMqttClient, actor: ActorRef, session: String) extends IMqttMessageListener{

  mqtt.subscribe("/session/" + session + "/slide",this)

  override def messageArrived(topic: String, message: MqttMessage): Unit = {
    actor ! message.toString
  }
}