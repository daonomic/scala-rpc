import Dependencies.{logbackClassic, mockito, scalaCheck, scalaTest}
import bintray.BintrayPlugin.autoImport.{bintrayOrganization, bintrayPackage, bintrayPackageLabels}
import sbt.Keys._
import sbt.{Project, url}

object implicits {

  implicit class RichProject(project: Project) {
    def tests(scope: String): Project = project
      .settings(libraryDependencies += scalaTest % scope)
      .settings(libraryDependencies += scalaCheck % scope)
      .settings(libraryDependencies += mockito % scope)
      .settings(libraryDependencies += logbackClassic % scope)

    def common: Project = project.settings(
      organization := "io.daonomic.rpc",
      bintrayOrganization := Some("daonomic"),
      bintrayPackageLabels := Seq("daonomic", "rpc", "scala"),
      bintrayPackage := s"scala-rpc-${name.value}",
      licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
      version := "1.0-SNAPSHOT",
      scalaVersion := Dependencies.fullScalaVersion
    )

    def transport: Project = common
      .settings(bintrayPackage := s"scala-rpc-transport-${name.value}")

    def scalether: Project = common.settings(
      organization := "io.daonomic.scalether",
      bintrayPackage := s"scalether-${name.value}",
      bintrayPackageLabels := Seq("daonomic", "rpc", "scala", "scalether", "ethereum")
    )
  }

}
