plugins {
    `java-library`
}

dependencies {
    implementation(project(":commons-spring-encrypt"))

    implementation("org.springframework.boot:spring-boot-starter-security:2.7.2")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.7.2")

    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")

}