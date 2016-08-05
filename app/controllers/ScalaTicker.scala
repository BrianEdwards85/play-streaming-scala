/*
 * Copyright (C) 2009-2016 Lightbend Inc. <http://www.lightbend.com>
 */
package controllers

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

import akka.actor.{ActorRef, Cancellable}
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Sink, Source}
import org.eclipse.paho.client.mqttv3.{IMqttClient, IMqttMessageListener, MqttMessage}
import play.api.libs.json._
import play.api.libs.streams.Streams

import scala.concurrent.duration._

class ScalaTicker @Inject()(mqtt: IMqttClient) {
//
//  def enumerator(implicit materializer: Materializer) = {
//    val publisher = stringSource.runWith(Sink.asPublisher(fanout = false))
//    val enumerator = Streams.publisherToEnumerator(publisher)
//    enumerator
//  }

  def stringSource: Source[String, _] = {
    val df: DateTimeFormatter = DateTimeFormatter.ofPattern("HH mm ss")
    val tickSource = Source.tick(0 millis, 100 millis, "TICK")
    val s = tickSource.map((tick) => {
      "X:" + df.format(ZonedDateTime.now())
    })
    s
  }

  def createRunner(actor: ActorRef, session: String): Unit = {
    val streamer = new QueuStreamer(mqtt,actor,session)
  }


  def queueSource(session: String): Source[String, _] = {
    val source = Source.actorRef(10,OverflowStrategy.dropHead).
      mapMaterializedValue(a => createRunner(a,session))
    source
  }

  def jsonSource: Source[JsValue, _] = {
    val tickSource = Source.tick(0 millis, 100 millis, "TICK")
    val s = tickSource.map((tick) => Json.toJson(ZonedDateTime.now))
    s
  }

}


