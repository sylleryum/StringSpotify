package com.example.demo.service;

import com.example.demo.Entity.*;
import com.example.demo.Entity.playlists.PlaylistList;
import com.example.demo.Entity.youtube.Youtube;
import com.example.demo.helper.SongTitleUtil;
import com.example.demo.helper.YoutubeUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceApiImpl implements ServiceApi {


    private AccessToken accessToken;
    private RestTemplate template = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private MultiValueMap<String, String> bodyParameters = new LinkedMultiValueMap<>();
    private User user;


    SongTitleUtil songTitleUtil;
    YoutubeUtil youtubeUtil;
    HttpEntity<MultiValueMap<String, String>> requestEntity;

//    @Autowired
//    SongTitleUtilImpl songMatcher;


    public ServiceApiImpl(SongTitleUtil songTitleUtil, YoutubeUtil youtubeUtil) {
        this.songTitleUtil = songTitleUtil;
        this.youtubeUtil = youtubeUtil;
    }


    @Override
    public AccessToken getAccessToken(String theCode) {
        cleaner();

        System.out.println("new token request");
        headers.add("Authorization", ServiceApi.AUTHORIZATION_TO_ACCESS);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        bodyParameters.add("grant_type", "authorization_code");
        bodyParameters.add("code", theCode);
        bodyParameters.add("redirect_uri", "https://stringspotify.herokuapp.com/callback");
        requestEntity = new HttpEntity<>(bodyParameters, headers);

        return tokenCall(requestEntity);
    }

    /**
     * clean variables and check for a valid access token, if token is valid, only clean, if expired, request a refresh one
     *
     * @return AccessToken if it was expired
     */
    @Override
    public AccessToken beforeCall() {
        cleaner();

        if (accessToken == null) {
            System.out.println("I has no token hurdurrr");
            return null;
        } else if (accessToken.getValidity() < System.currentTimeMillis()) {
            System.out.println("refreshing token");
            headers.add("Authorization", ServiceApi.AUTHORIZATION_TO_ACCESS);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            bodyParameters.add("grant_type", "refresh_token");
            bodyParameters.add("refresh_token", accessToken.getRefreshToken());
            requestEntity = new HttpEntity<>(bodyParameters, headers);
            return tokenCall(requestEntity);
        } else {
            return accessToken;
        }

    }

    /**
     * get users details and set the current user in the service
     *
     * @return User
     */
    @Override
    public User getUserDetails() {
        if (beforeCall() == null) return null;

        headers.add("Authorization", "Bearer " + accessToken.getAccessToken());

        try {
            HttpEntity<String> headParameters = new HttpEntity<>("paramenters", headers);
            //ResponseEntity<User> response = template.exchange(ServiceApi.USER_DETAILS, HttpMethod.GET, headParameters, User.class);
            ResponseEntity<User> response = template.exchange(ServiceApi.USER_DETAILS, HttpMethod.GET, headParameters, User.class);
            //System.out.println("user details " + response.getStatusCode() + " " + response.getBody());
            user = response.getBody();
            return response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getMessage());
            System.out.println("Fail " + e.getResponseBodyAsString());
            return null;
        }

    }

    @Override
    public Playlist createPlaylist(String playlistName) {
        if (beforeCall() == null) return null;
        if (user == null) user = getUserDetails();
        String URL_PLAYLIST = "https://api.spotify.com/v1/users/" + user.getId() + "/playlists";

        headers.add("Authorization", "Bearer " + accessToken.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreatePlaylist createPlaylist = new CreatePlaylist(playlistName);
        HttpEntity<CreatePlaylist> request = new HttpEntity<>(createPlaylist, headers);

        try {
            Playlist result = template.postForObject(URL_PLAYLIST, request, Playlist.class);
            System.out.println("playlist created " + result);
            return result;
        } catch (RestClientResponseException e) {
            System.out.println(e.getMessage());
            System.out.println("Fail " + e.getResponseBodyAsString());
            return null;
        }
    }

    /**
     * get playlists owned by user
     * @return
     */
    @Override
    public List<com.example.demo.Entity.playlists.Item> getPlaylists() {
        if (beforeCall() == null) return null;
        if (user == null) user = getUserDetails();
        String URL_PLAYLIST = "https://api.spotify.com/v1/users/" + user.getId() + "/playlists";

        headers.add("Authorization", "Bearer " + accessToken.getAccessToken());

        try {
            HttpEntity<String> headParameters = new HttpEntity<>("paramenters", headers);
            ResponseEntity<PlaylistList> response = template.exchange(URL_PLAYLIST, HttpMethod.GET, headParameters, PlaylistList.class);

            List<com.example.demo.Entity.playlists.Item> listReturn = new ArrayList<>();
            for (com.example.demo.Entity.playlists.Item item : response.getBody().getItems()) {
                if (item.getOwner().getId().equalsIgnoreCase(user.getId())) {
                    listReturn.add(item);
                }
            }

            return listReturn;
        } catch (RestClientResponseException e) {
            System.out.println(e.getMessage());
            System.out.println("Fail " + e.getResponseBodyAsString());
            return null;
        }

    }

    @Override
    public Item searchTrack(String trackToFind) {
        if (beforeCall() == null || trackToFind.isEmpty()) {
            return null;
        }
        if (user == null) user = getUserDetails();

        String theString = songTitleUtil.clearSpecialCharSong(trackToFind).replace(" ", "+");

        headers.add("Authorization", "Bearer " + accessToken.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServiceApi.SEARCH)
                .queryParam("type", "track")

                .queryParam("market", user.getCountry())
                .queryParam("q", theString);

        try {
            HttpEntity<SearchResult> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, SearchResult.class);
            //System.out.println("result "+response.getBody());
            //System.out.println("you got here " + response.getBody());
            System.out.println("result " + response.getBody().getTracks().getItems().get(0).getArtists().get(0).getName() + " = " +
                    response.getBody().getTracks().getItems().get(0).getName() + " " + response.getBody().getTracks().getItems().get(0).getUri());
            return response.getBody().getTracks().getItems().get(0);
//            if (response.getBody().getTracks().getItems().size()>0){
//
//            } else {
//                System.out.println("spotify track not found item<1 " + trackToFind);
//                return new Item(trackToFind);
//            }

        } catch (RestClientResponseException e) {
            //System.out.println(e.getMessage());
            System.out.println("Fail to find spotify track " + e.getResponseBodyAsString()+" "+trackToFind);
            return new Item(trackToFind);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("other fail to find spotify track " + trackToFind);
            return new Item(trackToFind);
        }
    }


    @Override
    public List<Item> searchTracks(List<String> tracksToFind) {
        List<Item> tracksfound = new ArrayList<>();
        for (String s : tracksToFind) {
            tracksfound.add(searchTrack(s));

        }
        System.out.println("searchTracks, tracks found ");

        for (Item i: tracksfound){
            System.out.println(i.getArtists().get(0).getName()+" + "+i.getName());
        }
        //System.out.println("tracks found: "+tracksfound);
        return tracksfound;
    }

    @Override
    public Boolean addTracks(Uri uris, String playlistID) {
        if (beforeCall() == null || playlistID.isEmpty()) {
            return null;
        }
        if (uris.getUris().size()<1){
            return false;
        }

        String URL = "https://api.spotify.com/v1/playlists/" + playlistID + "/tracks";
        headers.add("Authorization", "Bearer " + accessToken.getAccessToken());
        HttpEntity<Uri> request = new HttpEntity<>(uris, headers);

        try {
            ///////////////String s = template.postForObject(URL, requestEntity, String.class);
            String string = template.postForObject(URL, request, String.class);
            System.out.println("Tracks added " + string);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            //System.out.println("Fail- " + e.getResponseBodyAsString());
            return false;
        }
    }

    @Override
    public Map<Boolean, List<String>> submitAddAllTracks(List<String> tracks, String playlistID) {


        //List<String> listResult = songTitleUtil.clearSpecialCharSongList(tracks);

        //get yt tracks
//        List<String> trackList = listResult.stream().map(l -> songTitleUtil.clearSpecialCharSong(l)).collect(Collectors.toList());
//        System.out.println();
//        List<Item> listItem = trackList.stream().map(lItem->searchTrack(lItem)).collect(Collectors.toList());

        //-------working
        /*List<String> uriList = tracks.parallelStream().map(l -> getClearSongName(l)).map(lItem->searchTrack(lItem).getUri()).collect(Collectors.toList());
        addTracks(new Uri(uriList), playlistID);*/
        //-------
        Map<Boolean, List<String>> mapYt = tracks.stream().map(i->getClearSongName(i)).collect(Collectors.partitioningBy(str-> !str.contains("\b")));
        Map<Boolean, List<Item>> mapSp = mapYt.get(true).stream().map(e->searchTrack(e)).collect(Collectors.partitioningBy(it->it.getUri()!=null));

        List<String> listFailed = mapSp.get(false).stream().map(i->i.getName()).collect(Collectors.toList());
        //listFailed.addAll(mapYt.get(false));
        ///////////List<String> listToAdd = mapSp.get(true).stream().map(i->i.getUri()).collect(Collectors.toList());

        List<String> listToAdd = new ArrayList<>();
        List<String> listSuccessReturn = new ArrayList<>();
        for(Item i: mapSp.get(true)){
            listToAdd.add(i.getUri());
            listSuccessReturn.add(i.getArtists().get(0).getName()+" - "+i.getName());
        }

        List<String> failedSongs = mapYt.get(false);
        failedSongs.addAll(listFailed);
        Map<Boolean, List<String>> mapReturn = new HashMap<>();
        mapReturn.put(false, failedSongs);
        if (addTracks(new Uri(listToAdd),playlistID)){

            mapReturn.put(true, listSuccessReturn);

        } else {
            mapReturn.put(true, null);

        }
        return mapReturn;


    }

    /**
     *
     * @param url either the song name or the youtube URL, if youtube URL, returns the name cleared
     * @return track name cleared and ready to be searched
     */
    @Override
    public String getClearSongName(String url) {
        cleaner();

        String sTrim = url.trim();
        if (sTrim.substring(0,4).equalsIgnoreCase("http")){
            //System.out.println("youtube");
            //return clearYoutubeChar(youtubeUtil.getSongName(sTrim));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServiceApi.GET_YOUTUBE_SONG_NAME)
                .queryParam("part", "snippet")
                .queryParam("key", ServiceApi.YOUTUBE_KEY)
                .queryParam("id", songTitleUtil.getYoutubeId(url));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<Youtube> response = template.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Youtube.class);
        try {
            return songTitleUtil.clearYoutubeChar(response.getBody().getItems().get(0).getSnippet().getTitle());
        } catch (RestClientResponseException e){
            //System.out.println(e.getMessage());
            System.out.println("Fail to get youtube song - " + e.getResponseBodyAsString()+" "+url);
            return "\b"+url;
        } catch (Exception e){
            System.out.println("other small");
            return "\b"+url;
        }

        } else {
            return url;
        }

    }

    //=========================internal methods

    private void cleaner() {
        headers.clear();
        bodyParameters.clear();
        //bodyParameters.clear();
    }

    private AccessToken tokenCall(HttpEntity<MultiValueMap<String, String>> requestEntityCall) {
        try {
            accessToken = template.postForObject(ServiceApi.GET_ACCESS, requestEntity, AccessToken.class);
            accessToken.setValidity(3600000 + System.currentTimeMillis());
            System.out.println("Access acquired " + accessToken + " **" + accessToken.getValidity());
            return accessToken;
        } catch (RestClientResponseException e) {
            System.out.println(e.getMessage());
            System.out.println("Fail " + e.getResponseBodyAsString());
            accessToken = null;
            return null;
        }

    }

    @Override
    public void test(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
