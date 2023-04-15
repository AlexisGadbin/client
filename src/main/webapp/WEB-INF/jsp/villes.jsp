<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <meta charset="utf-8">

        <title>Villes</title>
        <meta name="description" content="TP ESEO API REST + CLIENT">
        <meta name="author" content="Alexis">
    </head>
    
    <body>
        
        <c:forEach items="${villes}" var="v">
            <c:out value="${v.getNomCommune()}" />
            <br>
        </c:forEach>
    </body>
</html>