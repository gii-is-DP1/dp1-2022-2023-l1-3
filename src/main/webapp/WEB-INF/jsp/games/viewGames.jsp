<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="xtreme" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<head>
    <xtreme:head></xtreme:head>
    <link rel="stylesheet" href="/resources/css/list.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.csss">
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
        $('#tabla').DataTable();
        $('.dataTables_length').addClass('bs-select');
  });
    </script>
    
</head>
<body>

<div>
<table id="tabla" class="minimalistBlack">
<thead>
<tr>
<th>Nombre partida</th>
<th>Numero de jugadores</th>
<th>Jugadores en la sala</th>
<th>Tipo de juego</th>
<th>Host de la partida</th>
<th>Unirse</th>
</tr>
</thead>
<tbody>
<c:forEach items="${games}" var="game">
    <tr>
        <td>
            <c:out value="${game.gameName}"/>
        </td>
        <td>
            <c:out value="${game.numPlayers}"/>
        </td>
        <td>
            <c:forEach items = "${game.players}" var = "player"> 
                <c:out value = "${player.user.username}"/>&nbsp;
            </c:forEach>
        </td>
        <td>
            <c:out value="${game.gameType}"/>    
        </td>
        <td>
            <c:out value="${game.creatorPlayer.user.username}"/>        
        </td>
        <td>
            <a class="button" href = "<spring:url value="/games/lobby/${game.id}"  htmlEscape="true"/>"><div class="large valign-text-middle vt323-normal-
            licorice-64px">Unirse a partida</div></a>
        </td>
    </tr>
</c:forEach>
</tbody>
<tfoot></tfoot>
</table>
</div>
<div>
    <table id="tabla" class="minimalistBlack">
    <thead>
    <tr>
    <th>Host</th>
    <th>Juego</th>
    <th>Tipo de invitacion</th>
    <th>Unirse</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${invitations}" var="invitation">
        <tr>
            <td>
                <c:out value="${invitation.player1.user.username}"/>
            </td>
            <td>
                <c:out value="${invitation.game.gameType}"/>
            </td>
            <td>
                <c:out value="${invitation.invitationType}"/>    
            </td>
            <td>
                <a class="button" href = "<spring:url value="/games/lobby/${invitation.game.id}"  htmlEscape="true"/>"><div class="large valign-text-middle vt323-normal-
                licorice-64px">Unirse a partida</div></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot></tfoot>
    </table>
    </div>
<div>
    <a style="position: relative;" href="<spring:url value="/home" htmlEscape="true"/>" class="regresar"> < Regresar</a>
</div>
<span class="help-inline"><c:out value="${message}"/></span>
</body>

