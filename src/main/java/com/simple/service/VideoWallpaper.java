package com.simple.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("video")
@Service
public class VideoWallpaper implements Wallpaper {
    @Override
    public void load() {
        System.out.println("video");
    }
}
