import Dependencies.{logbackClassic, mockito, scalaCheck, scalaTest}
import sbt.Keys._
import sbt.io.{Path, RichFile}
import sbt.librarymanagement.DependencyBuilders.RepositoryName
import sbt.librarymanagement.ivy.Credentials
import sbt.{Project, url}

object implicits {

  val bintrayUser = sbt.settingKey[String]("Bintray user name")
  val bintrayRepository = sbt.settingKey[String]("Bintray repository name")
  val bintrayPackage = sbt.settingKey[String]("Bintray package name")

  implicit class RichProject(project: Project) {
    def tests(scope: String): Project = project
      .settings(libraryDependencies += scalaTest % scope)
      .settings(libraryDependencies += scalaCheck % scope)
      .settings(libraryDependencies += mockito % scope)
      .settings(libraryDependencies += logbackClassic % scope)

    def publish: Project = project.settings(
      bintrayUser := "daonomic",
      bintrayRepository := "maven",
      bintrayPackage := name.value,
      credentials += {
        if (isSnapshot.value) {
          Credentials((Path.userHome: RichFile) / ".ivy2/nexus.credentials")
        } else {
          Credentials((Path.userHome: RichFile) / ".ivy2/bintray.credentials")
        }
      },
      publishTo := {
        if (isSnapshot.value) {
          Some(("snapshots": RepositoryName) at "http://10.7.3.6:8081/nexus/content/repositories/snapshots/")
        } else {
          Some(("releases": RepositoryName) at s"https://api.bintray.com/maven/${bintrayUser.value}/${bintrayRepository.value}/${bintrayPackage.value}/;publish=1")
        }
      }
    )

    def common: Project = publish.settings(
      organization := "io.daonomic.rpc",
      bintrayPackage := s"scala-rpc-${name.value}",
      licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
      version := "0.1.6",
      scalaVersion := Dependencies.fullScalaVersion
    )

    def transport: Project = common
      .settings(bintrayPackage := s"scala-rpc-transport-${name.value}")

    def scalether: Project = common.settings(
      organization := "io.daonomic.scalether",
      bintrayPackage := s"scalether-${name.value}"
    )

    def bitcoin: Project = common.settings(
      organization := "io.daonomic.bitcoin.rpc",
      bintrayPackage := s"bitcoin-${name.value}"
    )

    def blockchain: Project = common.settings(
      organization := "io.daonomic.blockchain",
      bintrayPackage := s"blockchain-${name.value}"
    )
  }
}
