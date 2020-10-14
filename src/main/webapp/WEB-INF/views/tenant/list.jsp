<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - lista najemców</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
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
                    Lista najemców
                </h1>
                <h2 class="subtitle">
                    Przeglądaj i edytuj listę najemców.</strong>
                </h2>
            </div>
        </div>
    </section>

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="notification has-text-centered is-light">
                    <strong>Szczegóły najemców</strong>
                </div>

                <table class="table">
                    <thead>
                    <tr>
                        <th>Lp.</th>
                        <th>Nazwa mieszkania</th>
                        <th>Imię i nazwisko</th>
                        <th>Czynsz (z rabatem)</th>
                        <th>Wysokość rabatu</th>
                        <th>E-mail</th>
                        <th>Data zawarcia umowy</th>
                        <th>Data zakończenia umowy</th>
                        <th>Akcje</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="tenant" items="${tenantList}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <c:if test="${tenant.propertyId eq null}">Brak mieszkania</c:if>
                                <a href="/property/show/${tenant.propertyId}">${tenant.apartmentName}</a>
                            </td>
                            <td>${tenant.fullName}</td>
                            <td><c:if test="${tenant.currentRent eq null}">Brak pokoju</c:if> ${tenant.currentRent}</td>
                            <td><c:if test="${tenant.rentDiscount eq null}">Brak</c:if> ${tenant.rentDiscount}</td>
                            <td>${tenant.email}</td>
                            <td>${tenant.leaseDateStart}</td>
                            <td>${tenant.leaseDateEnd}</td>
                            <td>
                                <div class="field is-grouped">
                                    <c:if test="${tenant.currentRent ne null}">
                                        <div class="control">
                                            <a class="button is-link is-warning"
                                               href="/room/checkout/from_room/${tenant.roomId}">Wykwateruj</a>
                                        </div>
                                    </c:if>
                                    <c:if test="${tenant.currentRent eq null}">
                                        <div class="control">
                                            <a class="button is-link is-success"
                                               href="/room/checkin/tenants_list/tenant/${tenant.id}">Zakwateruj</a>
                                        </div>
                                    </c:if>
                                    <div class="control">
                                        <a class="button is-light" href="/tenant/address/${tenant.id}">Adres</a>
                                    </div>
                                    <div class="control">
                                        <a class="button is-light" href="/tenant/edit/${tenant.id}">
                                            Edytuj
                                        </a>
                                    </div>
                                    <div class="control">
                                        <a class="button is-link is-light" href="/tenant/delete/${tenant.id}">
                                            Usuń
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
                            <a class="button is-success" href="/tenant/add">Dodaj najemcę</a>
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
