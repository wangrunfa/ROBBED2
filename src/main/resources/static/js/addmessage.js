var startnumber;
var stopnumber;
var zIndex = 3000;
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
var ymjSex;//性别

var inputmaxboxheight = 0;

var qsstatus=0;
//身份证正则
var Cardzz = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/
var phonezz = /^1[3456789]\d{9}$/

window.onload=function(){
    findqdtjstatus();
}

function findqdtjstatus() {
    var sourceIdsss= getUrlParam('sourceId')
if (sourceIdsss!=null && sourceIdsss!='' && sourceIdsss>0){


    $.ajax({
        url: "/findqdtjstatus",
        data: {
             "qdSource":sourceIdsss,
        },
        type: "post",
        dataType: "json",
        success: function (result) {
            console.warn(result)
            if (result.code == 0) {
                qsstatus=0
            }
            if (result.code == 1) {
                qsstatus=1
                alert(result.message)
                // $(location).prop('href',)

            }
        },
        error: function (result) {
            $(location).prop('href', "/login")
            console.warn("error----");
        }
    })
}else{
    qsstatus=1
    alert("温馨提示：您没有使用渠道!")
}


}





function submitMessages() {
    console.warn("进入 submitMessages:")

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
            "qdUsername": ymjusername,
            "qdSex": ymjSex,
            "qdSource": getUrlParam('sourceId')
        },
        type: "post",
        dataType: "json",
        success: function (result) {
            console.warn(result)
            if (result.code == 0) {
                alert("温馨提示：信息已提交完成，稍后将有工作人员联系您")
                $(location).prop('href', result.data)
            }else if (result.code == 1) {
                alert(result.message)
            }else if (result.code == 2) {
                alert("温馨提示：信息已提交完成，客服正在审核，请不要重复提交")
                $(location).prop('href', result.message)

            }
        },
        error: function (result) {
            $(location).prop('href', "/login")
            console.warn("error----");
        }
    })

}

function faxinInputFUZHI(number) {
    $("#paydayMessage").val("每月-" + number + "-号")
    paydayvalue = number;
    $(".faxinTimeOneBox").fadeOut()
}

function guanbifaxin() {
    $(".faxinTimeOneBox").fadeOut()
}

function faxinbox() {
    var faxinHtml = "";
    for (i = 1; i < 32; i++) {
        faxinHtml += "<li onclick='faxinInputFUZHI(" + i + ")'>" + i + "</li>";
        $(".faxinTimeConter").html(faxinHtml)
    }
    $(".faxinTimeOneBox").fadeIn()
}

function getUrlParam(name) {//封装方法
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function agesss(str) {
    var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (r == null) return 0;
    var d = new Date(r[1], r[3] - 1, r[4]);
    if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]) {
        var Y = new Date().getFullYear();
        return Y - r[1]
    }
    return ("输入的日期格式错误！");
}

