scalaVersion in ThisBuild := Version.scalaVersion

version in ThisBuild := Version.projectVersion

lazy val commonTestDependencies = Seq(
)

lazy val commonDependencies = libraryDependencies ++= Seq(
	"com.lihaoyi" %%% "scalarx" % "0.2.8",
	"org.parboiled" %%% "parboiled" % "2.0.1",
	"com.lihaoyi" %%% "utest" % "0.3.1" % "test",
	"org.monifu" %%% "minitest" % "0.12" % "test"
) ++ commonTestDependencies

lazy val parserJVM = (project in file("excelParser/JVM"))
	.settings(commonDependencies)

lazy val parserSJS = (project in file("excelParser/SJS"))
	.settings(commonDependencies)
	.enablePlugins(ScalaJSPlugin)

lazy val front = (project in file("front"))
	.settings(commonDependencies)
	.enablePlugins(ScalaJSPlugin)
	.dependsOn(parserSJS)

lazy val root = (project in file("."))
	.enablePlugins(ScalaJSPlugin)
	.aggregate(parserJVM, parserSJS, front)
