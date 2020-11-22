package com.robert.butacu.informant.models

import java.time.Instant
import cats.effect.Clock
import scala.language.higherKinds

trait Estimator[A] {
  def estimate(current: A, total: A): Instant
}

case class Progress[A: Estimator](name:               ProcessDetails,
                                  total:              Totality[A],
                                  progressed:         ProgressReached[A],
                                  reportedAt:         Instant,
                                  expectedToFinishAt: Instant)

case class InterruptedReport[A: Estimator](progress: Progress[A], timeSpent: TimeSpent, reason: FailureReason[A])
case class Report[A: Estimator](progress: Progress[A], timeSpent: TimeSpent)

trait ProgressReporter[F[_]] {
  def reportInterrupt[A: Estimator](progress: Progress[A])(implicit clock: Clock[F]): F[InterruptedReport[A]]
  def reportCompleted[A: Estimator](progress: Progress[A])(implicit clock: Clock[F]): F[Report[A]]
  def updateProgress[A: Estimator](current: Progress[A])(implicit clock: Clock[F]): F[Progress[A]]
}
