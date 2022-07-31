package com.simple.service;

import com.simple.Tool.FrameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("image")
@Service
public class ImageWallpaper implements Wallpaper {

    @Value("${wallpaper.img.dir}")
    private String imgDir;

    @Value("${wallpaper.img.switch.seconds}")
    private int switchSeconds;

    @Override
    public void load() {
        System.out.println("img");
        FrameUtils.callImageFrame(imgDir,switchSeconds);
    }
}
