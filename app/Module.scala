import javax.net.ssl.{SSLContext, SSLSocketFactory}

import com.google.inject.AbstractModule
import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttClient, MqttConnectOptions}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class Module extends AbstractModule{

  val brokerUrl = "tcp://edwardstx.us:1883"
  val persistence = new MemoryPersistence

  override def configure(): Unit = {
    val client = new MqttClient(brokerUrl, MqttClient.generateClientId, persistence)
    val options: MqttConnectOptions = new  MqttConnectOptions();
    options.setUserName("reverb")
    options.setPassword("yodle123".toCharArray)
    client.connect(options)

    bind(classOf[IMqttClient])
      .toInstance(client)

  }
}
