# StringSpotify
Available at: https://stringspotify.herokuapp.com/<br>
you paste a list of song names and/or youtube music's URL and it converts into a Spotify playlist (full description below)
![system working](https://github.com/sylleryum/StringSpotify/blob/master/system.gif)

-Simply press in "Get started" to authorize this app to create playlists/add songs to your spotify (for regular cases pressing once is enough, for longer use please check issues/next steps session below<br>
-You can either create a new playlist or add songs to an existing one<br>
-Paste song name (preferably in the format of artist name - song name)<br>
-One song per line<br>
<b>-The app clears automatically special characters/unnecessary words (regardless if YT link or not), example:</b><br>
https://www.youtube.com/watch?v=4k4SP01l6rY will fetch a result as: Gorillaz - Feel Good Inc. (Lyric Video) [HD] [HQ]<br>
the app will then convert it to Gorillaz Feel Good Inc<br>
Similarly song: Snail Mail "Pristi!ne" will be converted to Snail Mail Pristine<br>
<br><br>
Issues/next steps:<br>
-In some rare cases some songs which can be found searching directly on Spotify can't be found through the API call (to be verified);<br>
-Spotify's access token is valid for one hour, after this the app should get you a new one based on a refresh token, however, the documentation doesn't explain what is the valifity of the refresh token, therefore, if app isn't working after a long time stopped, it may be needed to press in "Get started" one more time (to be tested/verified the expiration of refresh token);<br>
-Implement cookies after investigating the above;<br>
-When using from the page https://stringspotify.herokuapp.com/ the same will go to sleep after a few hours where no access happens, 
in this case it is needed to press in "Get started" before using;<br>
-Developing a not so ugly user interface (if anyone wants to contribute, feel free);
