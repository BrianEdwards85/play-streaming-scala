/*
 * Copyright (C) 2009-2016 Lightbend Inc. <http://www.lightbend.com>
 */
package controllers

import javax.inject.{Inject, Singleton}

import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttMessage}
import play.api.http.ContentTypes
import play.api.libs.EventSource
import play.api.mvc._

@Singleton
class ScalaEventSourceController @Inject()(ticker: ScalaTicker,mqtt: IMqttClient) extends Controller {

  def index() = Action {
    Ok(views.html.scalaeventsource())
  }

  def streamClock() = Action {
    Ok.chunked(ticker.stringSource via EventSource.flow).as(ContentTypes.EVENT_STREAM)
  }

  def streamSlides(session: String) = Action {
    Ok.chunked(ticker.queueSource(session) via EventSource.flow).as(ContentTypes.EVENT_STREAM)
  }

  def updateSessoinSlide(session: String) = Action { (request: Request[AnyContent]) => {
    val slide = request.body.asText.get
    mqtt.publish("/session/" + session + "/slide",slide.getBytes(),0,false);
    Ok("ok")
  }}

}
