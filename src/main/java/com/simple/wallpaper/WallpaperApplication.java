package com.simple.wallpaper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
@SpringBootApplication(scanBasePackages = "com.simple")
public class WallpaperApplication {

    public static void main(String[] args) {
//        SpringApplication.run(WallpaperApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(WallpaperApplication.class);
        //设置headless=false，设置web为none
        builder.headless(false).run(args);
//        builder.headless(false).web(WebApplicationType.NONE).run(args);
    }

}
