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
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    String baseUrl = "https://accounts.spotify.com/api/token/";
    String CODE = "AQDoERYYT8yRF3AjXFpwszqLcy89fypCUMp8Azb1RCr2xnljlzh1oOm6Cpmn0bbuUhkHWH9_-OS7DZkfwVTk-ODqgD_o_ytC0Q2g9stev0xDYEx_0WiyiZHMsGKyXQ7lk73wISMKkeUeiLNaFmNhO9mse6Q_4n1juX-c0dedzoWnLssuMbT5oMm328eaziV8n_S-Yo9jTaZ657o2JwnGeHo6HrqmPT9_eEfDKGPkSI20nMyne4TFSMpg8nucuucP9fhiR--IvDKd4QjCRu-EBQGlHXXScjeLc3tacZeBqAds491BAm91rxISZ8lkvzC1TNVXMqlsjMSLPldy4gT4LyJ-G3NPoFuucIUWacCn";
    String AUTHORIZATION = "Bearer BQB0Q5ThoEzmdqXwLH7q0_KoFNjw3Pgl80xFBa_PKIcQmk0S7d8AR9GkbLgiXbmjYC8tQYPQhmGQgn3Z3i8pXuvp15awKZy1pd214ogATe2eyNu1Z3UQhXMo6JOC5eDYsqkAzc6cg3T6YRMnlDD3w0BKbUn1MdKt8hm2QCO-P7s5ZEfakiqm85fPl_U8YWyiQBQbdVvxWjMKl2wYB3dc1XMoGMmJX6O-C8hVVu03dH0";

    @Autowired
    SongTitleUtil songTitleUtil;
    @Autowired
    YoutubeUtil youtubeUtil;
    @Autowired
    ServiceApi serviceApi;

    @Test
    public void theTest(){

        String s = "https://youtu";

        //Optional<String> optional = Optional.ofNullable(s.split("be/")[1]);
        String[] ss =  s.split("be/");
        String ok;
        if (ss.length>1){
            ok=ss[1];
        }
        System.out.println();

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
        AccessToken accesstoken = new AccessToken("BQAhpbc08ZteXI9ze_0nYNez79LnwVvE2_ZjNyAMbpNNHAFKmfSBqq_QO6CghjBnqstmCF3UJZR5PRPx_-yvCkS7ePHBjopS8OJOTQh2QGcN-8qGCGPbAH0V9_iBzPzu56NpxcduiOVFD3ciSDe7s5gJKo-ePZMy4Z9iXI1HPVO93ZZ6RM_t8sdr9_z_Hr-4DCQs3wvT2NJCvYvEJDk9H2aq5DXMknhVt3ZhSV_NsZA",
                3600000 + System.currentTimeMillis());
        serviceApi.test(accesstoken);

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
        String token = "deu ruim";
        try{
            System.out.println("try");
            AccessToken accessToken = template.postForObject(url, requestEntity,  AccessToken.class);
            //accessToken = response.getAccessToken() + " - " + response.getTokenType();
            System.out.println("FEITOOOOOOOOO "+accessToken.getAccessToken());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


        //return token;
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
