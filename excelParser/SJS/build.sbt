name := """parserSJS"""

resolvers ++= Seq(
  "bintray-alexander_myltsev" at "http://dl.bintray.com/alexander-myltsev/maven/"
)

testFrameworks += new TestFramework("minitest.runner.Framework")
