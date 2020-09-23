<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - wyświetlanie mieszkania</title>
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

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="block has-text-centered">

                    <h1 class="title">
                        Szczegóły mieszkania
                    </h1>
                    <p class="subtitle">
                        Dla obiektu o nazwie: <strong>${propertyData.workingName} </strong>.
                    </p>
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

                <div id="rooms-details" class="field">
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
                                    <div class="control">
                                        <a class="button is-link is-light" href="/room/delete_by_property/${room.id}">
                                            Usuń pokój</a>
                                    </div>
                                </td>
                            </tr>
                            <c:set var="totalIncomeFromRooms" value="${totalIncomeFromRooms + room.catalogRent}"/>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div id="rooms-form" class="field">

                        <form action="/room/add_by_property" method="post">
                            <label class="label">Dodaj pokój - wprowadź czynsz:</label>
                            <div class="field is-grouped">
                                <p class="control is-expanded">
                                    <input class="input" type="number" name="rent" placeholder="Wprowadź kwotę">
                                </p>
                                <p class="control">
                                    <button class="button is-link" type="submit">Dodaj</button>
                                </p>
                            </div>
                            <input type="hidden" name="propertyId" value="${propertyData.id}">
                            <sec:csrfInput/>
                        </form>

                    </div>


                </div>

                <div id="property summary" class="field">
                    <div class="notification is-light">
                        <div class="columns is-vcentered">
                            <div class="column ">
                                Pokoje: ${propertyData.roomsNumber}
                            </div>
                            <div class="column">
                                Przychód: ${totalIncomeFromRooms}
                            </div>
                            <div class="column">
                                <c:set var="billsForApartment"
                                       value="${propertyData.billsRentAmount + propertyData.billsUtilityAmount}"/>
                                Wydatki: <c:out value="${pageScope.billsForApartment}"/>
                            </div>

                            <div class="column">
                                <c:set var="profit" value="${totalIncomeFromRooms - billsForApartment}"/>
                                <c:if test="${profit >= 0}">
                                    <div class="notification is-success">
                                        Zysk: <c:out value="${profit}"/>
                                    </div>
                                </c:if>

                                <c:if test="${profit < 0}">
                                    <div class="notification is-danger">
                                        Strata: <c:out value="${profit}"/>
                                    </div>
                                </c:if>


                            </div>
                        </div>
                    </div>

                    <div id="actions" class="field">

                        <div class="field is-grouped">
                            <div class="control">
                                <a class="button is-link is-warning" type="button">Dodaj pokój</a>
                            </div>

                            <div class="control">
                                <a class="button is-link" href="/property/edit/${propertyData.id}">Edytuj mieszkanie</a>
                            </div>
                            <div class="control">
                                <a class="button is-link is-light" href="/property/delete/${propertyData.id}">
                                    Usuń mieszkanie</a>
                            </div>
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
