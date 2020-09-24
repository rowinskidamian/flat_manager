<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Potwierdź usunięcie</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>
<section class="navbar">
    <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
</section>

<section class="section">
    <div class="container is-fluid">
        <div class="notification">
            <h5 class="title is-5 has-text-centered">Potwierdź usunięcie mieszkania</h5>
                <form:form modelAttribute="propertyDeleteData" method="post">
                <div class="field is-grouped is-grouped-centered">
                    <p class="control">
                    <div class="buttons">
                        <button class="button is-success" type="submit">
                            <strong>Potwierdź</strong>
                        </button>
                        <a class="button is-light" href="/property/delete/${propertyDeleteData.propertyId}">
                            Anuluj
                        </a>
                    </div>
                    </p>
                </div>
                    <input hidden name="propertyId" value="${propertyDeleteData.propertyId}">
                    <input hidden name="loggedUserName" value="${propertyDeleteData.loggedUserName}">
                <sec:csrfInput/>
                    </form:form>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>


</body>
</html>
