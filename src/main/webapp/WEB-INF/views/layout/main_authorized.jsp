<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="tile is-ancestor">
        <div class="tile is-4 is-vertical is-parent">
            <div class="tile is-child box">
                <p class="title is-4">Statystyki:</p>

                <div class="content">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Liczba pokoi</th>
                            <th>Wynajęte</th>
                            <th>Dostępne</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${noOfRoomsTotal}</td>
                            <td>${noOfRoomsRented}</td>
                            <td>${noOfRoomsTotal - noOfRoomsRented}</td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Liczba najemców</th>
                            <th>W pokojach</th>
                            <th>Bez pokoi</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${noOfTenantsTotal}</td>
                            <td>${noOfRoomsRented}</td>
                            <td>${noOfTenantsTotal - noOfRoomsRented}</td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="table">
                        <tr>
                            <th>Liczba mieszkań</th>
                        </tr>
                        <tr>
                            <td>${noOfPropertiesTotal}</td>
                        </tr>
                    </table>
                </div>

            </div>
            <div class="tile is-child box">
                <p class="title is-4">Stan płatności najemców:</p>

                <div class="content">
                    <c:if test="${currentBalance eq 0}">
                        <div class="notification is-info">
                            Kwota: <c:out value="${currentBalance}"/>
                        </div>
                    </c:if>

                    <c:if test="${currentBalance > 0}">
                        <div class="notification is-success">
                            Nadpłata: <c:out value="${currentBalance}"/>
                        </div>
                    </c:if>

                    <c:if test="${currentBalance < 0}">
                        <div class="notification is-danger">
                            Niedopłata: <c:out value="${currentBalance}"/>
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
        <div class="tile is-parent">
            <div class="tile is-child box">
                <p class="title is-4">Przejdź do wybranej sekcji:</p>
                <div class="buttons is-centered">
                    <a class="button is-link is-large" href="/property">Mieszkania</a>
                    <a class="button is-info is-large" href="/room">Pokoje</a>
                    <a class="button is-primary is-large" href="/tenant">Najemcy</a>
                    <a class="button is-success is-large" href="/payment">Płatności</a>
                    <a class="button is-warning is-large" href="/payment_balance/">Salda kont</a>
                </div>
            </div>
        </div>
    </div>
</div>

