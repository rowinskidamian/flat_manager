<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - lista pokoi</title>
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
                    Lista pokoi
                </h1>
                <h2 class="subtitle">
                    Przeglądaj i edytuj listę pokoi, kwateruj i wykwateruj najemcę. </strong>
                </h2>
            </div>
        </div>
    </section>

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="notification has-text-centered is-light">
                    <strong>Szczegóły pokoi</strong>
                </div>

                <table class="table">
                    <thead>
                    <tr>
                        <th>Lp.</th>
                        <th>Nazwa mieszkania</th>
                        <th>Czynsz</th>
                        <th>Najemca</th>
                        <th>Akcja</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="room" items="${roomList}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td><a href="/property/show/${room.propertyId}">${room.apartmentWorkingName}</a></td>
                            <td>${room.catalogRent}</td>
                            <td>
                                <c:if test="${room.tenantId ne null}">
                                    <a href="/tenant/show/${room.tenantId}">${room.tenantFullName}</a>
                                </c:if>
                                <c:if test="${room.tenantId eq null}">
                                    Brak
                                </c:if>
                            </td>
                            <td>
                                <div class="field is-grouped">
                                    <c:if test="${room.tenantId ne null}">
                                        <div class="control">
                                            <a class="button is-warning" href="/tenant/remove_from_room/${room.id}">
                                                Usuń najemcę
                                            </a>
                                        </div>
                                    </c:if>
                                    <c:if test="${room.tenantId eq null}">
                                        <div class="control">
                                            <a class="button is-success" href="/tenant/add_to_rom/${room.id}">
                                                Dodaj najemcę
                                            </a>
                                        </div>
                                    </c:if>

                                    <div class="control">
                                        <a class="button is-link is-light" href="/room/edit/${room.id}">
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
                            <a class="button is-success" href="/room/add">Dodaj pokój</a>
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
