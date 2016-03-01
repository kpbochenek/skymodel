package com.kpbochenek.skymodel

import akka.actor.ActorSystem

object SkyModel {

  def main(args: Array[String]) {
    println("Starting Actor System")
    val system = ActorSystem("SkyModel")

    println("Done")
  }
}
