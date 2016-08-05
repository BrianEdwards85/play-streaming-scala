import com.google.inject.AbstractModule
import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttClient}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class Module extends AbstractModule{

  val brokerUrl = "tcp://localhost:1883"
  val persistence = new MemoryPersistence

  override def configure(): Unit = {
    val client = new MqttClient(brokerUrl, MqttClient.generateClientId, persistence)
    client.connect

    bind(classOf[IMqttClient])
      .toInstance(client)

  }
}
