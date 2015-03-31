scalaVersion in ThisBuild := Version.scalaVersion

version in ThisBuild := Version.projectVersion

lazy val commonTestDependencies = Seq(
)

lazy val commonDependencies = libraryDependencies ++= Seq(
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
