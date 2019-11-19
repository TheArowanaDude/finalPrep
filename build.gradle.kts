plugins {
  java
  jacoco
  pmd
}      

repositories {
	mavenCentral()
}

dependencies {
    testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testCompile("org.mockito:mockito-core:2.2.26")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testRuntime("org.junit.platform:junit-platform-console:1.2.0")
    compile("net.iakovlev:timeshape:2018d.6")
    compile("org.json:json:20190722")

}

sourceSets {
    main {
        java.srcDirs("src")
    }
    test {
        java.srcDirs("test")
    }
}

tasks {
    val treatWarningsAsError =
            listOf("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")

    getByName<JavaCompile>("compileJava") {
        options.compilerArgs = treatWarningsAsError
    }

    getByName<JavaCompile>("compileTestJava") {
        options.compilerArgs = treatWarningsAsError
    }
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform {}
}
 
pmd {
    ruleSets = listOf()
    ruleSetFiles = files("../conf/pmd/ruleset.xml")
}

defaultTasks("clean", "test", "jacocoTestReport", "pmdMain")