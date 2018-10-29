<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>趣友欢乐游戏</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description"
	content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
<meta name="author" content="Muhammad Usman">
<link rel="Shortcut Icon" href=img/quyou.png>
<!-- The styles -->
<link id="bs-css" href="css/bootstrap-cerulean.min.css" rel="stylesheet">

<link href="css/charisma-app.css" rel="stylesheet" type="text/css">
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link href="css/mobile/font-awesome.min.css?t=20170928"
	rel="stylesheet" type="text/css" />

</head>

<body>
	<!-- topbar starts -->
	<div class="navbar navbar-default" role="navigation">

		<div class="navbar-inner">
			<button type="button" class="navbar-toggle pull-left animated flip">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"> <span
				style="font-size: 20px;font-weight: bold">趣友欢乐游戏</span></a>
			<!-- user dropdown starts -->
			<div class="btn-group pull-right">
				<button class="btn btn-default dropdown-toggle"
					onclick="location.href='exit.do'">
					<i class="glyphicon glyphicon-user"></i><span
						class="hidden-sm hidden-xs"> 退出</span>
				</button>

			</div>
				<div class="pull-right"
					style="float: left;padding: 15px 15px;
	             font-size: 18px;line-height: 20px;font-weight:bold;height: 50px;color:#FFF">
					<c:if test="${sessionScope.userType==1||sessionScope.userType==2}">新增人数：${sessionScope.addPersonNum}</c:if>
					<c:if test="${sessionScope.userType==2&&sessionScope.isCloseThreeLevelInstitution!=1}"> 代金券：${sessionScope.bonus}.00 <i class="icon-money"></i></c:if></div>
			<!-- user dropdown ends -->






		</div>
	</div>
	<!-- topbar ends -->
	<div class="ch-container">
		<div class="row">

			<!-- left menu starts -->
			<div class="col-sm-2 col-lg-2">
				<div class="sidebar-nav">
					<div class="nav-canvas">
						<div class="nav-sm nav nav-stacked"></div>
						<ul class="nav nav-pills nav-stacked main-menu">
							<li class="nav-header">Main</li>
							<li><a class="ajax-link" href="index.do"><i
									class="glyphicon glyphicon-home"></i><span> 我的信息</span></a></li>
							<c:if test="${sessionScope.userType==0}">
								<li><a class="ajax-link" href="mainproxy.do"><i
										class="glyphicon glyphicon-eye-open"></i><span>总代管理</span></a></li>
							</c:if>
							<c:if
								test="${sessionScope.userType==1||sessionScope.userType==0}">
								<li><a class="ajax-link" href="proxy.do"><i
										class="glyphicon glyphicon-edit"></i><span>代理管理</span></a></li>
							</c:if>
							<li><a class="ajax-link" href="cardRecord.do"><i
									class="glyphicon glyphicon-list-alt"></i><span>售卡记录</span></a></li>
							<li><a class="ajax-link" href="playerMag.do"><i
									class="glyphicon glyphicon-font"></i><span>玩家管理</span></a></li>
							<!-- <li><a class="ajax-link" href="#"><i
									class="glyphicon glyphicon-picture"></i><span>通知管理</span></a></li> -->
							<c:if
								test="${sessionScope.isCloseThreeLevelInstitution!=1&&sessionScope.userType==2}">
							<li><a class="ajax-link" href="bonusShop.do"><i
									class="glyphicon glyphicon-cog"></i><span>积分商城</span></a></li>
							</c:if>
							<li><a class="ajax-link" href="editPassword.jsp"><i
									class="glyphicon glyphicon-align-justify"></i><span>修改密码</span></a></li>
							<!-- <li class="accordion"><a href="#"><i
									class="glyphicon glyphicon-plus"></i><span>修改IP</span></a>
								<ul class="nav nav-pills nav-stacked">
									<li><a href="#">Child Menu 1</a></li>
									<li><a href="#">Child Menu 2</a></li>
								</ul></li> -->
						</ul>
					</div>
				</div>
			</div>
			<!--/span-->
			<!-- left menu ends -->

			<noscript>
				<div class="alert alert-block col-md-12">
					<h4 class="alert-heading">Warning!</h4>

					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="col-lg-10 col-sm-10">
				<!-- content starts -->
				<div class="row">
					<div class="box col-md-12">
						<div class="box-inner">
							<!-- box-header well Start -->
							<div class="box-header well">
								<h2 style="font-size: 20px">
									<i class="glyphicon glyphicon-info-sign"></i>会员管理
								</h2>

								<!-- <div class="box-icon">
									<a href="#" class="btn btn-setting btn-round btn-default"><i
										class="glyphicon glyphicon-cog"></i></a> <a href="#"
										class="btn btn-minimize btn-round btn-default"><i
										class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
										class="btn btn-close btn-round btn-default"><i
										class="glyphicon glyphicon-remove"></i></a>
								</div> -->
							</div>
							<!-- box-content row Start -->
							<div class="box-content row">
								<div class="col-lg-7 col-md-12">
