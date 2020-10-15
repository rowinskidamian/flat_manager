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
                Lista płatności
            </h1>
            <h2 class="subtitle">
                Przeglądaj i edytuj listę płatności. Dodaj płatności od najemców, aby powiększyć stan konta.
            </h2>
        </div>
    </div>
</section>

<section class="section">

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <table class="table">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Płatności dla najemcy:</th>
                        <th>Płatności dla mieszkania:</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <div class="field is-grouped">
                                <div class="control">
                                    <a class="button is-success" href="/payment/add">Dodaj płatność</a>
                                </div>
                                <div class="control">
                                    <a class="button is-link is-light" href="/payment">Wyświetl wszystkie</a>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="dropdown is-hoverable">
                                <div class="dropdown-trigger">
                                    <button class="button" aria-haspopup="true" aria-controls="dropdown-menu">
                                        <span>Wybierz najemcę</span>
                                        <span class="icon is-small">
                                         <i class="fas fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </button>
                                </div>
                                <div class="dropdown-menu" id="dropdown-menu" role="menu">
                                    <div class="dropdown-content">
                                        <c:forEach items="${tenantList}" var="tenant">
                                        <a href="/payment/show/for_tenant/${tenant.tenantId}" class="dropdown-item">
                                            ${tenant.tenantFullName}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="dropdown is-hoverable">
                                <div class="dropdown-trigger">
                                    <button class="button" aria-haspopup="true" aria-controls="dropdown-menu2">
                                        <span>Wybierz mieszkanie</span>
                                        <span class="icon is-small">
                                         <i class="fas fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </button>
                                </div>
                                <div class="dropdown-menu" id="dropdown-menu2" role="menu">
                                    <div class="dropdown-content">
                                        <c:forEach items="${propertyList}" var="property">
                                            <a href="/payment/show/for_property/${property.propertyId}" class="dropdown-item">
                                                    ${property.propertyWorkingName}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>

                    </tbody>
                </table>

                <div class="notification has-text-centered is-light">
                    Szczegóły płatności: <strong>${title}</strong>
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
                                <a href="/payment/show/for_tenant/${payment.tenantId}">${payment.tenantFullName}</a>
                            </td>
                            <td>
                                <a href="/payment/show/for_property/${payment.propertyId}">${payment.propertyWorkingName}</a>
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
