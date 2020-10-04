<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - wybierz najemcę do pokoju</title>
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
            <h5 class="title is-5 has-text-centered">Wybierz najemcę do pokoju</h5>
            <form:form modelAttribute="roomData" method="post">

                <div class="field">
                    <label class="label">Wybierz najemcę:</label>
                    <c:if test="${tenantListData.size() > 0}">
                        <div class="control">
                            <div class="select">
                                <form:select path="tenantId">
                                    <form:options items="${tenantListData}" itemValue="tenantId"
                                                  itemLabel="tenantFullName"/>
                                </form:select>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${tenantListData.size() == 0}">
                        Brak dostępnych najemców.
                    </c:if>
                </div>

                <div class="field is-grouped is-grouped-centered">
                    <div class="buttons">
                        <div class="control">
                            <button class="button is-link" type="submit">Wyślij</button>
                        </div>
                        <div class="control">
                            <button class="button is-light" onclick="history.go(-1)">
                                Anuluj
                            </button>
                        </div>
                    </div>
                </div>

                <input hidden name="roomId" value="${roomData.roomId}">
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
