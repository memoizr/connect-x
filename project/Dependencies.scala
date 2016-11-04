import sbt._
import Keys._

object Dependencies extends Build {
  def scalatest = "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  def fs2 = "co.fs2" %% "fs2-core" % "0.9.2"
  def mockito = "org.mockito" % "mockito-core" % "1.8.5" % "test"
  def rxScala = "io.reactivex" %% "rxscala" % "0.26.3"
}

