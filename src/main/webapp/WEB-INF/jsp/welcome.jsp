<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<!DOCTYPE html>
<html>
<head>
<style>
@import url("https://fonts.googleapis.com/css?family=VT323:400");
    .welcome {
    align-items: center;
    background-color: #000000;
    border: none;
    display: flex;
    flex-direction: column;
    min-height: 80%;
    max-height: 100vh;
    width: 100%;
    max-width: 100%;
    height: 100vh;
    }
    .title {
    -webkit-text-stroke: 3px var(--white) ;
    color: white;
    font-family: var(--font-family-vt323) ;
    font-size: var(--font-size-1) ;
    font-weight: 400;
    justify-content: center;
    letter-spacing: 0;
    line-height: normal;
    transform: rotate(0.07deg) ;
    width: fit-content;
    }
    .button {
    display: inline-block;
    padding: 15px 25px;
    font-size: 24px;
    cursor: pointer;
    text-align: center;
    font-family: var(--font-family-vt323);
    font-size: var(--font-size-2);
    text-decoration: none;
    justify-content: space-between;
    outline: none;
    color: #fff;
    background-color: #272927;
    border: none;
    border-radius: 15px;
    box-shadow: 0 9px #999;
    margin: 40px;
    }

    .button:hover {background-color: #0b0b0b}

    .button:active {
    background-color: #080808;
    box-shadow: 0 5px #666;
    transform: translateY(4px);
    }
    .large {
        -webkit-text-stroke: 3px var(--white) ;
        letter-spacing: 0;
        line-height: 28px;
        white-space: nowrap;
        width: fit-content;
    }
    .valign-text-middle {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }
    :root {
        --licorice: Mrgba(22, 19, 19, @.502);
        --white: Mrgba(255, 255, 255, 1);
        
        --font-size-m: 64px;
        --font-size-1: 128px;
        --font-size-2: 64px;
        
        --font-family-vt323: "VT323";
    }
    .vt323-normal-licorice-64px {
        font-family: var(--font-family-vt323) ;
        font-size: var(--font-size-m) ;
        font-weight: 400;
        font-style: normal;
    }
</style>
</head>
<div class="welcome screen">
    <h1 class="title">Xtreme Parchis&Oca</h1>
    <a class="button" href="http://localhost:8080/login"><div class="large valign-text-middle vt323-normal-
        licorice-64px">Iniciar Sesion</div></a>
    <a class="button" href = "http://localhost:8080/players/create"><div class="large valign-text-middle vt323-normal-
            licorice-64px">Registrarme</div></a>

    </div>
</div>
</div>