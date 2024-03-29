<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>MatDesign</title>
<!-- Favicon -->
<link rel="icon" href="../images/favicon.ico" sizes="32x32">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="../css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel='stylesheet' href='../css/fontawesome.min.css'>
<!-- Animate -->
<link href="../css/animate.css" rel="stylesheet">
<!-- Owl Carousel -->
<link rel="stylesheet" href="../css/owl.carousel.min.css">
<link rel="stylesheet" href="../css/owl.theme.default.min.css">
<!-- light box -->
<link rel="stylesheet" href="../css/lightbox.min.css">
<!-- jquery ui -->
<link rel="stylesheet" href="../css/jquery-ui.min.css">
<!--    <link rel="stylesheet" href="//basehold.it/24">-->

<!-- nice select -->
<link rel="stylesheet" href="../css/nice-select.min.css">
<!-- Main Styles -->
<link rel="stylesheet" href="../scss/main.css">

</head>

<body>
	<!-- main header navbar -->
	<nav class="navbar navbar-expand-lg navbar-light custom-navbar"
		id="mainMenu">
		<div class="container">
			<a class="navbar-brand" href="index.jsp"> <img
				src="../images/MatDesignLogo.png" alt="">
			</a>
			<!--  navbar actions -->
			<div class="main-navbar-action">
				<div id="mainNavbarDropdown">
					<!-- navbar user account dropdown -->
					<div class="dropdown-wrapper" id="usermenu" data-collapse="false">
						<div class="account-wrapper">
							<!-- login form wrapper -->
							<div class="account-wrapper__content" style="text-align:center;">
								<div class="">
									<div class="account-wrapper__heading">
										<span>${memberVO.memberAccount}</span> 
										<span
											class="account-wrapper__heading--link">${memberVO.memberName}
										</span>
									</div>
								</div>
								<div class="account-wrapper__content">
									<div class="form-group custom-form__input">
										<!-- <a class="dropdown-item "
											href="designerPorfile.jsp"> -->
										<form method="post" action="MemberServlet">	
											<button class="btn"><div><i class="icon-user-profile"></i>會員資料</div> </button>
											<input type="hidden" name="memberNo" value="${memberVO.memberNo}"> 
											<input type="hidden" name="action" value="portfolio_GetByNo">
										</form>
									</div>
									<div class="form-group custom-form__input">
										<form method="post" action="../index.html">	
											<button class="btn"><div><i class="icon-user-profile"></i>登出</div> </button>
										</form>
									</div>
								</div>

							</div>
						</div>
					</div>
					<!-- navbar cart dropdown -->
					<div class="" id="cartmenu" data-collapse="false"></div>
				</div>
				<!-- navbar user account icon -->
				<div class="main-navbar-action__btn nav-dropdown">
					<a class="dropdown-link" data-target="usermenu"> <i
						class="icon-user"></i>
					</a>
				</div>
				<!-- navbar cart icon -->
				<div class="main-navbar-action__btn nav-dropdown">
					<a class="dropdown-link" data-target="cartmenu"><!--  <span
						class="cart-badge">2</span> --> <i class="icon-shopping-bag"></i>
					</a>
				</div>
				<!-- navbar actions content -->
			</div>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#mainNavbar" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="mainNavbar">
				<ul class="navbar-nav main-navbar">
					<li class="nav-item main-navbar__item dropdown"><a
						class="nav-link " href="../designer_protfolio/memFindPortfolio.jsp">找作品</a>
					</li>
					<li class="nav-item main-navbar__item dropdown"><a
						class="nav-link " href="${pageContext.request.contextPath}/ShowDesignerPage">找設計師</a></li>
					<li class="nav-item main-navbar__item dropdown"><a
						class="nav-link " href="${pageContext.request.contextPath}/ShowShop">商城</a></li>
					<li class="nav-item main-navbar__item dropdown"><a
						class="nav-link " href="../forum/forumIndex.do">論壇</a></li>
					<!-- <li class="nav-item main-navbar__item dropdown">
                    <a class="nav-link " href="#" data-toggle="dropdown">報導文章</a>
                </li> -->
					<li class="nav-item main-navbar__item"><a class="nav-link"
						href="contact.html">關於我們</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- end main header navbar -->
	<!-- main header slider -->
	<div class="hero-header-slider">
		<div class="owl-carousel ltr" id="heroHeaderSlider">
			<div class="item">
				<div class="hero-header-slider__img slider-img"
					style="background-image: url('https://via.placeholder.com/1920x800')">
					<div class="hero-header-slider__wrapper">
						<div class="slider-inner">
							<div class="container overflow-hidden">
								<div class="inner-content inner-content--dark slider-animated">
									<h5 class="slider-subtitle">New Trend</h5>
									<h1 class="slider-title animated">A new way to design</h1>
									<p class="slider-text animated">Lorem ipsum dolor sit amet,
										consectetur adipisicing elit. Quam, vel.</p>
									<div class="slider-btn">
										<a class="btn animated" href="#">Shop now</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="item">
				<div class="hero-header-slider__img slider-img"
					style="background-image: url('https://via.placeholder.com/1920x800')">
					<div class="hero-header-slider__wrapper">
						<div class="slider-inner">
							<div class="container overflow-hidden">
								<div class="inner-content inner-content--dark slider-animated">
									<h5 class="slider-subtitle">New Trend</h5>
									<h1 class="slider-title animated">Stay on trend in new
										year</h1>
									<p class="slider-text animated">Lorem ipsum dolor sit amet,
										consectetur adipisicing elit. Quam, vel.</p>
									<div class="slider-btn">
										<a class="btn animated" href="#">View more</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- end of main header -->

	<!-- main content -->
	<div class="main-content py-100">
		<div class="container">
			<!-- latest blog post -->
			<div class="row overflow-hidden">
				<div class="col-12">
					<h2 class="main-title wow fadeIn">最新作品</h2>
				</div>
			</div>
			<div class="row wow fadeIn mb-100">
				<div class="col-lg-4 col-md-6">
					<div class="blog-post blog-post--grid">
						<div class="blog-post__img">
							<div class="blog-post__img--overlay"></div>
							<img src="https://via.placeholder.com/500x416" alt=""> <a
								href="#" class="btn blog-post__btn"> Read more </a>
						</div>
						<div class="blog-post__inner">
							<div class="blog-post__inner--title">
								<a href="#"><h4>Designer_protfolio1</h4></a>
							</div>
							<div class="blog-post__inner--details">
								<span class="author">DesignerName,</span> <span class="date">CreateDate</span>
							</div>
							<div class="blog-post__inner--des">
								<p>Description</p>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-6">
					<div class="blog-post blog-post--grid">
						<div class="blog-post__img">
							<div class="blog-post__img--overlay"></div>
							<img src="https://via.placeholder.com/500x416" alt=""> <a
								href="#" class="btn blog-post__btn"> Read more </a>
						</div>
						<div class="blog-post__inner">
							<div class="blog-post__inner--title">
								<a href="#"><h4>Designer_protfolio2</h4></a>
							</div>
							<div class="blog-post__inner--details">
								<span class="author">DesignerName,</span> <span class="date">CreateDate</span>
							</div>
							<div class="blog-post__inner--des">
								<p>Description</p>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-6 mx-auto">
					<div class="blog-post blog-post--grid">
						<div class="blog-post__img">
							<div class="blog-post__img--overlay"></div>
							<img src="https://via.placeholder.com/500x416" alt=""> <a
								href="#" class="btn blog-post__btn"> Read more </a>
						</div>
						<div class="blog-post__inner">
							<div class="blog-post__inner--title">
								<a href="#"><h4>Designer_protfolio3</h4></a>
							</div>
							<div class="blog-post__inner--details">
								<span class="author">DesignerName,</span> <span class="date">CreateDate</span>
							</div>
							<div class="blog-post__inner--des">
								<p>Description</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end latest blog post -->
		</div>
		<!-- gallery -->
		<div class="res-gallery res-gallery--bg mb-100">
			<div class="container">
				<div class="row">
					<!-- gallery tabs -->
					<div class="col-lg-3 res-gallery__tabs">
						<h3 class="gallery-title">熱門設計師</h3>
						<div class="nav nav-pills" id="v-pills-tab" role="tablist"
							aria-orientation="vertical">
							<a class="nav-link active" id="v-pills-cat01-tab"
								data-toggle="pill" href="#v-pills-cat01" role="tab"
								aria-controls="v-pills-cat01" aria-selected="true">All</a>
						</div>
					</div>
					<!-- gallery content -->
					<div class="col-lg-9">
						<div class="tab-content res-gallery__content"
							id="v-pills-tabContent">
							<div class="tab-pane fade show active" id="v-pills-cat01"
								role="tabpanel" aria-labelledby="v-pills-cat01-tab">
								<div class="row">
									<div class="col-md-4 col-6 gallery-column">
										<div class="image-effect">
											<img src="https://via.placeholder.com/400x367" alt="">
											<div class="image-effect__content">
												<a href="#"><h3 class="link">View more</h3></a>
											</div>
										</div>
									</div>
									<div class="col-md-4 col-6 gallery-column">
										<div class="image-effect">
											<img src="https://via.placeholder.com/400x367" alt="">
											<div class="image-effect__content">
												<a href="#"><h3 class="link">View more</h3></a>
											</div>
										</div>
									</div>
									<div class="col-md-4 col-6 gallery-column">
										<div class="image-effect">
											<img src="https://via.placeholder.com/400x367" alt="">
											<div class="image-effect__content">
												<a href="#"><h3 class="link">View more</h3></a>
											</div>
										</div>
									</div>
									<div class="col-md-4 col-6 gallery-column">
										<div class="image-effect">
											<img src="https://via.placeholder.com/400x367" alt="">
											<div class="image-effect__content">
												<a href="#"><h3 class="link">View more</h3></a>
											</div>
										</div>
									</div>
									<div class="col-md-4 col-6 gallery-column">
										<div class="image-effect">
											<img src="https://via.placeholder.com/400x367" alt="">
											<div class="image-effect__content">
												<a href="#"><h3 class="link">View more</h3></a>
											</div>
										</div>
									</div>
									<div class="col-md-4 col-6 gallery-column">
										<div class="image-effect">
											<img src="https://via.placeholder.com/400x367" alt="">
											<div class="image-effect__content">
												<a href="#"><h3 class="link">View more</h3></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end gallery -->
		<div class="container">
			<!-- items slider light -->
			<div class="row mb-100">
				<h2 class="main-title">商城</h2>
				<div class="owl-carousel card-item-slider ltr" id="itemSliderLight">
					<div class="item">
						<div class="card-item card-item--light">
							<div class="card-item__bg">
								<a href="#"><img src="https://via.placeholder.com/313"
									alt=""></a>
							</div>
							<div class="card-item__body">
								<div class="card-item__body--price">
									<strong>$299.00</strong>
								</div>
								<div class="card-item__body--title">
									<a href="#"><h4>Modern fabric armchair</h4></a>
								</div>
								<div class="product-rate">
									<div class="product-rate__star">
										<span class="rate-3"></span>
									</div>
									<div class="rate-number">(3)</div>
								</div>
							</div>
							<div class="card-item__overlay">
								<a href="#" class="btn">Add to cart</a>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="card-item card-item--light">
							<div class="card-item__bg">
								<a href="#"><img src="https://via.placeholder.com/313"
									alt=""></a>
							</div>
							<div class="card-item__body">
								<div class="card-item__body--price">
									<strong>$390.99</strong>
								</div>
								<div class="card-item__body--title">
									<a href="#"><h4>Modern fabric armchair</h4></a>
								</div>
								<div class="product-rate">
									<div class="product-rate__star">
										<span class="rate-3"></span>
									</div>
									<div class="rate-number">(3)</div>
								</div>
							</div>
							<div class="card-item__overlay">
								<a href="#" class="btn">Add to cart</a>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="card-item card-item--light">
							<div class="card-item__bg">
								<a href="#"><img src="https://via.placeholder.com/313"
									alt=""> <!--                                <span class="badge card-item__bg&#45;&#45;badge">New Arrival</span>-->
								</a>
							</div>
							<div class="card-item__body">
								<div class="card-item__body--price">
									<strong>$90.00</strong>
								</div>
								<div class="card-item__body--title">
									<a href="#"><h4>Modern fabric armchair</h4></a>
								</div>
								<div class="product-rate">
									<div class="product-rate__star">
										<span class="rate-3"></span>
									</div>
									<div class="rate-number">(3)</div>
								</div>
							</div>
							<div class="card-item__overlay">
								<a href="#" class="btn">Add to cart</a>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="card-item card-item--light">
							<div class="card-item__bg">
								<a href="#"><img src="https://via.placeholder.com/313"
									alt=""> </a>
							</div>
							<div class="card-item__body">
								<div class="card-item__body--price">
									<strong>$320.00</strong>
								</div>
								<div class="card-item__body--title">
									<a href="#"><h4>Modern fabric armchair</h4></a>
								</div>
								<div class="product-rate">
									<div class="product-rate__star">
										<span class="rate-3"></span>
									</div>
									<div class="rate-number">(3)</div>
								</div>
							</div>
							<div class="card-item__overlay">
								<a href="#" class="btn">Add to cart</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end items slider light -->
		</div>
		<!-- end items slider light blue -->



	</div>
	<!-- end main content -->
	<!-- footer -->
	<footer class="footer">
		<div class="container">
			<div class="footer__top-row">
				<div class="row">
					<div class="col-lg-4 col-md-6 footer__content">
						<div class="footer-logo">
							<img src="../images/MatDesignLogo.png" alt="">
						</div>
						<p></p>

					</div>
					<div class="col-lg-2 col-md-6 footer__content">
						<h5 class="footer-heading">關於我們</h5>
						<ul class="footer-list">
							<li class="footer-list__item"><a href="index.html">關於我們</a></li>
						</ul>
					</div>
					<div class="col-lg-3 col-md-6 footer__content">
						<h5 class="footer-heading">網站地圖</h5>
						<ul class="footer-list">
							<li class="footer-list__item"><a href="#">找作品</a></li>
							<li class="footer-list__item"><a href="#">找設計師</a></li>
							<li class="footer-list__item"><a href="#">商城</a></li>
							<li class="footer-list__item"><a href="#">論壇</a></li>
						</ul>
					</div>
					<div class="col-lg-3 col-md-6 footer__content">
						<h5 class="footer-heading">Keep in touch</h5>
						<ul class="footer-list footer-list-info">
							<li class="footer-list__item"><span><i
									class="fas fa-envelope"></i></span> <span>MatDesign@gmail.com</span></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="copyright">
				<p>&#169; copyright 2022. Designed by MatDesign</p>
			</div>
		</div>
	</footer>
	<!-- end footer -->
	<!-- scroll up btn -->
	<a class="back-to-top-btn" id="back-to-top"></a>
	<!-- end scroll up btn -->

	<!-- All Jquery -->
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/popper.min.js"></script>
	<script type="text/javascript" src="../js/bootstrap.min.js"></script>
	<!-- owl carousel js -->
	<script type="text/javascript" src="../js/owl.carousel.min.js"></script>
	<!-- Jquery ui -->
	<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	<!-- light box js -->
	<script type="text/javascript" src="../js/lightbox.min.js"></script>
	<!-- typeahead js -->
	<script type="text/javascript" src="../js/typeahead.jquery.min.js"></script>
	<!-- master zoom image js -->
	<script type="text/javascript" src="../js/jquery.zoom.min.js"></script>
	<!-- countdown js -->
	<script type="text/javascript" src="../js/countdown.jquery.min.js"></script>
	<!-- nice select js -->
	<script type="text/javascript" src="../js/nice-select.min.js"></script>
	<!-- wow js -->
	<script type="text/javascript" src="../js/wow.min.js"></script>
	<!-- custom js -->
	<script type="text/javascript" src="../js/custom.js"></script>

</body>
</html>