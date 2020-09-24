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

    <section class="hero is-info">
        <div class="hero-body">
            <div class="container">
                <h1 class="title">
                    Przegląd mieszkania
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
                        <td>${propertyData.billsPaymentDay}</td>
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
                                <th>Najemca</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:set var="totalIncomeFromRooms" value="${0}" scope="page"/>
                            <c:set var="actualIncomeFromRooms" value="${0}" scope="page"/>

                            <c:forEach items="${propertyData.rooms}" var="room" varStatus="counter">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${room.catalogRent}</td>
                                    <td>
                                        <c:if test="${room.tenatFullName eq null}">
                                            brak
                                        </c:if>
                                            ${room.tenatFullName}
                                    </td>
                                </tr>
                                <c:set var="totalIncomeFromRooms" value="${totalIncomeFromRooms + room.catalogRent}"/>
                                <c:if test="${room.tenatFullName != null}">
                                    <c:set var="actualIncomeFromRooms" value="${actualIncomeFromRooms + room.catalogRent}"/>
                                </c:if>

                            </c:forEach>
                            </tbody>
                        </table>

                        <div class="field is-grouped">
                            <div class="control">
                                <a class="button is-link" href="/room/edit_by_property/${propertyData.id}">
                                    Edytuj pokoje</a>
                            </div>
                        </div>

                    </div>

                    <div class="column is-two-third">
                        <div class="notification is-light">
                            <strong>Wynik finansowy</strong>
                        </div>

                        <div class="block">
                            <h3 class="subtitle">Aktualny wynik:</h3>
                        </div>

                        <c:set var="billsForApartment"
                               value="${propertyData.billsRentAmount + propertyData.billsUtilityAmount}"/>
                        <c:set var="profit" value="${actualIncomeFromRooms - billsForApartment}"/>

                        <table class="table">
                            <thead>
                            <tr>
                                <th>Przychód</th>
                                <th>Wydatki</th>
                                <th>Wynik</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${actualIncomeFromRooms}</td>
                                <td>
                                    <c:out value="${billsForApartment}"/>
                                </td>
                                <td>
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
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <div class="block">
                            <h3 class="subtitle">Potencjalny wynik:</h3>
                        </div>

                        <c:set var="billsForApartment"
                               value="${propertyData.billsRentAmount + propertyData.billsUtilityAmount}"/>
                        <c:set var="profit" value="${totalIncomeFromRooms - billsForApartment}"/>

                        <table class="table">
                            <thead>
                            <tr>
                                <th>Przychód</th>
                                <th>Wydatki</th>
                                <th>Wynik</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${totalIncomeFromRooms}</td>
                                <td>
                                    <c:out value="${billsForApartment}"/>
                                </td>
                                <td>
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
                                </td>
                            </tr>
                            </tbody>
                        </table>


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
