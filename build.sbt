organization  := "com.vpon"
name          := "konfig"
scalaVersion  := "2.11.8"
crossScalaVersions  := Seq("2.11.8", "2.12.0-M4")
// description   := "Yet a scala helper library for typesafe config"

scalacOptions := Seq(
  "-deprecation"             // Emit warning and location for usages of deprecated APIs
  , "-encoding", "UTF-8"
  , "-feature"                 // Emit warning and location for usages of features that should be imported explicitly
  , "-unchecked"               // Enable additional warnings where generated code depends on assumptions
  , "-Xfatal-warnings"         // Fail the compilation if there are any warnings
  , "-Xfuture"                 // Turn on future language features
  , "-Xlint"                   // Enable specific warnings (see `scalac -Xlint:help`)
  , "-Yno-adapted-args"        // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver
  , "-Ywarn-dead-code"         // Warn when dead code is identified
  , "-Ywarn-inaccessible"      // Warn about inaccessible types in method signatures
  , "-Ywarn-infer-any"         // Warn when a type argument is inferred to be `Any`
  , "-Ywarn-nullary-override"  // Warn when non-nullary `def f()' overrides nullary `def f'
  , "-Ywarn-nullary-unit"      // Warn when nullary methods return Unit
  , "-Ywarn-numeric-widen"     // Warn when numerics are widened
  , "-Ywarn-unused"            // Warn when local and private vals, vars, defs, and types are unused
  , "-Ywarn-unused-import"     // Warn when imports are unused
  , "-Ywarn-value-discard"     // Warn when non-Unit expression results are unused
)

libraryDependencies ++= {
  Seq(
    "com.typesafe"        %   "config"        % "1.3.0",
    "com.chuusai"         %%  "shapeless"     % "2.3.1",
    "org.scalacheck"      %% "scalacheck"     % "1.13.1" % "test",
    "org.scalatest"       %% "scalatest"      % "3.0.0-M7" % "test"
  )
}

wartremoverErrors in (Compile, compile) ++= Seq(
  Wart.Any
  , Wart.Any2StringAdd
  , Wart.AsInstanceOf
  , Wart.DefaultArguments
  , Wart.EitherProjectionPartial
  , Wart.Enumeration
  // , Wart.ExplicitImplicitTypes
  , Wart.IsInstanceOf
  , Wart.JavaConversions
  , Wart.ListOps
  // , Wart.NonUnitStatements
  , Wart.Null
  , Wart.Option2Iterable
  , Wart.OptionPartial
  , Wart.Product
  , Wart.Return
  , Wart.Serializable
  // , Wart.Throw
  , Wart.TryPartial
)
