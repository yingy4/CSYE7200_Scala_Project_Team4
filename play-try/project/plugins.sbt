// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.12")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"


addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.12")