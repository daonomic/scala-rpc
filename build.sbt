name := "scala-rpc"
scalaVersion := Versions.scala

def base(project: Project): Project = project.settings(
  organization := "io.daonomic.rpc",
  bintrayOrganization := Some("daonomic"),
  bintrayPackageLabels := Seq("daonomic", "rpc", "scala"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  version := "0.1.2",
  scalaVersion := Versions.scala
)

def common(project: Project): Project = base(project)
  .dependsOn(domain, `test-common` % "test")

lazy val `test-common` = base(project)

lazy val domain = base(project)

lazy val core = common(project)
  .dependsOn(domain)

lazy val `transport-try` = common(project)
  .dependsOn(core)

lazy val `transport-mono` = common(project)
  .dependsOn(core)

lazy val root = base(project in file("."))
  .settings(publish := {})
  .aggregate(domain, core, `transport-try`, `transport-mono`)
