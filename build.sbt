name := "scala-rpc"
scalaVersion := Dependencies.scalaVersion

def base(project: Project): Project = project.settings(
  organization := "io.daonomic.rpc",
  bintrayOrganization := Some("daonomic"),
  bintrayPackageLabels := Seq("daonomic", "rpc", "scala"),
  bintrayPackage := s"scala-rpc-${name.value}",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  version := "1.0-SNAPSHOT",
  scalaVersion := Dependencies.scalaVersion
)

def common(project: Project): Project = base(project)
  .dependsOn(domain, `test-common` % "test")

def transport(project: Project): Project = common(project).settings(
  bintrayPackage := s"scala-rpc-transport-${name.value}"
)

lazy val `test-common` = base(project)

lazy val domain = base(project)

lazy val cats = base(project)

lazy val core = common(project)
  .dependsOn(domain)

lazy val `transport-try` = transport(project in file("transport/try"))
  .dependsOn(core)

lazy val `transport-mono` = transport(project in file("transport/mono"))
  .dependsOn(core)

lazy val root = base(project in file("."))
  .settings(publish := {})
  .aggregate(domain, core, `transport-try`, `transport-mono`)
