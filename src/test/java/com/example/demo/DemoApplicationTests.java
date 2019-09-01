package com.example.demo;


import com.example.demo.Entity.*;
import com.example.demo.helper.SongTitleUtil;
import com.example.demo.helper.YoutubeUtil;
import com.example.demo.service.ServiceApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    String baseUrl = "https://accounts.spotify.com/api/token";
    String CODE = "AQDfvwVJkR2UuDIKfwRNAqxUKRoL9HAe_foyboL9t03cR_YGHqYdTmb8crpSmks93dvKQuft99IiUPbM-f2MpSz1wI4kZpJF0LUbQ38awFVwdjiyKHGKDWzk1X4F_8jFTGCKaDCcBwK_RSfUZfAyDshilkPBg582MT-S2GBaOagptElx64w0S4C8eyVFM9fXrDZWlFik2Tj9gQyUOxY2Wrk5gOXEOHkyYnmz67Ve3FZ2gXuiJFVc65PBFJcNj8hp4gdF6_wCv6dFZAE7bzOvC15u6_OhlFlt7LLIEOR-aZjWgVci3xo5_7i7c_bR0Cz_7S_YcxFya1cs1e3D6O1AFIHOwMO28aMLa7tEmkJb";
    String AUTHORIZATION = "Bearer BQB0Q5ThoEzmdqXwLH7q0_KoFNjw3Pgl80xFBa_PKIcQmk0S7d8AR9GkbLgiXbmjYC8tQYPQhmGQgn3Z3i8pXuvp15awKZy1pd214ogATe2eyNu1Z3UQhXMo6JOC5eDYsqkAzc6cg3T6YRMnlDD3w0BKbUn1MdKt8hm2QCO-P7s5ZEfakiqm85fPl_U8YWyiQBQbdVvxWjMKl2wYB3dc1XMoGMmJX6O-C8hVVu03dH0";

    @Autowired
    SongTitleUtil songTitleUtil;
    @Autowired
    YoutubeUtil youtubeUtil;
    @Autowired
    ServiceApi serviceApi;

    @Test
    public void newTest(){
        String aString = "A-87B9C143D24E940|jOmbFACBBAAAAADB2";
        String S0 = aString.substring((aString.indexOf("A")+1), aString.indexOf("B"));
        System.out.println(S0);
    }

    @Test
    public void theTest(){



        //String s = "Snail Mail \"Pristi!ne\"";
        String s = "BUBBLE PUPPY Hot Smoke & Sassafras";

        //Optional<String> optional = Optional.ofNullable(s.split("be/")[1]);

        //System.out.println(songTitleUtil.clearSpecialCharSong(s));

        AccessToken accesstoken = new AccessToken("BQC41XeSKs-xkmPo9PrhPDtOeORap1RNRBXDz1xdiI2KhUNeyNaWpnxaEVPT-t2ZIbWYXyiTOSpKIU03Zv2R4MK0DK3P9SA-F2F990P2on_Sx9m-PyH2rKsDq8G5ySYPx3W8QHlV4LqF8CcKrml04fSBIAU4TkGrUp3SCQimR0HobkhIvOLEgf3-yFd7hn2MERkxjz2_2jNBt-F0Mwa17JMwn40VPPdD7nkDBneYSqY",
                3600000 + System.currentTimeMillis());
        serviceApi.test(accesstoken);
        serviceApi.testRefresh("AQC3iZ75gRGcIOUBUKaPnL4AUDNKRv7aUL-T1_tb6g-dXJixErA4zdSkK53ZikS1xj8aBH7-2CjuqpqNdjhpUBUImbIdtvdc9iP_OC9bQMoFGRMRNEwYjXzVfUVizQPHcJmeZw");
        serviceApi.beforeCall();

    }

    @Test
    public void testSearchSubmit(){

        List<String> list = new ArrayList<>();
        list.add("https://www.youtube.com/watch?v=4k4SP01l6rY");
        list.add("https://www.youtu");
        list.add("kin k - kiukjioo");
        list.add("https://www.youtube.com/watch?v=uAsV5-Hv-7U");

        //validity 3600000 + System.currentTimeMillis()

        //****Set access token to use serviceApi's spotify API
        AccessToken accesstoken = new AccessToken("BQC41XeSKs-xkmPo9PrhPDtOeORap1RNRBXDz1xdiI2KhUNeyNaWpnxaEVPT-t2ZIbWYXyiTOSpKIU03Zv2R4MK0DK3P9SA-F2F990P2on_Sx9m-PyH2rKsDq8G5ySYPx3W8QHlV4LqF8CcKrml04fSBIAU4TkGrUp3SCQimR0HobkhIvOLEgf3-yFd7hn2MERkxjz2_2jNBt-F0Mwa17JMwn40VPPdD7nkDBneYSqY",
                3600000 + System.currentTimeMillis());
        serviceApi.test(accesstoken);
        //serviceApi.testRefresh("AQC3iZ75gRGcIOUBUKaPnL4AUDNKRv7aUL-T1_tb6g-dXJixErA4zdSkK53ZikS1xj8aBH7-2CjuqpqNdjhpUBUImbIdtvdc9iP_OC9bQMoFGRMRNEwYjXzVfUVizQPHcJmeZw");


        Map<Boolean, List<String>> mapYt = list.stream().map(i->serviceApi.getClearSongName(i)).collect(Collectors.partitioningBy(str-> !str.contains("\b")));

        Map<Boolean, List<Item>> mapSp = mapYt.get(true).stream().map(e->serviceApi.searchTrack(e)).collect(Collectors.partitioningBy(it->it.getUri()!=null));

        List<String> listToAdd = mapSp.get(true).stream().map(i->i.getUri()).collect(Collectors.toList());
        serviceApi.addTracks(new Uri(listToAdd), "7vOmFSzBe6C4TG7PGvg5lw");
        System.out.println();


        //List<String> newList = list.parallelStream().map(l -> songTitleUtil.clearSpecialCharSong(l)).collect(Collectors.toList());
        //List<Item> trackList = list.stream().map(l -> songTitleUtil.clearSpecialCharSong(l)).map(lItem->serviceApi.searchTrack(lItem)).collect(Collectors.toList());

        //System.out.println(youtubeUtil.getClearSongName("https://www.youtube.com/watch?v=4k4SP01l6rY"));
//        System.out.println();
//        System.out.println(songTitleUtil.clearSpecialCharSong("https://www.youtube.com/watch?v=4k4SP01l6rY"));
//        String uri = "https://www.youtube.com/watch?v=jljJ1m0CXAs&list=PLQl4GEJu5zoU273YLWjYvn1HU7nuXN02e&index=4&t=0s";
//        MultiValueMap<String, String> parameters =
//                UriComponentsBuilder.fromUriString(uri).build().getQueryParams();
//        List<String> s = parameters.get("v");
//        System.out.println();

//        List<String> param1 = parameters.get("param1");
//        List<String> param2 = parameters.get("param2");
//        System.out.println("param1: " + param1.get(0));
//        System.out.println("param2: " + param2.get(0) + "," + param2.get(1));
        //List<Item> listItem = newList.stream().map(lItem->serviceApi.searchTrack(lItem)).collect(Collectors.toList());
        //System.out.println(newList);

        //trackList.parallelStream().forEach(i->i.getUri());

        //===normalize
        //System.out.println(Normalizer.normalize("Susanne Sundfór", Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));

        //===collator
//        Collator insenstiveStringComparator = Collator.getInstance();
//        insenstiveStringComparator.setStrength(Collator.PRIMARY);
//        System.out.println("pp "+insenstiveStringComparator.compare("ô, ana", "o ana"));

    }

    @Test
    public void getToken() {
        System.out.println("entrou");
        String url = baseUrl;
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic M2JjZDNmMDVmYTBiNDVkOWE4MTY4ZmFmMjNhYjg3Mjk6Yzc4YWI5YzE5YmRhNGY3MzgwMDA5M2E3NDdiMmJjODg=");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        //map.add("Authorization", "Basic M2JjZDNmMDVmYTBiNDVkOWE4MTY4ZmFmMjNhYjg3Mjk6Yzc4YWI5YzE5YmRhNGY3MzgwMDA5M2E3NDdiMmJjODg=");

        map.add("grant_type", "authorization_code");
        map.add("code", CODE);
        map.add("redirect_uri", "https://www.getpostman.com/oauth2/callback");

//        map.add("client_id", clientId);
//        map.add("client_secret", secret);
//        map.add("code", code);
//        map.add("grant_type", "authorization_code");


        HttpEntity<MultiValueMap<String, String>> requestEntity=
                new HttpEntity<MultiValueMap<String, String>>(map, headers);
        try{
            System.out.println("try");
            template = new RestTemplate();
            AccessToken accessToken = template.postForObject(baseUrl, requestEntity,  AccessToken.class);
            //accessToken = response.getAccessToken() + " - " + response.getTokenType();
            System.out.println("FEITOOOOOOOOO "+accessToken.getAccessToken());
        }
        catch(RestClientResponseException e) {
            System.out.println(e.getMessage());
            System.out.println("Fail " + e.getResponseBodyAsString());
        }


        //return token;
    }

    @Test
    public void getTokenNew(){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Basic M2JjZDNmMDVmYTBiNDVkOWE4MTY4ZmFmMjNhYjg3Mjk6Yzc4YWI5YzE5YmRhNGY3MzgwMDA5M2E3NDdiMmJjODg=");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", CODE);
        requestBody.add("redirect_uri", "https://www.getpostman.com/oauth2/callback");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        try {
            AccessToken response = restTemplate.postForObject("https://accounts.spotify.com/api/token", httpEntity, AccessToken.class);
            System.out.println("success: "+response);
            //httpServletResponse.setHeader("Location", url); // redirect to success page
        }
        catch(HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userDetails(){

        String url = "https://api.spotify.com/v1/me/";
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", "Bearer BQASoTIOtYvWn1TiZ8Qx71XANzaahJOHTkNhQeuGq_Az8DMbwxrFLNNsaXy-Tt_Yg8_Qz4DQSKqseYkWeuasLKbe48-jlSc0ezh_NPkvq6P-qtBe290pJbnzGHyXMQwZ8rcR-cdoWiSVL4ZkrMAgupSD_pNuJuPkxXNfm3G8--qFxk_tmC0oOdmBiAs1T0O0PaQs2_PpNoFmKKfp3vj4fkaHjIWwSmEARbGofh_8WAQ");
        headers.add("Authorization", "Bearer BQD9WuN0MLe-k22vCTZ18L0Km9e2uIuoKqHGNzwepJ_dAKdbuQjdRDkMHK7l2X_ZHVHwZULs7oPSnAPST4TU7pXogzXLl5NHn3bJmombu5SIPerWHAQeol39nl6JU13L1hwR_NyFCt5cGAId3OBP2jVn0FXkjUGg7aqUs6q2mAXM9EY_Kn8LS0OLb3bl9o38nZ93fj3-em8OY6Wycj_RSmy3TfP0UNxwNCJo6nfpVw8");
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        //map.add("Authorization", "Basic M2JjZDNmMDVmYTBiNDVkOWE4MTY4ZmFmMjNhYjg3Mjk6Yzc4YWI5YzE5YmRhNGY3MzgwMDA5M2E3NDdiMmJjODg=");
//        map.add("grant_type", "authorization_code");
//        map.add("code", CODE);
//        map.add("redirect_uri", "https://www.getpostman.com/oauth2/callback");
        try{
            HttpEntity<String> entity = new HttpEntity<>("paramenters", headers);
            ResponseEntity<User> response = template.exchange(url, HttpMethod.GET, entity, User.class);
            //String user = template.exchange(url, HttpMethod.GET, headers, String.class);
            //accessToken = response.getAccessToken() + " - " + response.getTokenType();
            System.out.println("FEITOOOOOOOOO "+response.getStatusCode()+" "+response.getBody());
        }
        catch(Exception e){
            System.out.println("deu r u i m");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CreatePlaylistManual() {
        String URL = "https://api.spotify.com/v1/users/12162320634/playlists";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTHORIZATION);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        try {
            System.out.println("create json");
            jsonObject.put("name", "nova");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        try {
            System.out.println("postForObject");
            String result = restTemplate.postForObject(URL, request, String.class);
            JsonNode root = objectMapper.readTree(result);
            System.out.println("?? "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CreatePlaylistPojo() {
        String URL = "https://api.spotify.com/v1/users/12162320634/playlists";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTHORIZATION);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //ObjectMapper objectMapper = new ObjectMapper();

        CreatePlaylist createPlaylist = new CreatePlaylist("play 22");
        createPlaylist.setDescription("this is a description");
        HttpEntity<CreatePlaylist> request = new HttpEntity<>(createPlaylist, headers);

        try {
            System.out.println("postForObject");
            Playlist result = restTemplate.postForObject(URL, request, Playlist.class);
            //JsonNode root = objectMapper.readTree(result);
            System.out.println("?? "+result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
