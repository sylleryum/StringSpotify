package com.example.demo.helper;

import com.example.demo.service.ServiceApi;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class YoutubeUtilImpl implements YoutubeUtil {

    //https://www.youtube.com/watch?v=4k4SP01l6rY
    ////document.getElementsByTagName("h1")[0].innerText
    Document doc;

    @Override
    public String getSongName(String url) {

        //String uri = "https://www.youtube.com/watch?v=jljJ1m0CXAs&list=PLQl4GEJu5zoU273YLWjYvn1HU7nuXN02e&index=4&t=0s";

//        String ID = getId(url);
//        return serviceApiYoutube.getClearSongName(url);

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            Elements elements = doc.getElementsByTag("h1");
            System.out.println();
            //System.out.println("Song name "+doc.getElementsByTag("h1"));
            return doc.getElementsByTag("h1").get(1).text();
        } catch (Exception e) {
            System.out.println("not possible to get YOUTUBE songname " + url);
            e.printStackTrace();
            return null;
        }

    }

//    private String getId(String url){
//        MultiValueMap<String, String> parameters =
//                UriComponentsBuilder.fromUriString(url).build().getQueryParams();
//
//        try {
//            return parameters.get("v").get(0);
//        } catch (Exception e){
//            System.out.println("unable to get youtube ID "+url);
//            e.printStackTrace();
//            return null;
//        }

}


