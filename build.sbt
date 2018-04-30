import implicits._
import Dependencies._

name := "scala-rpc"
scalaVersion := fullScalaVersion

parallelExecution in ThisBuild := false

//common rpc
lazy val `test-common` = project.common.tests("compile")
  .dependsOn(`scalether-domain`)

lazy val domain = project.common

lazy val cats = project.common

lazy val core = project.common
  .dependsOn(domain)

lazy val `transport-try` = (project in file("transport/try"))
  .transport
  .dependsOn(core, `test-common`)

lazy val `transport-mono` = (project in file("transport/mono"))
  .transport
  .dependsOn(core, `test-common`)

//blockchain common
lazy val `blockchain-poller` = (project in file("blockchain/poller"))
  .blockchain
  .dependsOn(cats)

lazy val `blockchain-listener` = (project in file("blockchain/listener"))
  .blockchain
  .tests("test")
  .dependsOn(`blockchain-poller`)

//scalether
lazy val `scalether-util` = (project in file("scalether/util"))
  .scalether
  .tests("test")

lazy val `scalether-domain` = (project in file("scalether/domain"))
  .scalether
  .tests("test")
  .dependsOn(`scalether-util`)

lazy val `scalether-core` = (project in file("scalether/core"))
  .scalether
  .dependsOn(core, `scalether-util`, `scalether-domain`, cats, `test-common` % "test")

lazy val `scalether-abi` = (project in file("scalether/abi"))
  .scalether
  .dependsOn(`scalether-core`, `test-common` % "test")

lazy val `scalether-transaction` = (project in file("scalether/transaction"))
  .scalether
  .dependsOn(`blockchain-poller`, `scalether-core`, `test-common` % "test")

lazy val `scalether-listener` = (project in file("scalether/listener"))
  .scalether
  .dependsOn(`blockchain-listener`, `scalether-core`, `test-common` % "test")

lazy val `scalether-contract` = (project in file("scalether/contract"))
  .scalether
  .dependsOn(`scalether-abi`, `scalether-transaction`, `test-common` % "test")

lazy val root = (project in file("."))
  .common
  .settings(publish := {})
  .aggregate(
    domain, core,
    `blockchain-poller`, `blockchain-listener`,
    `transport-try`, `transport-mono`,
    `scalether-util`, `scalether-domain`, `scalether-core`, `scalether-abi`, `scalether-transaction`, `scalether-listener`, `scalether-contract`
  )
