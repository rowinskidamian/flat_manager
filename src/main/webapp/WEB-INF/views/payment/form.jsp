<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Formularz edycji płatności</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>
<section>
    <section class="navbar">
        <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
    </section>
</section>


<section class="hero is-info">
    <div class="hero-body">
        <div class="container">
            <h1 class="title">
                Formularz edycji płatności.
            </h1>
            <h2 class="subtitle">
                Wprowadź dane dla płatności.
            </h2>
        </div>
    </div>
</section>


<section class="section">
    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <form:form modelAttribute="paymentData" method="post">

                    <div class="field">
                        <label class="label">Data płatności</label>
                        <div class="control">
                            <input type="date" name="paymentDate">
                        </div>
                        <form:errors path="paymentDate" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Kwota</label>
                        <div class="control has-icons-left">
                            <form:input path="amount" cssClass="input"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-coins"></i>
                        </span>
                        </div>
                        <form:errors path="amount" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Wybierz najemcę</label>
                        <c:if test="${tenantList.size() > 0}">
                            <div class="control">
                                <div class="select">
                                    <form:select path="tenantId">
                                        <form:option value="" label="Wybierz najemcę"/>
                                        <form:options items="${tenantList}" itemValue="tenantId"
                                                      itemLabel="tenantFullName"/>
                                    </form:select>
                                </div>
                            </div>
                            <form:errors path="tenantId" cssClass="has-text-danger"/>
                        </c:if>
                        <c:if test="${tenantList.size() eq 0}">
                            Brak najemców w bazie. Dodaj nowych.
                        </c:if>
                    </div>


                    <div class="field is-grouped">
                        <div class="control">
                            <button class="button is-link" type="submit">Wyślij</button>
                        </div>
                        <div class="control">
                            <button class="button is-light" onclick="history.go(-1)">
                                Anuluj
                            </button>
                        </div>
                    </div>
                    <sec:csrfInput/>
                </form:form>
            </div>
        </div>

    </div>

    <footer class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </footer>

    <script src="/scripts/adress_in_registration.js" type="text/javascript"></script>

</section>
</body>
</html>