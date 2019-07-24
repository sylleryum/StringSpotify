package com.example.demo.Controller;

import com.example.demo.Entity.Playlist;
import com.example.demo.Entity.playlists.Item;
import com.example.demo.service.ServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class TheController {

    @Autowired
    ServiceApi serviceApi;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/authorize")
    public String authorize() {

        return "redirect:" + ServiceApi.AUTHORIZE_URL;

    }

    //TODO put coooooooookies

    @GetMapping("/callback")
    public String callback(@RequestParam String code) {

        //        Cookie cookie = new Cookie("test", "avalue123");
        //        cookie.setMaxAge(60*60*24);
        //        response.addCookie(cookie);
        //System.out.println("code: " + code);
        //ServiceApiImpl serviceApi = new ServiceApiImpl();
        serviceApi.getAccessToken(code);
        return "home";
    }

    @GetMapping("/userDetails")
    public String userDetails(Model model) {

        //////////serviceApi.getUserDetails();
        List<Item> list = serviceApi.getPlaylists();

//        List<Item> playlists = playlistList.getItems();
//        for (Item ss:playlists) {
//            System.out.println("== "+ss.getId()+" "+ss.getName()+" "+ss.getOwner());
//        }

        if (list!=null){
            model.addAttribute("playlists", list);
        } else {
            model.addAttribute("noToken", true);
        }

        System.out.println();

        return "home";
    }

    @GetMapping("/createPlaylist")
    public String createPlaylist(@RequestParam("playlistName")String playlistName){
        serviceApi.createPlaylist(playlistName);
        return "home";
    }

    @GetMapping("/searchTrack")
    public String searchTrack(@RequestParam(value = "searchName", required = false) String searchName, @RequestParam(value = "playlistName", required = false) String playlistName,
                              @RequestParam(value = "selectPlaylist", required = false) String selectPlaylist, @RequestParam("playlistRadio") String playlistRadio, Model theModel){
        if (searchName==null){System.out.println("no songs");return "home";}

        List<String> tracksToFind = Arrays.asList(searchName.split("\\r?\\n"));

        //serviceApi.searchTracks(s);
        //Uri uri = new Uri(new String[]{"spotify:track:69GuasseR3zP2F9uOVh50i","spotify:track:5sICkBXVmaCQk5aISGR3x1"});
        //serviceApi.addTracks(uri,playlistName);

        Map<Boolean,List<String>> mapReturn;
        if (playlistRadio.equalsIgnoreCase("existing")){
            mapReturn = serviceApi.submitAddAllTracks(tracksToFind, selectPlaylist);
        } else {
            Playlist playlist = serviceApi.createPlaylist(playlistName);
            if (playlist!=null){
                mapReturn = serviceApi.submitAddAllTracks(tracksToFind, playlist.getId());
            } else {
                mapReturn = null;
            }
        }

        if (mapReturn!=null){
            theModel.addAttribute("failedSongs", mapReturn);
        } else {
            theModel.addAttribute("noToken", true);
        }


        ////////////////////******=====serviceApi.searchTracks(s);

        //Arrays.asList(s).forEach(s1-> System.out.println(s1));
        //List list = Arrays.stream(searchName.split("\\n")).collect(Collectors.toList());
        //System.out.println("name before "+list);
        //serviceApi.searchTrack(trackName);
        return "home";
    }
}
//TODO upload
