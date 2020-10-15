<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - salda kont</title>
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
                    Salda kont
                </h1>
                <h2 class="subtitle">
                    Przeglądaj salda kont. Sprawdź czy, czy saldo jest ujemne (niedopłata), czy dodatnie (nadpłata).
                    Obciąż każde konto czynszem za pokój. </strong>
                </h2>
            </div>
        </div>
    </section>

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="block">

                    <div class="notification has-text-centered is-light">
                        <strong>Saldo konta dla użytkownika</strong>
                    </div>

                    <table class="table">
                        <thead>
                        <tr>
                            <th>Data aktualizacji</th>
                            <th>Stan konta</th>
                            <th>Nazwa</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${userBalance.currentBalanceDate}</td>
                                <td>${userBalance.currentBalance}</td>
                                <td>${userBalance.balanceHolderName}</td>
                            </tr>
                        </tbody>
                    </table>

                </div>

                <div class="block">
                    <div id="actions" class="field">

                        <div class="field is-grouped">
                            <div class="control">
                                <a class="button is-success" href="/payment_balance/collectrent">
                                    Zbierz czynsz od najemców</a>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="block">

                    <div class="notification has-text-centered is-light">
                        <strong>Salda kont mieszkań</strong>
                    </div>

                    <table class="table">
                        <thead>
                        <tr>
                            <th>Lp.</th>
                            <th>Data aktualizacji</th>
                            <th>Stan konta</th>
                            <th>Nazwa</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="propertyBalance" items="${propertyBalanceList}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${propertyBalance.currentBalanceDate}</td>
                                <td>${propertyBalance.currentBalance}</td>
                                <td>${propertyBalance.balanceHolderName}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

                </div>

                <div class="block">

                    <div class="notification has-text-centered is-light">
                        <strong>Salda kont najemców</strong>
                    </div>

                    <table class="table">
                        <thead>
                        <tr>
                            <th>Lp.</th>
                            <th>Data aktualizacji</th>
                            <th>Stan konta</th>
                            <th>Nazwa</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="tenantBalance" items="${tenantBalanceList}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${tenantBalance.currentBalanceDate}</td>
                                <td>${tenantBalance.currentBalance}</td>
                                <td>${tenantBalance.balanceHolderName}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

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
