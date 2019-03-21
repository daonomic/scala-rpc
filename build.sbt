import implicits._
import Dependencies._

name := "scala-rpc"
scalaVersion := fullScalaVersion

parallelExecution in ThisBuild := false

//common rpc
lazy val `test-common` = project.common.tests("compile")
  .dependsOn(`scalether-domain`)

lazy val util = project.common

lazy val domain = project.common
  .dependsOn(util)

lazy val cats = project.common
  .settings(organization := "io.daonomic.cats")
  .settings(bintrayPackage := "mono-cats")

lazy val core = project.common
  .dependsOn(domain, cats)

lazy val `transport-try` = (project in file("transport/try"))
  .transport
  .dependsOn(core, `test-common` % "test")

lazy val `transport-id` = (project in file("transport/id"))
  .transport
  .dependsOn(core, `test-common` % "test")

lazy val `transport-mono` = (project in file("transport/mono"))
  .transport
  .tests()
  .dependsOn(core, `test-common` % "test")

lazy val `transport-sttp` = (project in file("transport/sttp"))
  .transport
  .dependsOn(core, `test-common` % "test")

//blockchain common
lazy val `blockchain-poller` = (project in file("blockchain/poller"))
  .tests()
  .blockchain
  .tests("test")
  .dependsOn(cats)

lazy val `blockchain-listener` = (project in file("blockchain/listener"))
  .blockchain
  .tests()
  .dependsOn(`blockchain-poller`)

//bitcoin
lazy val `bitcoin-domain` = (project in file("bitcoin/domain"))
  .bitcoin
  .tests()

lazy val `bitcoin-core` = (project in file("bitcoin/core"))
  .bitcoin
  .dependsOn(core, `bitcoin-domain`, cats, `test-common` % "test")

lazy val `bitcoin-listener` = (project in file("bitcoin/listener"))
  .bitcoin
  .dependsOn(`blockchain-listener`, `bitcoin-core`, `test-common` % "test")

lazy val `bitcoin-test` = (project in file("bitcoin/test"))
  .bitcoin
  .dependsOn(`bitcoin-listener`, `transport-try`, `transport-mono`, `test-common` % "test")
  .settings(skip in publish := true)

//scalether
lazy val `scalether-util` = (project in file("scalether/util"))
  .scalether
  .dependsOn(util)
  .tests()

lazy val `scalether-domain` = (project in file("scalether/domain"))
  .scalether
  .tests()
  .dependsOn(domain, `scalether-util`)

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

lazy val `scalether-generator` = (project in file("scalether/generator"))
  .scalether
  .dependsOn(`test-common` % "test")

lazy val `scalether-transport-mono` = (project in file("scalether/transport-mono"))
  .scalether
  .dependsOn(`scalether-core`, `transport-mono`)

lazy val `scalether-test` = (project in file("scalether/test"))
  .scalether
  .dependsOn(`scalether-contract`, `scalether-listener`, `transport-try`, `test-common` % "test", `transport-mono`)
  .settings(skip in publish := true)

lazy val root = (project in file("."))
  .common
  .settings(skip in publish := true)
  .aggregate(
    util, domain, cats, core,
    `transport-try`, `transport-sttp`, `transport-mono`, `transport-id`,
    `blockchain-poller`, `blockchain-listener`,
    `scalether-util`, `scalether-domain`, `scalether-core`, `scalether-abi`, `scalether-transaction`,
    `scalether-listener`, `scalether-contract`, `scalether-generator`, `scalether-test`, `scalether-transport-mono`,
    `bitcoin-domain`, `bitcoin-core`, `bitcoin-listener`
  )
