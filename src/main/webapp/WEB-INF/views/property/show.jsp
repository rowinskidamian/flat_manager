<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                        <td>Tutaj będzie liczba pokoi</td>
                        <td>${propertyData.billsRentAmount}</td>
                        <td>${propertyData.billsUtilityAmount}</td>
                        <td>${propertyData.billsPaymentDate}</td>
                        <td>${propertyData.cityName}</td>
                        <td>${propertyData.streetName}</td>
                        <td>${propertyData.streetNumber}</td>
                        <td>${propertyData.firstName} ${propertyData.lastName}</td>
                        <td>${propertyData.email}</td>
                    </tr>

                    </tbody>
                </table>

    </div>
</section>

<footer class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

</body>
</html>
