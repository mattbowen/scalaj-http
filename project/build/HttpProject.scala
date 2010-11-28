import sbt._
import sbt.Process._
import com.weiglewilczek.bnd4sbt.BNDPlugin

class HttpProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
  override def managedStyle = ManagedStyle.Maven
  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)
  override def packageSrcJar= defaultJarPath("-sources.jar")
  val sourceArtifact = Artifact.sources(artifactID)
  override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageSrc)
  override def bndExportPackage = Seq("org.scalaj;version=\"0.2.2\"")

  val specs = if (buildScalaVersion.startsWith("2.7.")) {
    "org.scala-tools.testing" % "specs" % "1.6.2.2" % "test" withSources()
  } else {
    "org.scala-tools.testing" %% "specs" % "1.6.5" % "test" withSources()
  }
}
