name := """parserSJS"""

resolvers ++= Seq(
  "bintray-alexander_myltsev" at "http://dl.bintray.com/alexander-myltsev/maven/"
)

//testFrameworks += new TestFramework("utest.runner.Framework")
testFrameworks += new TestFramework("minitest.runner.Framework")

libraryDependencies ++= Seq(
	"org.parboiled" %%% "parboiled" % "2.0.1",
  "com.lihaoyi"   %%% "utest" % "0.3.1" % "test",
  "org.monifu" %% "minitest" % "0.12" % "test"
)
