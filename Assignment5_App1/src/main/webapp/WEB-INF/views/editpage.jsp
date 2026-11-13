<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.*"%>
<%@ page import="com.nagarro.training.model.Book"%>
<%@ page import="com.nagarro.training.model.Author"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
	crossorigin="anonymous"></script>
<title>Edit Book</title>
</head>
<body>
	<div class="container">

		<div class="fixed-top">

			<header class="border text-end"
				style="background: linear-gradient(to bottom, #99bbff 0%, #ffffff 100%); height: 80px">
				<div class="mt-3 me-4">
					<form action="logout" method="post">
						<b>Hi ${username} <input type="submit"
							class="btn btn-outline-primary" value="Logout"></b>
					</form>
				</div>
			</header>
		</div>
		<div style="height: 80px"></div>
		<div class="row">
			<h2 class="text-center mt-3 mb-5">Edit Book Details</h2>
			<form action="editBook" method="post" id="editform">
				<div class="row">
					<div class="col-sm-4 offset-sm-1">
						<label for="bookCode" class="form-label">Book Code</label>
					</div>
					<div class="col-sm-5">
						<input type="text" name="bookCode" value="${book.getId()}"
							class="form-control" readonly="readonly" required="required" />
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-4 offset-sm-1">
						<label for="bookName" class="form-label">Book Name</label>
					</div>
					<div class="col-sm-5">
						<input type="text" name="bookName" value="${book.getName()}"
							class="form-control" required="required" />
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-4 offset-sm-1">
						<label for="authorId" class="form-label">Author</label>
					</div>
					<div class="col-sm-5">
						<select class="form-select" name="authorId" required="required">
							<option disabled>Select Author</option>
							<c:forEach items="${authors}" var="author">
								<c:choose>
									<c:when test="${book.getAuthor().getId()==author.getId()}">
										<option selected value="${author.getId()}">${author.getName()}</option>
									</c:when>
									<c:otherwise>
										<option value="${author.getId()}">${author.getName()}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-4 offset-sm-1">
						<label for="date" class="form-label">Date</label>
					</div>
					<div class="col-sm-5">
						<input type="text" value="${date}" name="date"
							class="form-control" readonly="readonly" required="required" />
					</div>
				</div>
			</form>
		</div>
		<form action="back" id="cancelform" method="post"></form>
		<div class="row mt-3">
			<div class="ms-5 col-sm-3">
				<input form="editform" type="submit" class="btn btn-primary"
					value="Submit">
			</div>
			<div class="col-sm-3">
				<input form="cancelform" type="submit" class="btn btn-danger"
					value="Cancel">
			</div>
		</div>
		<div style="height: 80px"></div>
		<div class="border fixed-bottom"
			style="background: linear-gradient(to bottom, #ffffff 0%, #99bbff 100%); height: 80px">
			<footer class="text-center"></footer>
		</div>
	</div>
</body>
</html>