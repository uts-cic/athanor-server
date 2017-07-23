name := "athanorserver-server"
version := "0.1"
scalaVersion := "2.12.2"
organization := "au.edu.utscic"

// RUN sbt dependencyUpdates to check dependency version

// RUN sbt universal:packageZipTarball to create a tar package for upload to server
// ensure that JavaAppPackaging is enabled - disable for Travis CI
//enablePlugins(JavaAppPackaging)

coverageEnabled := true


//Scala library versions
val akkaVersion = "2.5.3"
val akkaStreamVersion = "2.5.3"
val akkaHttpVersion = "10.0.9"
val akkaHttpJson4sVersion = "1.17.0"
val json4sVersion = "3.5.2"
val slf4jVersion = "1.7.25"
val logbackVersion = "1.2.3"
val scalatestVersion = "3.0.3"
val nlytxCommonsVersion = "0.1.1"

//Java library versions
val coreNlpVersion = "3.8.0"
val jsonassertVersion = "1.5.0"

//Akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-stream_2.12" % akkaStreamVersion,
  "com.typesafe.akka" % "akka-stream-testkit_2.12" % akkaStreamVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
)
//NLP dependencies
libraryDependencies ++= Seq(
  "edu.stanford.nlp" % "stanford-corenlp" % coreNlpVersion,
  "edu.stanford.nlp" % "stanford-corenlp" % coreNlpVersion  classifier "models-english"
)

//General
libraryDependencies ++= Seq(
  "io.nlytx" %% "commons" % nlytxCommonsVersion,
  //  "com.typesafe" % "config" % "1.3.1",
  "org.json4s" %% "json4s-jackson" % json4sVersion,
  "de.heikoseeberger" %% "akka-http-json4s" % akkaHttpJson4sVersion,
  "org.skyscreamer" % "jsonassert" % jsonassertVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.slf4j" % "jcl-over-slf4j" % slf4jVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion
)

scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/src/main/scala/root-doc.md")

resolvers += Resolver.bintrayRepo("nlytx", "nlytx_commons")

coverageMinimum := 70

coverageFailOnMinimum := false

coverageHighlighting := true

publishArtifact in Test := false

parallelExecution in Test := false