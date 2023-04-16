<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <meta charset="utf-8">

        <title>Villes</title>
        <meta name="description" content="TP ESEO API REST + CLIENT">
        <meta name="author" content="Alexis">

        <link href="../css/index.css" rel="stylesheet" type="text/css">
        <link href="../css/home.css" rel="stylesheet" type="text/css">

    </head>
    
    <body>
        <%@ include file="navBar.jsp" %> 
        
        <main>
            <h1>Home page</h1>

            <form action="/" method="post">
                <select name="ville1" id="ville1">
                    <c:forEach items="${villes}" var="v">
                        <option value="${v.getCode_commune_INSEE()}" ${v.getCode_commune_INSEE() == ville1.getCode_commune_INSEE() ? 'selected' : ''}>${v.getNomCommune()}</option>
                    </c:forEach>
                </select>
                <select name="ville2" id="ville2">
                    <c:forEach items="${villes}" var="v">
                        <option value="${v.getCode_commune_INSEE()}" ${v.getCode_commune_INSEE() == ville2.getCode_commune_INSEE() ? 'selected' : ''}>${v.getNomCommune()}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Calculer">
            </form>
            <c:if test="${distance != null}">
                <p>La distance entre ${ville1.getNomCommune()} et ${ville2.getNomCommune()} est de ${distance}kms</p>
            </c:if>
        </main>

    </body>
</html>