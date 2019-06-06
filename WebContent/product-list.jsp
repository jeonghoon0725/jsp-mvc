<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Universal - All In 1 Template</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="vendor/font-awesome/css/font-awesome.min.css">
    <!-- Google fonts - Roboto-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,700">
    <!-- Bootstrap Select-->
    <link rel="stylesheet" href="vendor/bootstrap-select/css/bootstrap-select.min.css">
    <!-- owl carousel-->
    <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.carousel.css">
    <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.theme.default.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="css/style.blue.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="css/custom.css">
    <!-- Favicon and apple touch icons-->
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="img/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="57x57" href="img/apple-touch-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="72x72" href="img/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="img/apple-touch-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="img/apple-touch-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="img/apple-touch-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="img/apple-touch-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="img/apple-touch-icon-152x152.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
  </head>
  <body>
    <div id="all">
     <%@ include file="top-nav.jsp" %>
      
      
      <div id="content">
        <div class="container">
          <section class="bar">
            <div class="row">
              <div class="col-md-12">
                <div class="heading">
                <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
                  <h2>product list</h2>
                          <a href="product-list.do" class="btn btn-template">일련번호 정렬</a>
                          <a href="product-list1.do" class="btn btn-template">이름순 정렬</a>
                  		
                </div>
                
              </div>
            </div>
            <div class="row portfolio text-center">
            
            
            <c:forEach var="product" items="${productList}">
            
              <div class="col-md-6">
                <div class="box-image">
                  <div class="image"><img src="files/${product.image}" alt="" class="img-fluid">
                    <div class="overlay d-flex align-items-center justify-content-center">
                      <div class="content">
                        <div class="name">
                          <h3><a href="portfolio-detail.html" class="color-white">${product.pno}</a></h3>
                        </div>
                        <div class="text">
                        <p class="d-none d-sm-block">상품정보 : ${product.name }</p>
                        <p class="buttons">
                          <a href="product-detail.do?pno=${product.pno }" class="btn btn-template-outlined-white">보기</a>
                        <c:if test="${sessionScope.email == 'admin@induk.ac.kr'}">
                          <a href="product-info.do?pno=${product.pno }" class="btn btn-template-outlined-white">수정</a>
                          <a href="product-delete.do?pno=${product.pno }" class="btn btn-template-outlined-white">삭제</a>
                        </c:if>
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              </c:forEach>
              </div>
              
              <div class="row">
              <div class="col-md-5"></div>
              <div class="col-md-2">
              		<nav aria-label="Page navigation example">
                      <ul class="pagination">
                      <!-- pagination 속성은 product-list.do에서 전달된다. -->
                      <c:if test="${pagination.beginPage > pagination.paginationCount }">
                        <li class="page-item">
                          <a href="product-list.do?pageNum=${pagination.beginPage - 1 }" class="page-link">이전</a>
                        </li>
                      </c:if>
                      <c:forEach var="pnum" begin="${pagination.beginPage}" end="${pagination.endPage}">
             			<c:if test="${pnum == pagination.curPage }">
                        	<li class="page-item active">
                        </c:if>
                        <a href="product-list.do?pageNum=${pnum }" class="page-link">${pnum }</a></li>
                      </c:forEach>
                      <c:if test="${pagination.endPage < pagination.totalPages}">
                        <li class="page-item">
                        	<a href="product-list.do?pageNum=${pagination.endPage + 1 }" class="page-link">다음</a>
                        </li>
                      </c:if>
                      </ul>
                    </nav>
                </div>
                <div class="col-md-5"></div>
                </div>
                    
              </section>
        	</div>
        <section class="bar background-pentagon no-mb">
          <div class="container">
            <div class="row showcase text-center">
              <div class="col-md-3 col-sm-6">
                <div class="item">
                  <div class="icon-outlined icon-sm icon-thin"><i class="fa fa-align-justify"></i></div>
                  <h4><span class="h1 counter">580</span><br> Websites</h4>
                </div>
              </div>
              <div class="col-md-3 col-sm-6">
                <div class="item">
                  <div class="icon-outlined icon-sm icon-thin"><i class="fa fa-users"></i></div>
                  <h4><span class="h1 counter">100</span><br>Satisfied Clients</h4>
                </div>
              </div>
              <div class="col-md-3 col-sm-6">
                <div class="item">
                  <div class="icon-outlined icon-sm icon-thin"><i class="fa fa-copy"></i></div>
                  <h4><span class="h1 counter">320</span><br>Projects</h4>
                </div>
              </div>
              <div class="col-md-3 col-sm-6">
                <div class="item">
                  <div class="icon-outlined icon-sm icon-thin"><i class="fa fa-font"></i></div>
                  <h4><span class="h1 counter">923</span><br>Magazines and Brochures</h4>
                </div>
              </div>
            </div>
          </div>
        </section>
        <section class="bar bg-gray">
          <div class="container">
            <div class="heading text-center">
              <h2>Our Clients</h2>
            </div>
            <ul class="list-unstyled owl-carousel customers no-mb">
              <li class="item"><img src="img/customer-1.png" alt="" class="img-fluid"></li>
              <li class="item"><img src="img/customer-2.png" alt="" class="img-fluid"></li>
              <li class="item"><img src="img/customer-3.png" alt="" class="img-fluid"></li>
              <li class="item"><img src="img/customer-4.png" alt="" class="img-fluid"></li>
              <li class="item"><img src="img/customer-5.png" alt="" class="img-fluid"></li>
              <li class="item"><img src="img/customer-6.png" alt="" class="img-fluid"></li>
            </ul>
          </div>
        </section>
      </div>
      
      
      <%@ include file="getit-footer.jsp" %>
    </div>
    <!-- Javascript files-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper.js/umd/popper.min.js"> </script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="vendor/waypoints/lib/jquery.waypoints.min.js"> </script>
    <script src="vendor/jquery.counterup/jquery.counterup.min.js"> </script>
    <script src="vendor/owl.carousel/owl.carousel.min.js"></script>
    <script src="vendor/owl.carousel2.thumbs/owl.carousel2.thumbs.min.js"></script>
    <script src="js/jquery.parallax-1.1.3.js"></script>
    <script src="vendor/bootstrap-select/js/bootstrap-select.min.js"></script>
    <script src="vendor/jquery.scrollto/jquery.scrollTo.min.js"></script>
    <script src="js/front.js"></script>
  </body>
</html>