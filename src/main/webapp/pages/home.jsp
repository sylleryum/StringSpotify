<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://fonts.googleapis.com/css?family=Raleway&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="resources/css/style.css">
    <title>Create a Spotify playlist with your songlist</title>

    <script>

        document.addEventListener('DOMContentLoaded', function () {


            <c:choose>
            <c:when test="${noToken==true}">
            document.getElementById("no-token").classList.remove("block-style");
            document.getElementById("no-token").classList.add("no-token");
            </c:when>

            <c:otherwise>
            document.getElementById("main-window").classList.remove("block-style");
            document.getElementById("main-window").classList.add("program-container");
            </c:otherwise>
            </c:choose>

        }, false);


        function radioChecker() {
            if (document.getElementById("rNew").checked) {
                document.getElementById("new-playlist").classList.remove("block-style");
                document.getElementById("existing-playlist").classList.add("block-style");
            } else {
                document.getElementById("new-playlist").classList.add("block-style");
                document.getElementById("existing-playlist").classList.remove("block-style");
            }
        }


        function validation() {
            var errors = '';
            if (document.getElementById("rNew").checked) {

                if (document.getElementById("playlistName").value === "") {
                    errors = "Please choose a name for the new playlist";
                }
            } else if (!document.getElementById("rNew").checked && !document.getElementById("rExisting").checked) {
                errors = "Please select new or existing playlist";
            }

            if (document.getElementById("searchName").value === "") {
                errors += "\rPlease insert songs or youtube links";
            }

            if (errors === "") {
                return true;
            } else {
                alert(errors);
                return false;
            }

        }


    </script>

</head>
<body>

<form action="searchTrack" id="submit-btn" onsubmit="return validation()">

    <div class="credits-container">
    <div class="page-container">
        <div id="no-token" class="block-style" onclick="location.href='/authorize';" style="cursor:pointer;">
<%--            <input type="button" id="get-started" onclick="location.href='${pageContext.request.contextPath}/authorize';"--%>
<%--                   value="Click here to start"/>--%>

            <a href="/authorize">Click here to start</a>
        </div>
        <div class="block-style" id="main-window">
            <div class="main-menu">

                <div class="main-select">
                    <input type="radio" id="rNew" name="playlistRadio" value="new" onchange="radioChecker()">New
                    playlist
                    <input type="radio" id="rExisting" name="playlistRadio" value="existing"
                           onchange="radioChecker()">Existing
                    playlist
                </div>
                <div class="main-playlist">
                    <div id="new-playlist" class="block-style">
                        Playlist name: <input type="text" id="playlistName" name="playlistName"/>
                    </div>
                    <div id="existing-playlist" class="block-style">
                        <span>Your playlists:</span>
                        <select name="selectPlaylist">
                            <c:forEach var="items" items="${playlists}">
                                <option value="${items.getId()}">${items.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div>
                    Search tracks:
                </div>
                <div class=" main-textarea">
                    <textarea class="textarea-search" id="searchName" name="searchName" placeholder="Paste your songs here"></textarea>
                </div>
                <div class="main-go">
                    <button type="submit" id="submit-button">
                        Go
                    </button>
                </div>

            </div>
            <div class="separator"></div>
            <div class="results-menu">
                <div class="success">
                    <h4>Songs added (${failedSuccessSongs.get(true).size()}):</h4>
                    <c:forEach var="success" items="${failedSuccessSongs.get(true)}">
                        <p>${success}</p>
                    </c:forEach>
                </div>
                <div class="failure">
                    <h4>Songs not found (${failedSuccessSongs.get(false).size()}):</h4>
                    <c:forEach var="fail" items="${failedSuccessSongs.get(false)}">
                        <p>${fail}</p>
                    </c:forEach>
                </div>
            </div>

        </div>

    </div>
    <div class="credits">Find more information and source code at: <a href="https://github.com/sylleryum/StringSpotify" target="_blank">https://github.com/sylleryum/StringSpotify</a></div>
    </div>
</form>

</body>
</html>
