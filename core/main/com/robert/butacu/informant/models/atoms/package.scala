package com.robert.butacu.informant.models

import java.time.Instant

import scala.concurrent.duration.FiniteDuration

package object atoms {
  case class ProcessDetails(name: String, details: Map[String, String])
  case class Totality[A](value: A)
  case class ProgressReached[A](value: A)
  case class TimeSpent(value: Instant)
  case class FailureReason[A](message: A, error: Option[Throwable])
  case class Percentage(value: Double)
  case class TimeDetails(startedAt: Instant, estimatedCompletion: Instant, reportedAt: Instant) {
    def getTimeSpent: FiniteDuration = ???
  }
}
