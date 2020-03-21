

var minSesame=550;//最小芝麻分
var maxSesame=950;//最大芝麻分

var minAge=18;//最小年龄
var maxAge=70;//最大年龄

var minLimit=1000 ;//最小额度
var maxLimit=50000 ;//最大额度

var guarantee=0;//保单
var creditCard=0;//信用卡
var educationBackground=0;//学历
var multipleConditions=0;//社保公积金房车

var cityName="";//地址

$('.subscriptionSubmit').on('click', function () {
    $.ajax({
        url: "/PersonalSubscriptions",
        type: "get",
        data:{"minAge":minAge,"maxAge":maxAge,
            "minLimit":minLimit,"maxLimit":maxLimit,
            "guarantee":guarantee,"creditCard":creditCard,
            "educationBackground":educationBackground,
            "multipleConditions":multipleConditions,
            "cityName":cityName,
        },
        dataType: "json",
        success: function (result) {
            $(location).prop('href', '/grondstoffenlijst')

        },
        error: function (result) {
            console.log("error----");
        }
    })
});
//月供滑动
// $(".range_1").ionRangeSlider({
//     min: 550, //最小值
//     max: 950, //最大值
//     from: 0, //默认从哪个值开始选
//     to: 6000, //默认选到哪个值
//     type: 'double', //设置类型
//     step: 1,
//     prefix: "", //设置数值前缀
//     postfix: "分", //设置数值后缀
//     prettify: true,
//     hasGrid: true,
//     onChange: function (data) {//数据变化时触发
//         console.log(data)
//         moveSliderRange1(data.from, data.to);
//     },
// });
// var sliderRange2 = $(".range_1").data("ionRangeSlider");
//
// function moveSliderRange1(leftNum, rightNum) {
//    minSesame=leftNum;
//    maxSesame=rightNum;
//         $('.bxyc1').text(leftNum + '-' + rightNum + '分');
//         $('.bxyc1').attr("data-leftNum", leftNum);
//         $('.bxyc1').attr("data-rightNum", rightNum);
//
// }


//月供滑动
$(".range_2").ionRangeSlider({
    min: 22, //最小值 
    max: 45, //最大值 
    from: 22, //默认从哪个值开始选
    to: 45, //默认选到哪个值
    type: 'double', //设置类型
    step: 1,
    prefix: "", //设置数值前缀
    postfix: "岁", //设置数值后缀
    prettify: true,
    hasGrid: true,
    onChange: function (data) {//数据变化时触发
        console.log(data)
        moveSliderRange2(data.from, data.to);
    },
});
var sliderRange2 = $(".range_2").data("ionRangeSlider");

function moveSliderRange2(leftNum, rightNum) {
   minAge=leftNum;
   maxAge=rightNum;
        $('.bxyc2').text(leftNum + '-' + rightNum + '岁');
        $('.bxyc2').attr("data-leftNum", leftNum);
        $('.bxyc2').attr("data-rightNum", rightNum);

}



//月供滑动
$(".range_3").ionRangeSlider({
    min: 1000, //最小值 
    max: 50000, //最大值 
    from: 1000, //默认从哪个值开始选
    to: 50000, //默认选到哪个值
    type: 'double', //设置类型
    step: 1,
    prefix: "", //设置数值前缀
    postfix: "元", //设置数值后缀
    prettify: true,
    hasGrid: true,
    onChange: function (data) {//数据变化时触发
        console.log(data)
        moveSliderRange3(data.from, data.to);
    },
});
var sliderRange2 = $(".range_3").data("ionRangeSlider");

function moveSliderRange3(leftNum, rightNum) {
   minLimit=leftNum;
   maxLimit=rightNum;
        $('.bxyc3').text(leftNum + '-' + rightNum + '元');
        $('.bxyc3').attr("data-leftNum", leftNum);
        $('.bxyc3').attr("data-rightNum", rightNum);
}
$('.oneNextStep').on('click', function () {
     $(".onesubscription").css({"display":"none"})
	 $(".twosubscription").css({"display":"block"})
});
$('.sl-item-list .insure').on('click', function () {
      guarantee=$(this).attr("value")
	  $(".sl-item-list .insure").css({"background":"#F0F0F0","color":"#999"})
	 $(this).css({"background":"#ff6701","color":"#fff"})
	
});
$('.sl-item-list .card').on('click', function () {
	
    creditCard=$(this).attr("value")
	 $(".sl-item-list .card").css({"background":"#F0F0F0","color":"#999"})
	$(this).css({"background":"#ff6701","color":"#fff"})
		
});
$('.sl-item-list .edu').on('click', function () {
	
     educationBackground= $(this).attr("value")
	  $(".sl-item-list .edu").css({"background":"#F0F0F0","color":"#999"})
	$(this).css({"background":"#ff6701","color":"#fff"})
		
});
$('.sl-item-list .car').on('click', function () {
	
      multipleConditions=$(this).attr("value")
	   $(".sl-item-list .car").css({"background":"#F0F0F0","color":"#999"})
	 $(this).css({"background":"#ff6701","color":"#fff"})
	 	
});

$('.twoNextStep').on('click', function () {
     $(".twosubscription").css({"display":"none"})
	 $(".threesubscription").css({"display":"block"})
});

window.onload = function(){

    this.cityInitialMessage();

};

function cityInitialMessage() {
    var cityhtml="<li onclick=\"citySelect('全国')\">全国</li>";
    console.log("进入----");
    $.ajax({
        url: "/cityInfo",
        type: "get",
        dataType: "json",
        success: function (result) {
            for (i = 0; i < result.length; i++) {
                cityhtml += "<li onclick=\"citySelect('"+result[i].sfCity+"')\">"+result[i].sfCity+"</li>"
            }
            $("#cityminboxtopssss").html(cityhtml);
        },
        error: function (result) {
            console.log("error----");
        }
    })
}
function citySelect(value) {
    if (value!='全国') {
        cityName=value;
        var cname="城市选择 - "+value
        $("#cityxhuanzhe").html(cname)

    }else{
        cityName='';
        var cname="城市选择 - 全国"
        $("#cityxhuanzhe").html(cname)
    }


}