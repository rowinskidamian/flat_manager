<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - lista płatności</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
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
                Lista płatności
            </h1>
            <h2 class="subtitle">
                Przeglądaj i edytuj listę płatności.
            </h2>
        </div>
    </div>
</section>

<section class="section">

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="block">
                    <div class="level-left">
                        <div class="control">
                            <a class="button is-success" href="/payment/add">Dodaj płatność</a>
                        </div>
                    </div>
                </div>

                <div class="notification has-text-centered is-light">
                    <strong>Szczegóły płatności</strong>
                </div>

                <table class="table">
                    <thead>
                    <tr>
                        <th>Lp.</th>
                        <th>Data płatności</th>
                        <th>Najemca</th>
                        <th>Mieszkanie</th>
                        <th>Kwota</th>
                        <th>Akcja</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="payment" items="${paymentsList}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${payment.paymentDate}</td>
                            <td>
                                <a href="/payment/by_tenant/${payment.tenantId}">${payment.tenantFullName}</a>
                            </td>
                            <td>
                                <a href="/payment/by_property/${payment.propertyId}">${payment.propertyWorkingName}</a>
                            </td>
                            <td>${payment.amount}</td>
                            <td>
                                <div class="field is-grouped">
                                    <div class="control">
                                        <a class="button is-link" href="/payment/edit/${payment.id}">
                                            Edytuj
                                        </a>
                                    </div>
                                    <div class="control">
                                        <a class="button is-link is-light"
                                           href="/payment/delete/${payment.id}">
                                            Usuń
                                        </a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>

        </div>

    </div>

</section>

<footer class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

</body>
</html>
