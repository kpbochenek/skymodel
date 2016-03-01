package com.kpbochenek.skymodel

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.event.LoggingReceive

import scala.collection.mutable
import scala.swing.{MainFrame, SimpleSwingApplication}

class MonitorActor extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  var nodes: mutable.Map[String, String] = mutable.Map()

  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }

  override def postStop(): Unit = cluster.unsubscribe(self)

  override def receive: Receive = LoggingReceive.withLabel("Monitoring") {
    case MemberUp(member) =>
      log.info("Member is Up: {}", member.address)
      nodes(member.address.toString) = member.status.toString + "(UP!)"
      drawClusterStatus()
    case UnreachableMember(member) =>
        log.info("Member detected as unreachable: {}", member)
      nodes(member.address.toString) = member.status.toString + "(UNREACHABLE!)"
      drawClusterStatus()
    case MemberRemoved(member, previousStatus) =>
        log.info("Member is Removed: {} after {}", member.address, previousStatus)
      nodes(member.address.toString) = member.status.toString + "(REMOVED!)"
      drawClusterStatus()
    case MemberJoined(member) =>
      log.info("Member joined cluster: {}", member)
      drawClusterStatus()
    case x: MemberEvent =>
      println(s"IGNORING $x")
      drawClusterStatus()
  }

  def drawClusterStatus(): Unit = {
    println("-------------------------------------------------------")
    for (n <- nodes) {
      println(s"$n")
    }
    println("-------------------------------------------------------")
  }
}



object Monitoring extends SimpleSwingApplication {

  val system = ActorSystem("SkyModel")
  system.actorOf(Props[MonitorActor])

  def top = new MainFrame {
    // top is a required method
    title = "A Sample Scala Swing GUI"
  }
}
