<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script>
        function validation() {
            var errors = "";
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
    <style type="text/css">

        table, th, td {
            border: 1px solid black;
        }

        html, body{
            height: 100%;
        }

        html{
            display: table;
            width: 100%;
        }

        body{
            display: table-cell;
            text-align: center;
        }


    </style>
</head>
<body style="">

<form action="searchTrack" onsubmit="return validation()">

    <table>
        <tr>
            <td>
                <input type="button" onclick="location.href='${pageContext.request.contextPath}/authorize';"
                       value="Get started!"/>
            </td>
            <td rowspan="8" width="350px">
                Songs added:
                <br>
                <c:forEach var="success" items="${failedSongs.get(true)}">
                    <p>${success}</p>
                </c:forEach>
                <br>
                Songs not found:
                <br>
                <c:forEach var="fail" items="${failedSongs.get(false)}">
                    <p>${fail}</p>
                </c:forEach>


            </td>
        </tr>
        <tr>
            <td>
                <input type="button" onclick="location.href='${pageContext.request.contextPath}/userDetails';"
                       value="User details"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="radio" id="rNew" name="playlistRadio" value="new">New playlist
                <input type="radio" id="rExisting" name="playlistRadio" value="existing">Existing playlist
            </td>
        </tr>
        <tr>
            <td>
                Playlist name: <input type="text" id="playlistName" name="playlistName"/>
            </td>
        </tr>
        <tr>
            <td>
                <select name="selectPlaylist">
                    Your playlists:
                    <br>
                    <c:forEach var="items" items="${playlists}">
                        <option value="${items.getId()}">${items.getName()}</option>
                    </c:forEach>
                </select>
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
        <tr>
            <td>February</td>
        </tr>
    </table>


    <br>

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