<!-- form action="recommend.do" class="testform" method="post"
										"onsubmit="return sumbit_sure() -->
									<div class="testform">
										<input type="hidden" name="type" value="1"></input>
										<table class="table table-bordered table-striped responsive"
											style="position:absolute; top:30px; left:50px;">
											<tr>
												<td>名称：</td>
												<td><input type="text" id="username" value="" name="username"
													style="border:1px solid #b4b4b4;width:180px" /></td>
											</tr>
											<tr>
												<td>地址：</td>
												<td><input type="text" id="address" value="" name="address"
													style="border:1px solid #b4b4b4;width:180px" /></td>
											</tr>
											<tr>
												<td>联系人：</td>
												<td><input type="text" id="contactName" value="" name="contactName"
													style="border:1px solid #b4b4b4;width:180px" /></td>
											</tr>
											<tr>
												<td>联系电话：</td>
												<td><input type="text" id="contactPhone" value="" name="contactPhone"
													id="contactPhone"
													style="border:1px solid #b4b4b4;width:180px;"
													onblur="OnInput (event)" /></td>
											</tr>
													<tr>
												<td>游戏id：</td>
												<td><input type="number" id="gameUid" value="0"
													name="gameUid"
													style="border:1px solid #b4b4b4;width:180px" /></td>
											</tr>
											<tr>
												<td>推荐人：</td>
												<td><input type="number" id="recommendPerson" value="0"
													name="recommendPerson"
													style="border:1px solid #b4b4b4;width:180px" /></td>
											</tr>
											<tr>
												<td>限时房卡数量：</td>
												<td><input type="text" value="" name="cardCount"
													style="border:1px solid #b4b4b4;width:180px"
													disabled="true" /></td>
											</tr>
											<tr>
												<td>永久房卡数量：</td>
												<td><input type="text" value="" name="cardCount"
													style="border:1px solid #b4b4b4;width:180px"
													disabled="true" /></td>
											</tr>
											<tr>
												<td colspan="2" style="text-align:center"><br /> <input
													type="submit" value="保存" onclick="save()"/></td>
											</tr>

										</table>
									</div>
									<!--暂且去掉form  -->
								</div>
							</div>
							<!-- box-content row End -->
						</div>
					</div>
				</div>
				<!-- content ends -->
			</div>
		</div>
		<!--/fluid-row-->

		<!-- Ad, you can remove it -->

		<!-- Ad ends -->

		<hr>



		<footer class="row">
		<p class="col-md-9 col-sm-9 col-xs-12 copyright">
			&copy; <a href="#" target="_blank">QuYou GoPlayUS Beijing
				Technology Co., Ltd </a> 2007 ~ 2018
		</p>


		</footer>

	</div>

	</div>
	<!--/.fluid-container-->
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<script src="js/mobile/app.js" type="text/javascript"></script>
	<!-- external javascript -->
	<script src="js/bootstrap.min.js"></script>
	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>
	<!-- data table plugin -->
	<!-- notification plugin -->
	<script src="js/jquery.noty.js"></script>
	<script language="javascript">  
    function sumbit_sure(){   
    var p0 = document.getElementById("contactPhone").value; //获取密码框的值
    if(checkTel(p0)){
    if (window.confirm("确定要提交?")) {
     return true;
    }
    }
    return false;
    }  
    function checkTel(phone){
     var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
      if(!myreg.test(phone)) 
       { 
           alert('请输入有效的手机号码！'); 
           return false; 
       } 
      return true;
   } 
   	function check(inputId) {
			var reg = /^\d+$/; // 注意：故意限制了 0321 这种格式，如不需要，直接/^[1-9]\d*$|^0$/
			if (reg.test(inputId) == true) {
				return true;
			} else {
				return false;
			}
		}
    // Firefox, Google Chrome, Opera, Safari, Internet Explorer from version 9
        function OnInput (event) {
        var phone=event.target.value;
        checkTel(phone)
        }
        		function save() {
			//doTimeOut();
			//var oriPass = $('#ori-pass-field').val();
			var username = $('#username').val();
			var address = $('#address').val();
			var contactName = $('#contactName').val();
			var contactPhone = $('#contactPhone').val();
			var recommendPerson = $('#recommendPerson').val();
			var gameUid = $('#gameUid').val();
			if (username == "" || address == "" || contactName == "" || contactPhone == "" || recommendPerson == "") {
				alert('请填入完整的信息');
			    return 
			}
			if (!checkTel(contactPhone)) {
				alert("请输入正确的电话号码");
				return;
			}
			if (!check(recommendPerson)) {
				alert("推荐人为代理id,请输入正确的代理id");
				return;
			}
			var url = "/addproxyMb.do";
			doPostWithValidate(url, {
				username : username,
				address : address,
				contactName : contactName,
				contactPhone : contactPhone,
				recommendPerson : recommendPerson,
				gameUid : gameUid
			}, function(response) {
				if (response.status == 0) {
					alert('添加成功');gotoHtml('/proxy.do');
				} else if (response.status == -10003) {
					alert('游戏Id不存在');
				} else if (response.status == -10004) {
					alert('此玩家id已经绑定代理号');
				} else {
					alert('添加失败');
				}
			});
		}
    </script>


</body>
</html>
