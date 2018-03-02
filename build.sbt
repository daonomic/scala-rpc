import java.util.Properties

val nexusProperties = settingKey[Properties]("Nexus properties")

name := "scala-rpc"

version := "0.1.0-SNAPSHOT"

scalaVersion := Versions.scala

def base(project: Project): Project = project
  .settings(organization := "io.daonomic")
  .settings(nexusProperties := {
    val prop = new Properties()
    IO.load(prop, Path.userHome / ".ivy2" / ".nexus")
    prop
  })
  .settings(publishTo := {
    val nexus = nexusProperties.value.getProperty("url")
    if (isSnapshot.value)
      Some("snapshots" at nexus + "/content/repositories/snapshots/")
    else
      Some("releases" at nexus + "/content/repositories/releases/")
  })
  .settings(credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"))

def common(project: Project): Project = base(project)
  .dependsOn(domain, `test-common` % "test")

lazy val `test-common` = base(project)

lazy val domain = base(project)

lazy val core = common(project)
  .dependsOn(domain)

lazy val `scalaj-http` = common(project)
  .dependsOn(core)

lazy val root = base(project in file(".")).
  aggregate(core, `scalaj-http`)
