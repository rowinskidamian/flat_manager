<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - lista mieszkań</title>
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
                    Lista mieszkań
                </h1>
                <h2 class="subtitle">
                    Przeglądaj i edytuj listę swoich mieszkań. </strong>
                </h2>
            </div>
        </div>
    </section>

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="notification has-text-centered is-light">
                    <strong>Dane mieszkania</strong>
                </div>

                <table class="table">
                    <thead>
                    <tr>
                        <th>Lp.</th>
                        <th>Liczba pokoi</th>
                        <th>Czynsz</th>
                        <th>Opłaty</th>
                        <th>Data płatności</th>
                        <th>Miasto</th>
                        <th>Ulica</th>
                        <th>Numer</th>
                        <th>Właściciel</th>
                        <th>E-mail</th>
                        <th>Akcja</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="property" items="${propertyList}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${property.roomsNumber}</td>
                            <td>${property.billsRentAmount}</td>
                            <td>${property.billsUtilityAmount}</td>
                            <td>${property.billsPaymentDay}</td>
                            <td>${property.cityName}</td>
                            <td>${property.streetName}</td>
                            <td>${property.addressFullNumber}</td>
                            <td>${property.ownerName}</td>
                            <td>${property.email}</td>
                            <td>
                                <div class="field is-grouped">
                                    <div class="control">
                                        <a class="button is-link" href="/property/show/${property.id}">
                                            Szczegóły
                                        </a>
                                    </div>
                                    <div class="control">
                                        <a class="button is-link is-light" href="/property/edit/${property.id}">
                                            Edycja
                                        </a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>

                <div id="actions" class="field">

                    <div class="field is-grouped">
                        <div class="control">
                            <a class="button is-warning" href="/property/add">Dodaj mieszkanie</a>
                        </div>
                    </div>

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
