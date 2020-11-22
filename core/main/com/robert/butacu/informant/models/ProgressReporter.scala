package com.robert.butacu.informant.models

import java.time.Instant

import cats.effect.Clock
import com.robert.butacu.informant.models.atoms._

import scala.language.higherKinds

trait Estimator[A] {
  def estimate(current: A, total: A):      Instant
  def getPercentage(current: A, total: A): Percentage
}

case class ProgressStatus[A: Estimator](progress: ProgressReached[A], total: Totality[A])

case class Progress[A: Estimator](
                                   name:        ProcessDetails,
                                   total:       Totality[A],
                                   progressed:  ProgressStatus[A],
                                   timeDetails: TimeDetails
                                 )

case class InterruptedReport[A: Estimator](progress: Progress[A], reason: FailureReason[A]) {
  def timeSpent: TimeSpent = ??? // get from progress -> time details
}
case class Report[A: Estimator](progress: Progress[A], timeSpent: TimeSpent)

trait ProgressReporter[F[_]] {
  implicit val clock: Clock[F]
  def reportInterrupt[A: Estimator](progress: Progress[A]):                     F[InterruptedReport[A]]
  def reportCompleted[A: Estimator](progress: Progress[A]):                     F[Report[A]]
  def update[A: Estimator](current: Progress[A], progress: ProgressReached[A]): F[Progress[A]]
  def start[A: Estimator](process: ProcessDetails, total: Totality[A]):         F[Progress[A]]
}
