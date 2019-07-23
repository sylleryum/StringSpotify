package com.example.demo.helper;

import com.example.demo.Entity.Item;

import java.util.List;

public interface SongTitleUtil {
    //for spotify songs - paranoid android - remastered
    String clearRemastered(String trackName);

    String clearSpecialCharSong(String trackName);
    List<String> clearSpecialCharSongList(List<String> songList);
    String clearYoutubeChar(String trackName);
    String getYoutubeId(String url);

    String clearSpecialChar(String trackName);
}
