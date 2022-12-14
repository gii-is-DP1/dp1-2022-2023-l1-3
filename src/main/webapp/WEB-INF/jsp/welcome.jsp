<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xtreme" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<head>
    <xtreme:head></xtreme:head>
	<link rel="stylesheet" href="/resources/css/base.css">
	<link rel="stylesheet" href="/resources/css/welcome.css">
</head>
<div class="welcome">
    <img src="/resources/img/Xtreme_Oca_2006.jpeg" alt="Logo Xtremo" class = "logo">
    <h1 style="margin-top: 0.5em; margin-bottom: 0.10em;" class="title"><span class="text">Xtreme</span> Parchis&Oca</h1>
    
    <a class="button" href="<spring:url value="/home"  htmlEscape="true"/>"><div class="large valign-text-middle vt323-normal-
        licorice-64px">Iniciar Sesión</div></a>
    <a class="button" href = "<spring:url value="/players/create"  htmlEscape="true"/>"><div class="large valign-text-middle vt323-normal-
            licorice-64px">Registrarme</div></a>
</div>
<canvas id="frame"></canvas>

<script src="/resources/js/fire.js"></script>
