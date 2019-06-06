<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- Top bar-->
 ${name}
      <div class="top-bar">
        <div class="container">
          <div class="row d-flex align-items-center">
            <div class="col-md-6 d-md-block d-none">
            <p>
            <%
            	if(session.getAttribute("email") == null) {
            %>
             	 관리자 : Contact us on +420 777 555 333 or hello@universal.com.</p>
            <%
                }
                else {//jsp는 html코드와 함께 쓰기 용이
            %>
            	<p>사용자 :  <%= (String) session.getAttribute("email") %></p>
            	<p>사용자 :  ${sessionScope.email }</p>
            <%
            	}
            %>
            </div>
            <div class="col-md-6">
              <div class="d-flex justify-content-md-end justify-content-between">
                <ul class="list-inline contact-info d-block d-md-none">
                  <li class="list-inline-item"><a href="#"><i class="fa fa-phone"></i></a></li>
                  <li class="list-inline-item"><a href="#"><i class="fa fa-envelope"></i></a></li>
                </ul>
                <div class="login">
                <%
                	if(session.getAttribute("email") == null) {
                %>
               		<a href="#" data-toggle="modal" data-target="#login-modal" class="login-btn"><i class="fa fa-sign-in"></i>
                	<span class="d-none d-md-inline-block">로그인</span></a>
                	<a href="customer-register.jsp" class="signup-btn"><i class="fa fa-user"></i>
                	<span class="d-none d-md-inline-block">회원 등록</span></a>
                <%
                	}
                	else {
                %>
                	<a href="member-info.do" class="signup-btn"><i class="fa fa-user"></i>
                	<span class="d-none d-md-inline-block">회원 정보</span></a>
                	<a href="logout.jsp" class="signup-btn"><i class="fa fa-user"></i>
                	<span class="d-none d-md-inline-block">로그아웃</span></a>
                <%
                	}
                %>
                </div>
                
                <ul class="social-custom list-inline">
                  <li class="list-inline-item"><a href="logout.jsp"><i class="fa fa-facebook"></i></a></li>
                  <li class="list-inline-item"><a href="#"><i class="fa fa-google-plus"></i></a></li>
                  <li class="list-inline-item"><a href="#"><i class="fa fa-twitter"></i></a></li>
                  <li class="list-inline-item"><a href="#"><i class="fa fa-envelope"></i></a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Top bar end-->
      <!-- Login Modal-->
      <div id="login-modal" tabindex="-1" role="dialog" aria-labelledby="login-modalLabel" aria-hidden="true" class="modal fade">
        <div role="document" class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 id="login-modalLabel" class="modal-title">Customer Login</h4>
              <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
              <form action="member-login.do" method="get"> <!-- 로그인 하는 폼!  member-login.do 에 email 과 pw 을 넘김-->
                <div class="form-group">
                  <!--  --><input id="email_modal" name="email" type="text" placeholder="email" class="form-control">
                </div>
                <div class="form-group">
                  <input id="password_modal" name="pw" type="password" placeholder="password" class="form-control">
                </div>
                <p class="text-center">
                  <button class="btn btn-template-outlined"><i class="fa fa-sign-in"></i> Log in</button>
                </p>
              </form>
              <p class="text-center text-muted">Not registered yet?</p>
              <p class="text-center text-muted"><a href="customer-register.jsp"><strong>Register now</strong></a>! It is easy and done in 1 minute and gives you access to special discounts and much more!</p>
            </div>
          </div>
        </div>
      </div>
      <!-- Login modal end-->
      <!-- Navbar Start-->
      <header class="nav-holder make-sticky">
        <div id="navbar" role="navigation" class="navbar navbar-expand-lg">
          <div class="container"><a href="index.jsp" class="navbar-brand home"><img src="img/logo1-small.png" alt="Universal logo" class="d-none d-md-inline-block"><img src="img/logo1-small.png" alt="Universal logo" class="d-inline-block d-md-none"><span class="sr-only">Universal - go to homepage</span></a>
            <button type="button" data-toggle="collapse" data-target="#navigation" class="navbar-toggler btn-template-outlined"><span class="sr-only">Toggle navigation</span><i class="fa fa-align-justify"></i></button>
            <div id="navigation" class="navbar-collapse collapse">
              <ul class="nav navbar-nav ml-auto">
                <li class="nav-item dropdown active"><a href="javascript: void(0)" data-toggle="dropdown" class="dropdown-toggle">Home <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li class="dropdown-item"><a href="index.jsp" class="nav-link">Option 1: Default Page</a></li>
                    <li class="dropdown-item"><a href="index2.jsp" class="nav-link">Option 2: Application</a></li>
                    <li class="dropdown-item"><a href="index3.jsp" class="nav-link">Option 3: Startup</a></li>
                    <li class="dropdown-item"><a href="index4.jsp" class="nav-link">Option 4: Agency</a></li>
                    <li class="dropdown-item"><a href="index5.jsp" class="nav-link">Option 5: Portfolio</a></li>
                  </ul>
                </li>
                <li class="nav-item dropdown menu-large"><a href="#" data-toggle="dropdown" class="dropdown-toggle">Features<b class="caret"></b></a>
                  <ul class="dropdown-menu megamenu">
                    <li>
                      <div class="row">
                        <div class="col-lg-6"><img src="img/template-easy-customize.png" alt="" class="img-fluid d-none d-lg-block"></div>
                        <div class="col-lg-3 col-md-6">
                          <h5>Shortcodes</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="template-accordions.jsp" class="nav-link">Accordions</a></li>
                            <li class="nav-item"><a href="template-alerts.jsp" class="nav-link">Alerts</a></li>
                            <li class="nav-item"><a href="template-buttons.jsp" class="nav-link">Buttons</a></li>
                            <li class="nav-item"><a href="template-content-boxes.jsp" class="nav-link">Content boxes</a></li>
                            <li class="nav-item"><a href="template-blocks.jsp" class="nav-link">Horizontal blocks</a></li>
                            <li class="nav-item"><a href="template-pagination.jsp" class="nav-link">Pagination</a></li>
                            <li class="nav-item"><a href="template-tabs.jsp" class="nav-link">Tabs</a></li>
                            <li class="nav-item"><a href="template-typography.jsp" class="nav-link">Typography</a></li>
                          </ul>
                        </div>
                        <div class="col-lg-3 col-md-6">
                          <h5>Header variations</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="template-header-default.jsp" class="nav-link">Default sticky header</a></li>
                            <li class="nav-item"><a href="template-header-nosticky.jsp" class="nav-link">No sticky header</a></li>
                            <li class="nav-item"><a href="template-header-light.jsp" class="nav-link">Light header</a></li>
                          </ul>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="nav-item dropdown menu-large"><a href="#" data-toggle="dropdown" class="dropdown-toggle">Portfolio <b class="caret"></b></a>
                  <ul class="dropdown-menu megamenu">
                    <li>
                      <div class="row">
                        <div class="col-lg-6"><img src="img/template-homepage.png" alt="" class="img-fluid d-none d-lg-block"></div>
                        <div class="col-lg-3 col-md-6">
                          <h5>Portfolio</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="portfolio-2.jsp" class="nav-link">2 columns</a></li>
                            <li class="nav-item"><a href="portfolio-no-space-2.jsp" class="nav-link">2 columns with negative space</a></li>
                            <li class="nav-item"><a href="portfolio-3.jsp" class="nav-link">3 columns</a></li>
                            <li class="nav-item"><a href="portfolio-no-space-3.jsp" class="nav-link">3 columns with negative space</a></li>
                            <li class="nav-item"><a href="portfolio-4.jsp" class="nav-link">4 columns</a></li>
                            <li class="nav-item"><a href="portfolio-no-space-4.jsp" class="nav-link">4 columns with negative space</a></li>
                            <li class="nav-item"><a href="portfolio-detail.jsp" class="nav-link">Portfolio - detail</a></li>
                            <li class="nav-item"><a href="portfolio-detail-2.jsp" class="nav-link">Portfolio - detail 2</a></li>
                          </ul>
                        </div>
                        <div class="col-lg-3 col-md-6">
                          <h5>About</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="about.jsp" class="nav-link">About us</a></li>
                            <li class="nav-item"><a href="team.jsp" class="nav-link">Our team</a></li>
                            <li class="nav-item"><a href="team-member.jsp" class="nav-link">Team member</a></li>
                            <li class="nav-item"><a href="services.jsp" class="nav-link">Services</a></li>
                          </ul>
                          <h5>Marketing</h5>
                          <ul class="list-unstyled">
                            <li class="nav-item"><a href="packages.jsp" class="nav-link">Packages</a></li>
                          </ul>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <!-- ========== FULL WIDTH MEGAMENU ==================-->
                <li class="nav-item dropdown menu-large"><a href="#" data-toggle="dropdown" data-hover="dropdown" data-delay="200" class="dropdown-toggle">All Pages <b class="caret"></b></a>
                  <ul class="dropdown-menu megamenu">
                    <li>
                      <div class="row">
                        <div class="col-md-6 col-lg-3">
                          <h5>Home</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="index.jsp" class="nav-link">Option 1: Default Page</a></li>
                            <li class="nav-item"><a href="index2.jsp" class="nav-link">Option 2: Application</a></li>
                            <li class="nav-item"><a href="index3.jsp" class="nav-link">Option 3: Startup</a></li>
                            <li class="nav-item"><a href="index4.jsp" class="nav-link">Option 4: Agency</a></li>
                            <li class="nav-item"><a href="index5.jsp" class="nav-link">Option 5: Portfolio</a></li>
                          </ul>
                          <h5>About</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="about.jsp" class="nav-link">About us</a></li>
                            <li class="nav-item"><a href="team.jsp" class="nav-link">Our team</a></li>
                            <li class="nav-item"><a href="team-member.jsp" class="nav-link">Team member</a></li>
                            <li class="nav-item"><a href="services.jsp" class="nav-link">Services</a></li>
                          </ul>
                          <h5>Marketing</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="packages.jsp" class="nav-link">Packages</a></li>
                          </ul>
                        </div>
                        <div class="col-md-6 col-lg-3">
                          <h5>Portfolio</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="portfolio-2.jsp" class="nav-link">2 columns</a></li>
                            <li class="nav-item"><a href="portfolio-no-space-2.jsp" class="nav-link">2 columns with negative space</a></li>
                            <li class="nav-item"><a href="portfolio-3.jsp" class="nav-link">3 columns</a></li>
                            <li class="nav-item"><a href="portfolio-no-space-3.jsp" class="nav-link">3 columns with negative space</a></li>
                            <li class="nav-item"><a href="portfolio-4.jsp" class="nav-link">4 columns</a></li>
                            <li class="nav-item"><a href="portfolio-no-space-4.jsp" class="nav-link">4 columns with negative space</a></li>
                            <li class="nav-item"><a href="portfolio-detail.jsp" class="nav-link">Portfolio - detail</a></li>
                            <li class="nav-item"><a href="portfolio-detail-2.jsp" class="nav-link">Portfolio - detail 2</a></li>
                          </ul>
                          <h5>User pages</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="customer-register.jsp" class="nav-link">Register / login</a></li>
                            <li class="nav-item"><a href="customer-orders.jsp" class="nav-link">Orders history</a></li>
                            <li class="nav-item"><a href="customer-order.jsp" class="nav-link">Order history detail</a></li>
                            <li class="nav-item"><a href="customer-wishlist.jsp" class="nav-link">Wishlist</a></li>
                            <li class="nav-item"><a href="customer-account.jsp" class="nav-link">Customer account / change password</a></li>
                          </ul>
                        </div>
                        <div class="col-md-6 col-lg-3">
                          <h5>Shop</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="shop-category.jsp" class="nav-link">Category - sidebar right</a></li>
                            <li class="nav-item"><a href="shop-category-left.jsp" class="nav-link">Category - sidebar left</a></li>
                            <li class="nav-item"><a href="shop-category-full.jsp" class="nav-link">Category - full width</a></li>
                            <li class="nav-item"><a href="shop-detail.jsp" class="nav-link">Product detail</a></li>
                            <li class="nav-item"><a href="product-list.do" class="nav-link">상품목록</a></li>
                            <li class="nav-item"><a href="product-register.jsp" class="nav-link">상품추가</a></li>
                          </ul>
                          <h5>Shop - order process</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="shop-basket.jsp" class="nav-link">Shopping cart</a></li>
                            <li class="nav-item"><a href="shop-checkout1.jsp" class="nav-link">Checkout - step 1</a></li>
                            <li class="nav-item"><a href="shop-checkout2.jsp" class="nav-link">Checkout - step 2</a></li>
                            <li class="nav-item"><a href="shop-checkout3.jsp" class="nav-link">Checkout - step 3</a></li>
                            <li class="nav-item"><a href="shop-checkout4.jsp" class="nav-link">Checkout - step 4</a></li>
                         	<li class="nav-item"><a href="member-list.do" class="nav-link">회원목록보기</a></li>
                         	<li class="nav-item"><a href="customer-update.jsp" class="nav-link">회원정보수정</a></li>
                          </ul>
                        </div>
                        <div class="col-md-6 col-lg-3">
                          <h5>Contact</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="contact.jsp" class="nav-link">Contact</a></li>
                            <li class="nav-item"><a href="contact2.jsp" class="nav-link">Contact - version 2</a></li>
                            <li class="nav-item"><a href="contact3.jsp" class="nav-link">Contact - version 3</a></li>
                          </ul>
                          <h5>Pages</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="text.jsp" class="nav-link">Text page</a></li>
                            <li class="nav-item"><a href="text-left.jsp" class="nav-link">Text page - left sidebar</a></li>
                            <li class="nav-item"><a href="text-full.jsp" class="nav-link">Text page - full width</a></li>
                            <li class="nav-item"><a href="faq.jsp" class="nav-link">FAQ</a></li>
                            <li class="nav-item"><a href="404.jsp" class="nav-link">404 page</a></li>
                          </ul>
                          <h5>Blog</h5>
                          <ul class="list-unstyled mb-3">
                            <li class="nav-item"><a href="blog.jsp" class="nav-link">Blog listing big</a></li>
                            <li class="nav-item"><a href="blog-medium.jsp" class="nav-link">Blog listing medium</a></li>
                            <li class="nav-item"><a href="blog-small.jsp" class="nav-link">Blog listing small</a></li>
                            <li class="nav-item"><a href="blog-post.jsp" class="nav-link">Blog Post</a></li>
                          </ul>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <!-- ========== FULL WIDTH MEGAMENU END ==================-->
                <!-- ========== Contact dropdown ==================-->
                <li class="nav-item dropdown"><a href="javascript: void(0)" data-toggle="dropdown" class="dropdown-toggle">Contact <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li class="dropdown-item"><a href="contact.jsp" class="nav-link">Contact option 1</a></li>
                    <li class="dropdown-item"><a href="contact2.jsp" class="nav-link">Contact option 2</a></li>
                    <li class="dropdown-item"><a href="contact3.jsp" class="nav-link">Contact option 3</a></li>
                  </ul>
                </li>
                <!-- ========== Contact dropdown end ==================-->
              </ul>
            </div>
            <div id="search" class="collapse clearfix">
              <form role="search" class="navbar-form">
                <div class="input-group">
                  <input type="text" placeholder="Search" class="form-control"><span class="input-group-btn">
                    <button type="submit" class="btn btn-template-main"><i class="fa fa-search"></i></button></span>
                </div>
              </form>
            </div>
          </div>
        </div>
      </header>
      <!-- Navbar End-->