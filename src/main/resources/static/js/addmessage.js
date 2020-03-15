$(function() {
	var startnumber;
	var stopnumber;
	var zIndex=3000;
	var Borrowingnumber; //借贷额度
	var educationvalue; //学历
	var whethervalue; //有无社保
	var anywherevalue; //有无保单
	var Assetsvalue; //资产情况
	var marriagevalue; //婚姻情况
	var linusericssonvalue; //现居地
	var Professorvalue; //有无本地公积金
	var Northwesternvalue; //有无信用卡
	var Professionalvalue; //职业
	var unitsCurrencyvalue; //工作单位
	var AddressLocationvalue; //单位地址
	var monthlyvalue; //月收入
	var paydayvalue; //发薪日
	var ymjusername;//姓名
	var ymjage;//年龄
	var ymjphone;//手机号
	var ymjQQ;//QQ号
	var ymjwechat;//微信号
	var ymjSesame;//芝麻分
	var ymjCard;//身份证号
	var ymjRegion;//身份证号qd_region

	//身份证正则
	var Cardzz=/^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/
 var phonezz=/^1[3456789]\d{9}$/
	
	//theFinalStep  最后一步
		$(".theFinalStepSbmit").click(function() {
				 ymjusername=$("#ymjusername").val();
				 if (ymjusername!="") {
					 ymjage = $("#ymjage").val();
					 if (ymjage != "") {
						 ymjphone = $("#ymjphone").val();
						 if (phonezz.test(ymjphone)) {
							 ymjQQ = $("#ymjQQ").val();
							 if (ymjQQ != "") {
								 ymjwechat = $("#ymjwechat").val();
								 if (ymjwechat != "") {
									 ymjSesame = $("#ymjSesame").val();
									 if (ymjSesame != "") {
										 ymjCard = $("#ymjCard").val();
										 ymjRegion = linusericssonvalue.trim().split(" ")[0];
										 if (Cardzz.test(ymjCard)) {
											 $.ajax({
												 url: "/insertBasicmanager",
												 data: {
													 "qdLoanpay": Borrowingnumber,
													 "qdOften": educationvalue,
													 "qdSocial": whethervalue,
													 "qdPlicy": anywherevalue,
													 "qdAssets": Assetsvalue,
													 "qdMarriage": marriagevalue,
													 "qdDress": linusericssonvalue,
													 "qdFund": Professorvalue,
													 "qdCredit": Northwesternvalue,
													 "qdJobs": Professionalvalue,
													 "qdUnits": unitsCurrencyvalue,
													 "qdUnitsDress": AddressLocationvalue,
													 "qdIncome": monthlyvalue,
													 "qdSalary": paydayvalue,
													 "qdAge": ymjage,
													 "qdPhone": ymjphone,
													 "qdQq": ymjQQ,
													 "qdWechat": ymjwechat,
													 "qdSesame": ymjSesame,
													 "qdCard": ymjCard,
													 "qdRegion": ymjRegion,
													 "qdUsername": ymjusername
												 },
												 type: "post",
												 dataType: "json",
												 success: function (result) {
													 if (result.code == 0) {
														 $(location).prop('href', '/login')
													 }
													 alert(result.message)

												 },
												 error: function (result) {
													 console.log("error----");
												 }
											 })
										 }

										 else{alert("请正确填写身份证号")}
									 }else{
									 alert("请正确填写芝麻分") }
								 }else{
								 alert("请正确填写微信号") }
							 }else{
							 alert("请正确填写QQ号") }
						 }else{
						 alert("请正确填写手机号") }
					 }else{
					 alert("请正确填写年龄")
				 }
				 }else{
			alert("请正确填写用户名")
				 }
		})
		$(".anywhereInput>.ButtonThe").click(function() {
				 $(".anywhere").css({"display": "block","z-index":zIndex})
		})
	//anywhere  有无保单
		$(".anywhereselect li").click(function() {
				 anywherevalue=$(this).attr("value")
				  $(".anywhereInput>.prompting").html("有无保单"+anywherevalue);
				 $(".anywhereInput").css({"display": "block"})
				 $(".anywhere").css({"display": "none"})
		})
		$(".anywhereInput>.ButtonThe").click(function() {
				 zIndex=zIndex+100
				 $(".anywhere").css({"display": "block","z-index":zIndex})
		})
	//payday  发薪日
		$(".paydayxiayibu").click(function() {
				 paydayvalue=$("#paydayMessage").val();
			if (paydayvalue!="") {
				  $(".paydayInput>.prompting").html("现发薪日居地："+paydayvalue);
				 $(".paydayInput").css({"display": "block"})
				 $(".payday").css({"display": "none"})
		}else {
				alert("请正确填写发薪日")
			}
		})
		$(".paydayInput>.ButtonThe").click(function() {
			 zIndex=zIndex+100
				 $(".payday").css({"display": "block","z-index":zIndex})
		})
	//monthly  月收入
	$(".monthlyxiayibu").click(function() {
			 monthlyvalue=$("#monthlyMessage").val()
		if (monthlyvalue!="") {
			  $(".monthlyInput>.prompting").html("月收入："+monthlyvalue);
			 $(".monthlyInput").css({"display": "block"})
			 $(".monthly").css({"display": "none"})
		}else {
			alert("请正确填写月收入")
		}
	})
	$(".monthlyInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".monthly").css({"display": "block","z-index":zIndex})
	})
	//AddressLocation  单位地址
	$(".AddressLocationxiayibu").click(function() {
			 AddressLocationvalue=$("#AddressLocationMessage").val()
		if (AddressLocationvalue!="") {
			  $(".AddressLocationInput>.prompting").html("单位地址："+AddressLocationvalue);
			 $(".AddressLocationInput").css({"display": "block"})
			 $(".AddressLocation").css({"display": "none"})
		}else {
			alert("请正确填写单位地址")
		}
	})
	$(".AddressLocationInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".AddressLocation").css({"display": "block","z-index":zIndex})
	})
	//unitsCurrency  工作单位
	$(".unitsCurrencyxiayibu").click(function() {
			 unitsCurrencyvalue=$("#unitsCurrencyMessage").val()
		if (unitsCurrencyvalue!="") {
			$(".unitsCurrencyInput>.prompting").html("工作单位：" + unitsCurrencyvalue);
			$(".unitsCurrencyInput").css({"display": "block"})
			$(".unitsCurrency").css({"display": "none"})
		}else {
			alert("请正确填写工作单位")
		}
	})
	$(".linusericssonInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".linusericsson").css({"display": "block","z-index":zIndex})
	})
	//Professional  职业
	$(".Professionalselect li").click(function() {
			 Professionalvalue=$(this).attr("value")
			  $(".ProfessionalInput>.prompting").html("职业："+Professionalvalue);
			 $(".ProfessionalInput").css({"display": "block"})
			 $(".Professional").css({"display": "none"})
	})
	$(".ProfessionalInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".Professional").css({"display": "block","z-index":zIndex})
	})
	//Northwestern  有无信用卡
	$(".Northwesternselect li").click(function() {
			 Northwesternvalue=$(this).attr("value")
			  $(".NorthwesternInput>.prompting").html("有无信用卡："+Northwesternvalue);
			 $(".NorthwesternInput").css({"display": "block"})
			 $(".Northwestern").css({"display": "none"})
	})
	$(".NorthwesternInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".Northwestern").css({"display": "block","z-index":zIndex})
	})
	
	//Professor  有无本地公积金
	$(".Professorselect li").click(function() {
			 Professorvalue=$(this).attr("value")
			  $(".ProfessorInput>.prompting").html("有无本地公积金："+Professorvalue);
			 $(".ProfessorInput").css({"display": "block"})
			 $(".Professor").css({"display": "none"})
	})
	$(".ProfessorInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".Professor").css({"display": "block","z-index":zIndex})
	})
	//linusericsson  现居地
	$(".linusericssonselectxiayibu").click(function() {
			 linusericssonvalue=$("#linusericssonmessage").val();
			 if (linusericssonvalue!=""){
				 $(".linusericssonInput>.prompting").html("现居地："+linusericssonvalue);
				 $(".linusericssonInput").css({"display": "block"})
				 $(".linusericsson").css({"display": "none"})
			 }else {
				 alert("请正确填写现居地")
			 }
	})
	$(".linusericssonInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".linusericsson").css({"display": "block","z-index":zIndex})
	})
	
	//marriage  婚姻情况
	$(".marriageselect li").click(function() {
			 marriagevalue=$(this).attr("value")
			  $(".marriageInput>.prompting").html("婚姻情况："+marriagevalue);
			 $(".marriageInput").css({"display": "block"})
			 $(".marriage").css({"display": "none"})
	})
	$(".marriageInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".marriage").css({"display": "block","z-index":zIndex})
	})
	
	//Borrowing  借贷金额
	$(".startinputbox li").click(function() {
		$(".startinputbox li").css({
			"background-color": "#007AFF"
		});
		$(this).css({
			"background-color": "#163960"
		});
		startnumber=$(this).attr("value")
	})
	$(".stopinputbox li").click(function() {
		$(".stopinputbox li").css({
			"background-color": "#007AFF"
		});
		$(this).css({
			"background-color": "#163960"
		});
		stopnumber=$(this).attr("value")
	})
	$(".Borrowingxiayibu").click(function() {
		if(startnumber<stopnumber){
			Borrowingnumber=startnumber+"-"+stopnumber+"万"
		    $(".BorrowingInput>.prompting").html("借贷额度："+startnumber+"-"+stopnumber+"万");
			 $(".BorrowingInput").css({"display": "block"})
			 $(".Borrowing").css({"display": "none"})
		}else{
		alert("借贷 开始额度不能小于结束额度")
		}
	})
	$(".BorrowingInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".Borrowing").css({"display": "block","z-index":zIndex})
	})
    //education  您的学历
	$(".educationselect li").click(function() {
			 educationvalue=$(this).attr("value")
			  $(".educationInput>.prompting").html("您的学历："+educationvalue);
			 $(".educationInput").css({"display": "block"})
			 $(".education").css({"display": "none"})
	})
	$(".educationInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".education").css({"display": "block","z-index":zIndex})
	})
	//whether  有无社保
	$(".whetherselect li").click(function() {
			 whethervalue=$(this).attr("value")
			  $(".whetherInput>.prompting").html("您的社保："+whethervalue);
			 $(".whetherInput").css({"display": "block"})
			 $(".whether").css({"display": "none"})
	})
	$(".whetherInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".whether").css({"display": "block","z-index":zIndex})
	})
	//Assets  资产情况
	$(".Assetsselect li").click(function() {
			 Assetsvalue=$(this).attr("value")
			  $(".AssetsInput>.prompting").html("资产情况："+Assetsvalue);
			 $(".AssetsInput").css({"display": "block"})
			 $(".Assets").css({"display": "none"})
	})
	$(".AssetsInput>.ButtonThe").click(function() {
		 zIndex=zIndex+100
			 $(".Assets").css({"display": "block","z-index":zIndex})
	})
})
