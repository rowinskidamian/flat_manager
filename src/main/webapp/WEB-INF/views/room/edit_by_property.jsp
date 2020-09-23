<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - edycja pokoi w mieszkaniu</title>
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
                    Edycja pokoi
                </h1>
                <h2 class="subtitle">
                    Nazwa obiektu: <strong>${propertyData.workingName} </strong>
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
                        <th>Liczba pokoi</th>
                        <th>Czynsz</th>
                        <th>Opłaty</th>
                        <th>Data płatności</th>
                        <th>Miasto</th>
                        <th>Ulica</th>
                        <th>Numer</th>
                        <th>Imię i nazwisko właściciela</th>
                        <th>E-mail</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${propertyData.roomsNumber}</td>
                        <td>${propertyData.billsRentAmount}</td>
                        <td>${propertyData.billsUtilityAmount}</td>
                        <td>${propertyData.billsPaymentDate}</td>
                        <td>${propertyData.cityName}</td>
                        <td>${propertyData.streetName}</td>
                        <td>${propertyData.addressFullNumber}</td>
                        <td>${propertyData.ownerName}</td>
                        <td>${propertyData.email}</td>
                    </tr>

                    </tbody>
                </table>

                <div id="actions" class="field">

                    <div class="field is-grouped">
                        <div class="control">
                            <a class="button is-link" href="/property/edit/${propertyData.id}">Edytuj dane</a>
                        </div>
                        <div class="control">
                            <a class="button is-link is-light" href="/property/delete/${propertyData.id}">
                                Usuń mieszkanie</a>
                        </div>
                    </div>

                </div>


                <div class="columns">
                    <div class="column is-one-third">
                        <div class="notification is-light">
                            <strong>Pokoje</strong>
                        </div>

                        <table class="table">
                            <thead>
                            <tr>
                                <th>Lp.</th>
                                <th>Czynsz</th>
                                <th>Akcja</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:set var="totalIncomeFromRooms" value="${0}" scope="page"/>

                            <c:forEach items="${propertyData.rooms}" var="room" varStatus="counter">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${room.catalogRent}</td>
                                    <td>
                                        <div class="field is-grouped">
                                            <div class="control">
                                                <a class="button is-link is-light" href="/room/delete/${room.id}">
                                                    Usuń</a>
                                            </div>
                                            <c:if test="${room.tenantId eq null}">
                                                <div class="control">
                                                    <a class="button is-link is-warning" href="/tenant/add_to_room/${room.id}">
                                                        Dodaj najemcę</a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                                <c:set var="totalIncomeFromRooms" value="${totalIncomeFromRooms + room.catalogRent}"/>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>

                    <div class="column is-two-third">
                        <div class="notification is-light">
                            <strong>Dodaj pokój</strong>
                        </div>

                        <div id="rooms-form" class="field">


                            <form:form action="/room/edit_by_property" method="post" modelAttribute="roomData">
                                <label class="label">Wprowadź czynsz:</label>
                                <div class="field is-grouped">
                                    <p class="control is-expanded">
                                        <input class="input" type="number" name="catalogRent" placeholder="Podaj kwotę">
                                    </p>
                                    <p class="control">
                                        <button class="button is-link" type="submit">Dodaj</button>
                                    </p>
                                </div>
                                <form:errors path="catalogRent" cssClass="has-text-danger"/>
                                <input type="hidden" name="propertyId" value="${propertyData.id}">
                                <sec:csrfInput/>
                            </form:form>

                        </div>

                    </div>

                </div>

                <div class="field is-grouped">
                    <div class="control">
                        <a class="button is-success" href="/property/show/${propertyData.id}">
                            Zapisz zmiany</a>
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
