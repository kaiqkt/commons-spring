plugins {
    `java-library`
}

dependencies {
    implementation(project(":commons-spring-encrypt"))

    implementation("org.springframework.boot:spring-boot-starter-security:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.2.0")

    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")

}