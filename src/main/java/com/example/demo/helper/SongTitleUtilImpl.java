package com.example.demo.helper;

import com.example.demo.Entity.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SongTitleUtilImpl implements SongTitleUtil {


    private static YoutubeUtil youtubeUtil;
    Pattern specialPattern;
    private static Pattern youtubePattern;
    private Matcher matcher;
    private String testString = "When i need you -Leo Sayer (with lyrics ) w/lyrics [HQ] ";


    public SongTitleUtilImpl(YoutubeUtil youtubeUtil) {
        this.youtubeUtil = youtubeUtil;
        //static Pattern specialPattern = Pattern.compile("(?i)[(\bhq|lyrics|BY\b)!@#$%¨&*()_\\-+={\\[}\\]º|\\\\,.:;?°]");
        //static Pattern specialPattern = Pattern.compile("(?i)\\blyrics|inc\\b[!@#$%¨&*()_\\-+={\\[}\\]º|\\\\,.:;?°]");
        //=============
        //**special with only english characters
        // specialPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        //**youtube with only english characters
        //youtubePattern = Pattern.compile("[^a-z0-9 ]|\\blyrics|hd|hq|lyric video|with lyrics|w/|video|official music\\b", Pattern.CASE_INSENSITIVE);

        specialPattern = Pattern.compile("[!@#$%¨*()_\\-+={\\[}\\]º|\\\\,.:;?°]", Pattern.CASE_INSENSITIVE);
        youtubePattern = Pattern.compile("[!@#$%¨*()_\\-+={\\[}\\]º|\\\\,.:;?°]|\\blyrics|hd|hq|lyric video|with lyrics|w/|video|official|official music\\b", Pattern.CASE_INSENSITIVE);


    }

    //TODO to move
    static public String matchSong(Item track) {
        return null;
    }

    /**
     * remove any additional information after the '-' of a track name
     *
     * @param trackName e.g. paranoid android - remastered
     * @return e.g. paranoid android
     */
    @Override
    public String clearRemastered(String trackName) {

        if (trackName.contains("-")) {
            String a = trackName.substring(0, trackName.indexOf("-") - 1);
            return a;
        }
        return null;
    }


    @Override
    public String clearSpecialCharSong(String trackName) {
        String sTrim = trackName.trim();
        if (sTrim.substring(0, 4).equalsIgnoreCase("http")) {
            System.out.println("youtube");
            return clearYoutubeChar(youtubeUtil.getSongName(sTrim));
        } else {
            //System.out.println("song name");
            //return clearSpecialChar(trackName);
            return trackName;
        }

    }

    @Override
    public List<String> clearSpecialCharSongList(List<String> songList) {
        List<String> theReturn = new ArrayList<>();
        for (String s : songList) {
            theReturn.add(clearSpecialCharSong(s));
        }
        return theReturn;
    }

    //TODO change to private
    @Override
    public String clearYoutubeChar(String trackName) {
        matcher = youtubePattern.matcher(trackName);
        return matcher.replaceAll("");
    }

    @Override
    public String clearSpecialChar(String trackName) {
        matcher = specialPattern.matcher(trackName);
        return matcher.replaceAll("");
    }

    @Override
    public String getYoutubeId(String url) {

        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(url).build().getQueryParams();
        String v;

        try {
            return parameters.get("v").get(0);
        } catch (Exception e) {
            System.out.println("unable to get youtube ID with V parameter " + url);
            String[] ss =  url.split("be/");
            String ok;
            if (ss.length>1){
                return ss[1];
            }
            //e.printStackTrace();
            return null;
        }

    }
}