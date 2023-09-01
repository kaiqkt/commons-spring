plugins {
    `java-library`
}

dependencies {
    implementation(project(":commons-spring-encrypt"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")

}