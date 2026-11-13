<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.nagarro.training.model.Book"%>

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
<script type="text/javascript">
	function confirmDelete() {
		//alert("confirmDelete");
		var answer = confirm("Are you sure you want to delete");
		if (answer == true) {
			return true;
		} else {
			return false;
		}
	}
</script>
<title>Dashboard</title>
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
		<div>
			<div class="row mb-3">
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<h1>Books Listing</h1>
				</div>
				<div class="col-sm-4">
					<form action="addView" method="get">
						<input type="submit" class="btn btn-primary" value="Add a book">
					</form>
				</div>
			</div>
			<table class="table table-bordered w-100 text-center">
				<thead>
					<tr>
						<th>Book Code</th>
						<th>Book Name</th>
						<th>Author</th>
						<th>Date Added</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${bookList.size() > 0}">
						<c:forEach items="${bookList}" var="book">
							<tr>
								<td>${book.getId()}</td>
								<td>${book.getName()}</td>
								<td>${book.getAuthor().getName()}</td>
								<td>${book.getDate()}</td>
								<td>
								<div class="row">
									<div class="col-sm-6">
										<form action="editView" method="post">
											<input type="submit" name="edit"
												class="btn btn-outline-dark w-100" value="Edit"> <input
												type="hidden" name="id" value="${book.getId()}" />
										</form>
									</div>
									<div class="col-sm-6">
										<form action="delete" method="post">
											<input type="submit" name="delete"
												class="btn btn-outline-danger w-100" value="Delete"
												onclick="{return confirmDelete();}"> <input
												type="hidden" name="id" value="${book.getId()}" />
										</form>
									</div>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
		<div style="height: 80px"></div>
		<div class="border fixed-bottom"
			style="background: linear-gradient(to bottom, #ffffff 0%, #99bbff 100%); height: 80px">
			<footer class="text-center"></footer>
		</div>
	</div>
</body>
</html>