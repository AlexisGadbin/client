<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <meta charset="utf-8">

        <title>Villes</title>
        <meta name="description" content="TP ESEO API REST + CLIENT">
        <meta name="author" content="Alexis">

        <link href="../css/index.css" rel="stylesheet" type="text/css">
        <link href="../css/villes.css" rel="stylesheet" type="text/css">

    </head>
    
    <body>
        <%@ include file="navBar.jsp" %> 
        
        <main>
            <div class="grid">
                <div class="listeVilles">
                    <h2>Liste des villes</h2>
                    <c:forEach items="${villes}" var="v">
                        <a class="linkVille" href="/villes?ville=${v.getNomCommune()}"><c:out value="${v.getNomCommune()}" /></a>
                    </c:forEach>
                </div>
                <div class="infosVille">
                    <c:if test="${ville != null}">
                        <h2><c:out value="${ville.getNomCommune()}"/></h2>
                    
                        <form method="post" action="/villes?ville=${ville.getNomCommune()}" name="modifierVille">
                            
                            <label for="codeCommune">Code commune</label>
                            <input name="codeCommune" value="${ville.getCode_commune_INSEE()}" readonly/>

                            <label for="codePostal">Code postal</label>
                            <input name="codePostal" value="${ville.getCodePostal()}"/>

                            <label for="libelle">Libelle</label>
                            <input name="libelle" value="${ville.getLibelleAcheminement()}"/>

                            <label for="ligne">Ligne</label>
                            <input name="ligne" value="${ville.getLigne()}"/>

                            <label for="latitude">Latitude</label>
                            <input name="latitude" value="${ville.getLatitude()}"/>

                            <label for="longitude">Longitude</label>
                            <input name="longitude" value="${ville.getLongitude()}"/>

                            <input hidden name="nomCommune" value="${ville.getNomCommune()}"/>

                            <input type="submit" name="submitModifierVille" value="Modifier la ville">
                        </form>
                    </c:if>
                </div>
            </div>
        </main>

    </body>
</html>