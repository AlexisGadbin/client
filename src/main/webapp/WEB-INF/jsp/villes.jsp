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
                        <a class="linkVille" href="/villes?ville=${v.getNomCommune()}&page=${page}"><c:out value="${v.getNomCommune()}" /></a>
                    </c:forEach>
                    <div class="pagination">
                        <c:choose>
                            <c:when test="${ville.getNomCommune() != ''}">
                                <a href="/villes?ville=${ville.getNomCommune()}&page=${page-1}" class="${page <= 1 ? 'disabledHref' : ''}">&lt</a>                               
                                <c:if test="${page+4<=68}">
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+1}">${page+1}</a>
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+2}">${page+2}</a>
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+3}">${page+3}</a>
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+4}">${page+4}</a>
                                    ...
                                </c:if>
                                <a href="/villes?ville=${ville.getNomCommune()}&page=${page+1}" class="${page >=68 ? 'disabledHref' : ''}">&gt</a>
                            </c:when>
                            <c:otherwise>
                                    <a href="/villes?page=${page-1}" class="${page <= 1 ? 'disabledHref' : ''}">&lt</a>
                                <c:if test="${page+4<=68}">
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+1}">${page+1}</a>
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+2}">${page+2}</a>
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+3}">${page+3}</a>
                                    <a href="/villes?ville=${ville.getNomCommune()}&page=${page+4}">${page+4}</a>
                                    ...
                                </c:if>
                                <a href="/villes?page=${page+1}" class="${page >=68 ? 'disabledHref' : ''}">&gt</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="infosVille">
                    <c:if test="${ville == null}">
                        <h3>Selectionner une ville pour voir les informations</h3>
                    </c:if>
                    <c:if test="${ville != null}">
                        <h2><c:out value="${ville.getNomCommune()}"/></h2>
                    
                        <form method="post" action="/villes?ville=${ville.getNomCommune()}&page=${page}" name="modifierVille">
                            
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