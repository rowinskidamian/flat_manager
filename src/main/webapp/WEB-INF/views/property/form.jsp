<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Formularz dodawania mieszkania</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>
<section>
    <section class="navbar">
        <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
    </section>
</section>

<section class="section">
    <div class="container is-fluid">

        <div class="columns is-vcentered">

            <div class="column is-three-quarters">
                <div class="block has-text-centered">
                    <h1 class="title">
                        Formularz Dodawania mieszkania
                    </h1>
                    <p class="subtitle">
                        Wpisz swoje dane, aby dodać mieszkanie.
                    </p>
                </div>

                <form:form modelAttribute="propertyData" method="post">

                    <div class="field">
                        <label class="label">Nazwa mieszkania</label>
                        <div class="control">
                            <form:input path="workingName" cssClass="input"/>
                        </div>
                        <form:errors path="workingName" cssClass="has-text-danger"/>
                    </div>

                    <div id="payment-details" class="field">

                        <div class="notification is-light">
                            <strong>Płatności</strong>
                        </div>

                        <div class="block">

                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Czynsz</th>
                                    <th>Opłaty miesięczne</th>
                                    <th>Termin płatności</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <div class="control">
                                            <input type="number" name="billsRentAmount" class="input">
                                        </div>
                                        <form:errors path="billsRentAmount" cssClass="has-text-danger"/>
                                    </td>
                                    <td>
                                        <div class="control">
                                            <input type="number" name="billsUtilityAmount" class="input">
                                        </div>
                                        <form:errors path="billsUtilityAmount" cssClass="has-text-danger"/>
                                    </td>
                                    <td>
                                        <div class="control">
                                            <input type="date" name="billsPaymentDate" class="input">
                                        </div>
                                        <form:errors path="billsPaymentDate" cssClass="has-text-danger"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                        </div>
                    </div>

                    <div id="adress-details" class="field">

                        <div class="notification is-light">
                            <strong>Dane adresowe</strong>
                        </div>

                        <div class="block">

                            <div class="field">
                                <label class="label">Miasto</label>
                                <div class="control has-icons-left has-icons-right">
                                    <form:input path="cityName" cssClass="input"/>
                                    <span class="icon is-small is-left">
                                        <i class="fas fa-envelope"></i>
                                     </span>
                                </div>
                                <form:errors path="cityName" cssClass="has-text-danger"/>
                            </div>

                            <div class="field">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Ulica</th>
                                        <th>Numer mieszkania</th>
                                        <th>Numer domu</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <div class="control has-icons-left">
                                                <form:input path="streetName" cssClass="input"/>
                                                <span class="icon is-small is-left">
                                                        <i class="fas fa-envelope"></i>
                                                    </span>
                                            </div>
                                            <form:errors path="streetName" cssClass="has-text-danger"/>
                                        </td>
                                        <td>
                                            <div class="control">
                                                <input type="number" name="streetNumber" class="input">
                                            </div>
                                            <form:errors path="streetNumber" cssClass="has-text-danger"/>
                                        </td>
                                        <td>
                                            <div class="control">
                                                <input type="number" name="apartmentNumber" class="input">
                                            </div>
                                            <form:errors path="apartmentNumber" cssClass="has-text-danger"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>

                    </div>

                    <div id="contact-details" class="field">
                        <div class="notification is-light">
                            <strong>Dane kontaktowe - właściciel</strong>
                        </div>

                        <div class="block">

                            <div class="field">
                                <label class="label">Imię</label>
                                <div class="control has-icons-left has-icons-right">
                                    <form:input path="firstName" cssClass="input"/>
                                    <span class="icon is-small is-left">
                                    <i class="fas fa-user"></i>
                                </span>
                                </div>
                                <form:errors path="firstName" cssClass="has-text-danger"/>
                            </div>

                            <div class="field">
                                <label class="label">Nazwisko</label>
                                <div class="control has-icons-left has-icons-right">
                                    <form:input path="lastName" cssClass="input"/>
                                    <span class="icon is-small is-left">
                                    <i class="fas fa-user"></i>
                                </span>
                                </div>
                                <form:errors path="lastName" cssClass="has-text-danger"/>
                            </div>

                            <div class="field">
                                <label class="label">Email</label>
                                <div class="control has-icons-left has-icons-right">
                                    <form:input path="email" cssClass="input"/>
                                    <span class="icon is-small is-left">
                                    <i class="fas fa-envelope"></i>
                                </span>
                                </div>
                                <form:errors path="email" cssClass="has-text-danger"/>
                            </div>
                        </div>
                    </div>

                    <div id="rooms-details" class="field">
                        <div class="notification is-light">
                            <strong>Pokoje</strong>
                        </div>

                        <div class="control">
                            <button class="button is-link is-warning" type="button">Dodaj pokój</button>
                        </div>

                    </div>


                    <div class="field is-grouped">
                        <div class="control">
                            <button class="button is-link" type="submit">Wyślij</button>
                        </div>
                        <div class="control">
                            <button class="button is-link is-light" type="reset">Wyczyść</button>
                        </div>
                    </div>
                    <sec:csrfInput/>
                </form:form>
            </div>
        </div>

    </div>

    <footer class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </footer>


</section>
</body>
</html>