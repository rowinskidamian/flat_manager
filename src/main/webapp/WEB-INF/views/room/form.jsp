<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - edycja pokoju</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>
<section>
    <section class="navbar">
        <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
    </section>
</section>

<section>

    <section class="hero is-info">
        <div class="hero-body">
            <div class="container">
                <h1 class="title">
                    Edycja pokoju
                </h1>
                <h2 class="subtitle">
                    Formularz edycji danych pokoju
                </h2>
            </div>
        </div>
    </section>

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="notification has-text-centered is-light">
                    <strong>Szczegóły pokoju</strong>
                </div>

                <div id="rooms-form" class="field">

                    <form:form method="post" modelAttribute="roomData">

                        <div class="field">
                            <label class="label">Wybierz mieszkanie</label>
                            <div class="control">
                                <div class="select">
                                    <form:select path="propertyId">
                                        <form:options items="${propertyListData}" itemValue="propertyId"
                                                      itemLabel="propertyWorkingName"/>
                                    </form:select>
                                </div>
                            </div>
                        </div>

                        <div class="field">
                            <label class="label">Dodaj najemcę</label>
                            <c:if test="${tenantListData.size() > 0}">
                                <div class="control">
                                    <div class="select">
                                        <form:select path="tenantId">
                                            <form:options items="${tenantListData}" itemValue="tenantId"
                                                          itemLabel="tenantFullName"/>
                                            <form:option value="" label="Bez najemcy"/>
                                        </form:select>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${tenantListData.size() == 0}">
                                Brak najemców oczekujących na pokój. Dodaj najemcę później.
                            </c:if>
                        </div>

                        <div class="field">
                            <label class="label">Podaj czynsz</label>
                            <div class="control">
                                <form:input path="catalogRent" cssClass="input"/>
                            </div>
                            <form:errors path="catalogRent" cssClass="has-text-danger"/>
                        </div>

                        <div class="field is-grouped">
                            <div class="control">
                                <button class="button is-link" type="submit">Wyślij</button>
                            </div>
                        </div>

                        <sec:csrfInput/>
                    </form:form>

                </div>

            </div>

        </div>

    </div>



</section>

<footer class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

</body>
</html>
