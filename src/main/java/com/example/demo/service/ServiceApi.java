package com.example.demo.service;

import com.example.demo.Entity.*;

import java.util.List;
import java.util.Map;

public interface ServiceApi {
    String AUTHORIZE_URL = "https://accounts.spotify.com/authorize?client_id=3bcd3f05fa0b45d9a8168faf23ab8729&response_type=code&redirect_uri=https%3A%2F%2Fstringspotify.herokuapp.com%2Fcallback&scope=user-read-private%20user-read-email%20playlist-read-private%20playlist-modify-public%20playlist-modify-private";
    String GET_ACCESS = "https://accounts.spotify.com/api/token/";
    String AUTHORIZATION_TO_ACCESS = "Basic M2JjZDNmMDVmYTBiNDVkOWE4MTY4ZmFmMjNhYjg3Mjk6Yzc4YWI5YzE5YmRhNGY3MzgwMDA5M2E3NDdiMmJjODg=";
    String USER_DETAILS = "https://api.spotify.com/v1/me/";
    String SEARCH = "https://api.spotify.com/v1/search";
    String GET_YOUTUBE_SONG_NAME = "https://www.googleapis.com/youtube/v3/videos";
    String YOUTUBE_KEY = "AIzaSyCESltrZhcNxRodbjop8fMLhhtIfxD-_Wk";

    AccessToken getAccessToken(String theCode);
    AccessToken beforeCall();
    User getUserDetails();
    Playlist createPlaylist(String playlistName);
    List<com.example.demo.Entity.playlists.Item> getPlaylists();
    Item searchTrack(String trackToFind);
    List<Item> searchTracks(List<String> tracksToFind);
    Boolean addTracks(Uri uris, String playlistID);
    Map<Boolean, List<String>> submitAddAllTracks(List<String> tracks, String playlistID);
    /**
     *
     * @param v either the song name or the youtube URL, if youtube URL, returns the name cleared
     * @return track name cleared and ready to be searched
     */
    String getClearSongName(String v);

    void test(AccessToken accessToken);




}
