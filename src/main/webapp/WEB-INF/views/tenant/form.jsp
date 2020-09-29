<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Formularz dodawania najemcy</title>
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
                Formularz edycji najemcy.
            </h1>
            <h2 class="subtitle">
                Wprowadź dane najemcy.
            </h2>
        </div>
    </div>
</section>


<section class="section">
    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <form:form modelAttribute="tenantData" method="post">

                    <div class="field">
                        <label class="label">Imię</label>
                        <div class="control has-icons-left">
                            <form:input path="firstName" cssClass="input"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-user"></i>
                        </span>
                        </div>
                        <form:errors path="firstName" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Nazwisko</label>
                        <div class="control has-icons-left">
                            <form:input path="lastName" cssClass="input"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-user"></i>
                        </span>
                        </div>
                        <form:errors path="lastName" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <div class="columns">
                            <div class="column">
                                <label class="label">Wybierz pokój</label>
                                <c:if test="${roomListData.size() > 0}">
                                    <div class="control">
                                        <div class="select">
                                            <form:select path="roomId">
                                                <form:option value="" label="Wybierz pokój"/>
                                                <form:options items="${roomListData}" itemValue="roomId"
                                                              itemLabel="roomNameAndPrice"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${roomListData.size() eq 0}">
                                    Brak wolnych pokoi. Dodaj pokój, aby dodać najemcę.
                                </c:if>
                            </div>
                            <div class="column">
                                <label class="label">Rabat (w PLN)</label>
                                <form:input cssClass="input" path="rentDiscount"/>
                                <form:errors path="rentDiscount" cssClass="has-text-danger"/>
                            </div>
                        </div>
                    </div>

                    <div class="field">
                        <div class="columns">
                            <div class="column">
                                <label class="label">Data rozpoczęcia najmu</label>
                                <div class="control">
                                    <input type="date" name="leaseDateStart">
                                </div>
                                <form:errors path="leaseDateStart" cssClass="has-text-danger"/>
                            </div>
                            <div class="column">
                                <label class="label">Data zakończenia najmu</label>
                                <div class="control">
                                    <input type="date" name="leaseDateEnd">
                                </div>
                                <form:errors path="leaseDateEnd" cssClass="has-text-danger"/>
                            </div>
                            <div class="column">
                                <label class="label">Termin płatności</label>
                                <div class="control">
                                    <form:input path="paymentDeadline" cssClass="input"/>
                                </div>
                                <form:errors path="paymentDeadline" cssClass="has-text-danger"/>
                            </div>
                        </div>
                    </div>

                    <div class="field">
                        <label class="label">E-mail</label>
                        <div class="control has-icons-left">
                            <form:input path="email" cssClass="input"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-user"></i>
                        </span>
                        </div>
                        <form:errors path="email" cssClass="has-text-danger"/>
                    </div>

                    <div id="adress-details" class="field">

                        <div class="notification is-light">
                            <strong>Dane adresowe</strong>
                        </div>

                        <div class="block">

                            <div class="field">
                                <label class="label">Miasto</label>
                                <div class="control has-icons-left has-icons-right">
                                    <form:input path="cityName" cssClass="input"/>
                                    <span class="icon is-small is-left">
                                        <i class="fas fa-envelope"></i>
                                     </span>
                                </div>
                                <form:errors path="cityName" cssClass="has-text-danger"/>
                            </div>

                            <div class="field">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Ulica</th>
                                        <th>Numer mieszkania</th>
                                        <th>Numer domu</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <div class="control has-icons-left">
                                                <form:input path="streetName" cssClass="input"/>
                                                <span class="icon is-small is-left">
                                                        <i class="fas fa-envelope"></i>
                                                    </span>
                                            </div>
                                            <form:errors path="streetName" cssClass="has-text-danger"/>
                                        </td>
                                        <td>
                                            <div class="control">
                                                <form:input path="streetNumber" cssClass="input"/>
                                            </div>
                                            <form:errors path="streetNumber" cssClass="has-text-danger"/>
                                        </td>
                                        <td>
                                            <div class="control">
                                                <form:input path="apartmentNumber" cssClass="input"/>
                                            </div>
                                            <form:errors path="apartmentNumber" cssClass="has-text-danger"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>

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

    <footer class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </footer>


</section>
</body>
</html>