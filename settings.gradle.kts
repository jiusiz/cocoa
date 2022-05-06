rootProject.name = "cocoa"
include("cocoa-core")
include("cocoa-spring-boot-starter")

dependencyResolutionManagement{
    repositories{
//        maven{
//            url = java.net.URI("https://maven.aliyun.com/repository/public/")
//        }
//        mavenLocal()
        mavenCentral()
    }
}
