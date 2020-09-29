<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - adres najemcy</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
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

                <div class="notification has-text-centered is-light">
                    <strong>Szczegóły adresu najemcy</strong>
                </div>

                <table class="table">
                    <thead>
                    <tr>
                        <th>Pole</th>
                        <th>Szczegóły</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>Imię</th>
                        <td>${tenantAddressData.firstName}</td>
                    </tr>
                    <tr>
                        <th>Nazwisko</th>
                        <td>${tenantAddressData.lastName}</td>
                    </tr>
                    <tr>
                        <th>E-mail</th>
                        <td>${tenantAddressData.email}</td>
                    </tr>
                    <tr>
                        <th>Miasto</th>
                        <td>${tenantAddressData.cityName}</td>
                    </tr>
                    <tr>
                        <th>Ulica</th>
                        <td>${tenantAddressData.streetName}</td>
                    </tr>
                    <tr>
                        <th>Numer</th>
                        <td>${tenantAddressData.addressNumber}</td>
                    </tr>
                    </tbody>

                </table>


                <div id="actions" class="field">

                    <div class="field is-grouped">
                        <div class="control">
                            <a class="button is-link" href="/tenant">Powrót do listy</a>
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