$(function () {
    // var storage = window.localStorage;
    //theFinalStep  最后一步

    $(".theFinalStepSbmit").click(function () {
        // var clicknumber=storage.getItem("clicknumber")
        // var usernames=storage.getItem("usernames")
        // console.warn("点击数："+clicknumber)
        // if (clicknumber==null) {
        //     storage.setItem("clicknumber",0);
        // }
        // if (clicknumber==3&&usernames==$("#ymjusername").val();) {
        //   alert("！")
        //
        // }

        ymjusername = $("#ymjusername").val();
        if (qsstatus==0){


        if (checkChinese(ymjusername)) {
            // storage.setItem("usernames",$("#ymjusername").val());
            // var firstName = ymjusername.substr(0, 1);
            // if (checkbjx(firstName)) {
            ymjphone = $("#ymjphone").val();
            if (phonezz.test(ymjphone)) {

                ymjCard = $("#ymjCard").val();
                if (Cardzz.test(ymjCard)) {
                    var sourceIdsss= getUrlParam('sourceId')
                    $(".jiashiann").show()
                    $("#paydayMessage").val("点击选择发薪日")
                    $.ajax({
                        url: "/judgeUserMessage",
                        data: {
                            "name": ymjusername,
                            "mobile": ymjphone,
                            "idcard": ymjCard,
                            "sourceId":sourceIdsss
                        },
                        type: "post",
                        dataType: "json",
                        success: function (resultJson) {
                            console.warn(resultJson)
                            if (resultJson.code==1){
                                alert(resultJson.message);
                            }
                            if (resultJson.code == 0) {
                                if (resultJson.result.res == 1) {
                                    ymjSex = resultJson.result.sex;
                                    console.warn("性别:" + ymjSex)
                                    var ymjagebirthday = resultJson.result.birthday;
                                    var returnAge = agesss(ymjagebirthday.slice(0, 4) + "-" + ymjagebirthday.slice(4, 6) + "-" + ymjagebirthday.slice(6, 8));

                                        console.warn("年龄:" + returnAge)
                                        if (returnAge >= 23 && returnAge <= 45) {
                                            ymjage = returnAge;
                                            $(".theFinalStepInput").fadeIn();
                                            $(".theFinalStep").fadeOut();
                                            // $(".selectmaxbox").css({"height": "550px","overflow-y": "scroll"})
                                            if (inputmaxboxheight <= 350) {
                                                inputmaxboxheight = inputmaxboxheight + 35
                                                $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
                                            }
                                            $(".jiashiann").hide()
                                        } else {
                                            if (resultJson.requsetNumber==3){
                                                alert("温馨提示：实名错误提示，实名需要三要素一致， 姓名，手机号码，身份证 ")
                                            }else{
                                                alert("温馨提示：您的年龄不符合申请要求-年龄条件：23-45 之间")
                                            }

                                        }

                                } else {
                                    // alert("错误！请检查-姓名,手机号,身份证-是否都为 您 所有")
                                    if (resultJson.requsetNumber==3){
                                        alert("温馨提示：实名错误提示，实名需要三要素一致， 姓名，手机号码，身份证 ")
                                    }else {
                                        alert("温馨提示：请检查您的实名信息是否填写正确！")
                                    }
                                }


                            }

                            $(".jiashiann").hide()
                        },
                        error: function (result) {
                            $(".jiashiann").hide()
                            // $(location).prop('href', "/login")
                            alert("温馨提示：您的信息填写错误，请重试！")
                        }
                    })
                    $(".theFinalStepInput>.prompting").html("姓名:" + ymjusername);
                    console.warn("姓名:" + ymjusername)
                    console.warn("年龄:" + ymjage)
                    console.warn("手机号:" + ymjphone)
                    console.warn("QQ:" + ymjQQ)
                    console.warn("微信:" + ymjwechat)
                    console.warn("芝麻分:" + ymjSesame)
                    console.warn("身份证:" + ymjCard)
                    console.warn("性别:" + ymjSex)

                } else {
                    alert("温馨提示：请正确填写身份证号")
                }

            } else {
                alert("温馨提示：请正确填写手机号")
            }

            // } else {
            //     alert("请输入正确中文姓名！");
            // }
        } else {
            alert("温馨提示：请输入正确中文姓名，并且长度为2-4位");
        }
        }else{
            alert("温馨提示：渠道错误");
        }
    })

    $(".theFinalStepInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".theFinalStep").css({"z-index": zIndex})
        $(".theFinalStep").fadeIn();
        // $(".selectmaxbox").css({"height": "770px","overflow-y": "hidden"})
    })

    //验证百家姓
    function checkbjx(name) {
        //js正则表达式  match
        //为什么此时的match方法不对
        if ("赵|钱|孙|李|周|吴|郑|王|冯|陈|楮|卫|蒋|沈|韩|杨|朱|秦|尤|许|何|吕|施|张|孔|曹|严|华|金|魏|陶|姜|戚|谢|邹|喻|柏|水|窦|章|云|苏|潘|葛|奚|范|彭|郎|鲁|韦|昌|马|苗|凤|花|方|俞|任|袁|柳|酆|鲍|史|唐|费|廉|岑|薛|雷|贺|倪|汤|滕|殷|罗|毕|郝|邬|安|常|乐|于|时|傅|皮|卞|齐|康|伍|余|元|卜|顾|孟|平|黄|和|穆|萧|尹|姚|邵|湛|汪|祁|毛|禹|狄|米|贝|明|臧|计|伏|成|戴|谈|宋|茅|庞|熊|纪|舒|屈|项|祝|董|梁|杜|阮|蓝|闽|席|季|麻|强|贾|路|娄|危|江|童|颜|郭|梅|盛|林|刁|锺|徐|丘|骆|高|夏|蔡|田|樊|胡|凌|霍|虞|万|支|柯|昝|管|卢|莫|经|房|裘|缪|干|解|应|宗|丁|宣|贲|邓|郁|单|杭|洪|包|诸|左|石|崔|吉|钮|龚|程|嵇|邢|滑|裴|陆|荣|翁|荀|羊|於|惠|甄|麹|家|封|芮|羿|储|靳|汲|邴|糜|松|井|段|富|巫|乌|焦|巴|弓|牧|隗|山|谷|车|侯|宓|蓬|全|郗|班|仰|秋|仲|伊|宫|宁|仇|栾|暴|甘|斜|厉|戎|祖|武|符|刘|景|詹|束|龙|叶|幸|司|韶|郜|黎|蓟|薄|印|宿|白|怀|蒲|邰|从|鄂|索|咸|籍|赖|卓|蔺|屠|蒙|池|乔|阴|郁|胥|能|苍|双|闻|莘|党|翟|谭|贡|劳|逄|姬|申|扶|堵|冉|宰|郦|雍|郤|璩|桑|桂|濮|牛|寿|通|边|扈|燕|冀|郏|浦|尚|农|温|别|庄|晏|柴|瞿|阎|充|慕|连|茹|习|宦|艾|鱼|容|向|古|易|慎|戈|廖|庾|终|暨|居|衡|步|都|耿|满|弘|匡|国|文|寇|广|禄|阙|东|欧|殳|沃|利|蔚|越|夔|隆|师|巩|厍|聂|晁|勾|敖|融|冷|訾|辛|阚|那|简|饶|空|曾|毋|沙|乜|养|鞠|须|丰|巢|关|蒯|相|查|后|荆|红|游|竺|权|逑|盖|益|桓|公|万俟|司马|上官|欧阳|夏侯|诸葛|闻人|东方|赫连|皇甫|尉迟|公羊|澹台|公冶|宗政|濮阳|淳于|单于|太叔|申屠|公孙|仲孙|轩辕|令狐|锺离|宇文|长孙|慕容|鲜于|闾丘|司徒|司空|丌官|司寇|仉|督|子车|颛孙|端木|巫马|公西|漆雕|乐正|壤驷|公良|拓拔|夹谷|宰父|谷梁|晋|楚|阎|法|汝|鄢|涂|钦|段干|百里|东郭|南门|呼延|归|海|羊舌|微生|岳|帅|缑|亢|况|后|有|琴|梁丘|左丘|东门|西门|商|牟|佘|佴|伯|赏|南宫|墨|哈|谯|笪|年|爱|阳|佟|第五|言|福".search(name) != -1
        ) {
            return true;
        } else {
            return false;
        }
    }

    //校验只能输入汉字，并且长度为2-4
    function checkChinese(name) {

        reg = /^[\u4E00-\u9FA5]{2,4}$/;

        if (reg.test(name)) {

            return true;
        } else {
            return false;

        }

    }


    $(".anywhereInput>.ButtonThe").click(function () {
        $(".anywhere").css({"display": "block", "z-index": zIndex})
    })
    //anywhere  有无保单
    $(".anywhereselect li").click(function () {
        anywherevalue = $(this).attr("value")
        $(".anywhereInput>.prompting").html("有无保单: " + anywherevalue);
        console.warn("有无保单:" + anywherevalue)
        $(".anywhereInput").fadeIn();
        $(".anywhere").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".anywhereInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".anywhere").css({"z-index": zIndex})
        $(".anywhere").fadeIn();
    })
    //supplementarySbmit  补充信息
    $(".supplementarySbmit").click(function () {
        ymjQQ = $("#ymjQQ").val();
        var qqzz=/^[1-9]\d{4,9}$/;

        if (qqzz.test(ymjQQ)) {
            ymjwechat = $("#ymjwechat").val();
            if (ymjwechat!="") {
                ymjSesame = $("#ymjSesame").val();
                if (ymjSesame >= 550 && ymjSesame <= 950) {
                    $(".supplementaryInput>.prompting").html("QQ号: " + ymjQQ);
                    console.warn("QQ号:" + ymjQQ)
                    $(".supplementaryInput").fadeIn();
                    $(".supplementary").fadeOut();
                    if (inputmaxboxheight <= 350) {
                        inputmaxboxheight = inputmaxboxheight + 35
                        $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
                    }
                } else {
                    alert("温馨提示：请正确填写芝麻分（ 550 至 950 ） ")
                }
            } else {
                alert("温馨提示：请填写微信号")
            }
        } else {
            alert("温馨提示：请正确填写QQ号")
        }
    })
    $(".supplementaryInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".supplementary").css({"z-index": zIndex})
        $(".supplementary").fadeIn();
    })
    //payday  发薪日

    $(".paydayxiayibu").click(function () {

        if (paydayvalue <= 31) {
            $(".paydayInput>.prompting").html("发薪日：" + paydayvalue);
            console.warn("发薪日：" + paydayvalue)
            $(".paydayInput").fadeIn();
            $(".payday").fadeOut();
            // $(".selectmaxbox").css({"height": "260px","overflow-y": "scroll"})

            $(".inputmaxbox").css({"height": "550px"})

        } else {
            alert("温馨提示：请正确填写发薪日")
        }
    })
    $(".paydayInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".payday").css({"z-index": zIndex})
        $(".payday").fadeIn();
    })
    //monthly  月收入
    $(".monthlyxiayibu").click(function () {
        monthlyvalue = $("#monthlyMessage").val()
        if (monthlyvalue != "") {
            $(".monthlyInput>.prompting").html("月收入：" + monthlyvalue);
            console.warn("月收入：" + monthlyvalue)
            $(".monthlyInput").fadeIn();
            $(".monthly").fadeOut();
            if (inputmaxboxheight <= 350) {
                inputmaxboxheight = inputmaxboxheight + 35
                $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
            }
        } else {
            alert("温馨提示：请正确填写月收入")
        }
    })
    $(".monthlyInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".monthly").css({"z-index": zIndex})
        $(".monthly").fadeIn();
    })
    //AddressLocation  单位地址
    $(".AddressLocationxiayibu").click(function () {
        AddressLocationvalue = $("#AddressLocationMessage").val()
        if (AddressLocationvalue != "") {
            $(".AddressLocationInput>.prompting").html("单位地址：" + AddressLocationvalue);
            console.warn("单位地址：" + AddressLocationvalue)
            $(".AddressLocationInput").fadeIn();
            $(".AddressLocation").fadeOut();
            if (inputmaxboxheight <= 350) {
                inputmaxboxheight = inputmaxboxheight + 35
                $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
            }
        } else {
            alert("温馨提示：请正确填写单位地址")
        }
    })
    $(".AddressLocationInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".AddressLocation").css({"z-index": zIndex})
        $(".AddressLocation").fadeIn();
    })
    //unitsCurrency  工作单位
    $(".unitsCurrencyxiayibu").click(function () {
        unitsCurrencyvalue = $("#unitsCurrencyMessage").val()
        if (unitsCurrencyvalue != "") {
            $(".unitsCurrencyInput>.prompting").html("工作单位：" + unitsCurrencyvalue);
            console.warn("工作单位：" + unitsCurrencyvalue)
            $(".unitsCurrencyInput").fadeIn();
            $(".unitsCurrency").fadeOut();
            if (inputmaxboxheight <= 350) {
                inputmaxboxheight = inputmaxboxheight + 35
                $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
            }
        } else {
            alert("温馨提示：请正确填写工作单位")
        }
    })
    $(".unitsCurrencyInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".unitsCurrency").css({"z-index": zIndex})
        $(".unitsCurrency").fadeIn();
    })
    //Professional  职业
    $(".Professionalselect li").click(function () {
        Professionalvalue = $(this).attr("value")
        $(".ProfessionalInput>.prompting").html("职业：" + Professionalvalue);
        console.warn("职业：" + Professionalvalue)
        $(".ProfessionalInput").fadeIn();
        $(".Professional").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".ProfessionalInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".Professional").css({"z-index": zIndex})
        $(".Professional").fadeIn();
    })
    //Northwestern  有无信用卡
    $(".Northwesternselect li").click(function () {
        Northwesternvalue = $(this).attr("value")
        $(".NorthwesternInput>.prompting").html("有无信用卡：" + Northwesternvalue);
        console.warn("有无信用卡：" + Northwesternvalue)
        $(".NorthwesternInput").fadeIn();
        $(".Northwestern").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".NorthwesternInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".Northwestern").css({"z-index": zIndex})
        $(".Northwestern").fadeIn();
    })

    //Professor  有无本地公积金
    $(".Professorselect li").click(function () {
        Professorvalue = $(this).attr("value")
        $(".ProfessorInput>.prompting").html("有无本地公积金：" + Professorvalue);
        console.warn("有无本地公积金：" + Professorvalue)
        $(".ProfessorInput").fadeIn();
        $(".Professor").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".ProfessorInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".Professor").css({"z-index": zIndex})
        $(".Professor").fadeIn();
    })
    //linusericsson  现居地
    $(".linusericssonselectxiayibu").click(function () {
        linusericssonvalue = $("#linusericssonmessage").val();
        if (linusericssonvalue != "") {
            $(".linusericssonInput>.prompting").html("现居地：" + linusericssonvalue);
            ymjRegion = linusericssonvalue.split(" ")[0];

            console.warn("现居地：" + linusericssonvalue)
            $(".linusericssonInput").fadeIn();
            $(".linusericsson").fadeOut();
            if (inputmaxboxheight <= 350) {
                inputmaxboxheight = inputmaxboxheight + 35
                $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
            }
        } else {
            alert("温馨提示：请正确填写现居地")
        }
    })
    $(".linusericssonInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".linusericsson").css({"z-index": zIndex})
        $(".linusericsson").fadeIn();
    })

    //marriage  婚姻情况
    $(".marriageselect li").click(function () {
        marriagevalue = $(this).attr("value")
        $(".marriageInput>.prompting").html("婚姻情况：" + marriagevalue);
        console.warn("婚姻情况：" + marriagevalue)
        $(".marriageInput").fadeIn();
        $(".marriage").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".marriageInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".marriage").css({"z-index": zIndex})
        $(".marriage").fadeIn();
    })

    // //Borrowing  借贷金额
    // $(".startinputbox li").click(function() {
    // 	$(".startinputbox li").css({
    // 		"background-color": "#007AFF"
    // 	});
    // 	$(this).css({
    // 		"background-color": "#163960"
    // 	});
    // 	startnumber=$(this).attr("value")
    // })
    // $(".stopinputbox li").click(function() {
    // 	$(".stopinputbox li").css({
    // 		"background-color": "#007AFF"
    // 	});
    // 	$(this).css({
    // 		"background-color": "#163960"
    // 	});
    // 	stopnumber=$(this).attr("value")
    // })
    // $(".Borrowingxiayibu").click(function() {
    // 	if(startnumber<stopnumber){
    // 		Borrowingnumber=startnumber+"-"+stopnumber+"万"
    // 	    $(".BorrowingInput>.prompting").html("借贷额度："+startnumber+"-"+stopnumber+"万");
    // 		 $(".BorrowingInput").css({"display": "block"})
    // 		 $(".Borrowing").fadeOut();
    // 		if (inputmaxboxheight<=350){
    // 			inputmaxboxheight=inputmaxboxheight+35
    // 			$(".inputmaxbox").css({"height": inputmaxboxheight+"px"})
    // 		}
    //
    // 	}else{
    // 	alert("借贷 开始额度不能小于结束额度")
    // 	}
    // })
    // $(".BorrowingInput>.ButtonThe").click(function() {
    // 	 zIndex=zIndex+100
    // 		 $(".Borrowing").css({"display": "block","z-index":zIndex})
    // })
    //education  您的学历
    $(".educationselect li").click(function () {
        educationvalue = $(this).attr("value")
        $(".educationInput>.prompting").html("您的学历：" + educationvalue);
        console.warn("您的学历：" + educationvalue)
        $(".educationInput").fadeIn();
        $(".education").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".educationInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".education").css({"z-index": zIndex})
        $(".education").fadeIn();
    })
    //Borrowings 额度
    $(".Borrowingselect li").click(function () {
        Borrowingnumber = $(this).attr("value")
        $(".BorrowingInput>.prompting").html("贷款额度：" + Borrowingnumber);
        console.warn("贷款额度：" + Borrowingnumber)
        $(".BorrowingInput").fadeIn();
        $(".Borrowing").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".BorrowingInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".Borrowing").css({"z-index": zIndex})
        $(".Borrowing").fadeIn()
    })
    //whether  有无社保
    $(".whetherselect li").click(function () {
        whethervalue = $(this).attr("value")
        $(".whetherInput>.prompting").html("您的社保：" + whethervalue);
        console.warn("您的社保：" + whethervalue)
        $(".whetherInput").fadeIn();
        $(".whether").fadeOut()
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".whetherInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".whether").css({"z-index": zIndex})
        $(".whether").fadeIn()
    })
    //Assets  资产情况
    $(".Assetsselect li").click(function () {
        Assetsvalue = $(this).attr("value")
        $(".AssetsInput>.prompting").html("资产情况：" + Assetsvalue);
        console.warn("资产情况：" + Assetsvalue)
        $(".AssetsInput").fadeIn();
        $(".Assets").fadeOut();
        if (inputmaxboxheight <= 350) {
            inputmaxboxheight = inputmaxboxheight + 35
            $(".inputmaxbox").css({"height": inputmaxboxheight + "px"})
        }
    })
    $(".AssetsInput>.ButtonThe").click(function () {
        zIndex = zIndex + 100
        $(".Assets").css({"z-index": zIndex})
        $(".Assets").fadeIn()
    })
})
