<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet"
          href="resources/css/style.css">
    <script>

        document.addEventListener('DOMContentLoaded', function () {


            <c:choose>
            <c:when test="${noToken==true}">
            document.getElementById("no-token").classList.remove("block-style");
            </c:when>

            <c:otherwise>
            document.getElementById("main-window").classList.remove("block-style");
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
<body style="">

<form action="searchTrack" onsubmit="return validation()">

    <div id="no-token" class="block-style">
        <input type="button" onclick="location.href='${pageContext.request.contextPath}/authorize';"
               value="Get started!"/>

    </div>
    <div id="main-window" class="block-style">
        <table>


            <tr>
                <td>
                    <input type="button" onclick="location.href='${pageContext.request.contextPath}/authorize';"
                           value="Get started!"/>
                </td>
                <td rowspan="8" width="350px">
                    <div style="font-weight: bold">Songs added (${failedSuccessSongs.get(true).size()}):</div>
                    <c:forEach var="success" items="${failedSuccessSongs.get(true)}">
                        <p>${success}</p>
                    </c:forEach>

                    <br>
                    <div style="font-weight: bold">Songs not found (${failedSuccessSongs.get(false).size()}):</div>
                    <c:forEach var="fail" items="${failedSuccessSongs.get(false)}">
                        <p>${fail}</p>
                    </c:forEach>


                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" onclick="location.href='${pageContext.request.contextPath}/userDetails';"
                           value="Get my playlists"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="radio" id="rNew" name="playlistRadio" value="new" onchange="radioChecker()">New
                    playlist
                    <input type="radio" id="rExisting" name="playlistRadio" value="existing" onchange="radioChecker()">Existing
                    playlist
                </td>
            </tr>
            <tr>
                <td>
                    <div id="new-playlist" class="block-style">
                        Playlist name: <input type="text" id="playlistName" name="playlistName"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="existing-playlist" class="block-style">
                        <span>Your playlists:</span>
                        <select name="selectPlaylist">
                            <c:forEach var="items" items="${playlists}">
                                <option value="${items.getId()}">${items.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    Search tracks:
                    <br>
                    <textarea rows="10" cols="70" id="searchName" name="searchName"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Go">
                </td>
            </tr>


        </table>
    </div>

    <br>
    <div>Find more information and source code at: <a href="https://github.com/sylleryum/StringSpotify" target="_blank">https://github.com/sylleryum/StringSpotify</a>
    </div>
    <br>


    <br>

    <%--<button type="button" onclick="(function() {--%>
    <%--window.location='/createPlaylist?playlistName='+document.getElementsByName('playlistName')[0].value;--%>
    <%--})();">Create playlist--%>
    <%--</button>--%>
    <br>


    <br><br>

    <br>


</form>
<br>


</body>


</html>