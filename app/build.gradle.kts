apply(plugin = "com.github.johnrengelman.shadow")

dependencies {
    val vertxVersion = project.extra["vertxVersion"]
    val jupiterVersion = project.extra["jupiterVersion"]
    val logbackClassicVersion = project.extra["logbackClassicVersion"]
    val restAssuredVersion = project.extra["restAssuredVersion"]
    val assertjVersion = project.extra["assertjVersion"]
    val nettyResolverVersion = project.extra["nettyResolverVersion"]

    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-config:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")
    implementation("io.vertx:vertx-config-yaml:$vertxVersion")
    implementation("io.vertx:vertx-service-proxy:$vertxVersion")
    implementation("io.vertx:vertx-service-discovery:$vertxVersion")

    implementation("io.vertx:vertx-codegen:$vertxVersion:processor")

    annotationProcessor("io.vertx:vertx-codegen:$vertxVersion:processor")
    annotationProcessor("io.vertx:vertx-service-proxy:$vertxVersion")

    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:$jupiterVersion")
    testImplementation("io.vertx:vertx-junit5:$vertxVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")

    runtimeOnly("io.netty:netty-resolver-dns-native-macos:$nettyResolverVersion:osx-x86_64")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}

application {
    mainClass.set("com.japharr.uploader.MainVerticle")
}

tasks.test {
    useJUnitPlatform()
}