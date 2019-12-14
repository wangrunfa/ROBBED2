var floor = 0;
var roomNumber = 0;
var id = 0;
var clickStatic=0;


function occupation(floor1, roomNumber1, id1) {
    clickStatic=0;
    floor = floor1;
    roomNumber = roomNumber1;
    id = id1;

    $("#modal-body-texts").text('您选择的 楼层：' + floor + '-房间号：' + roomNumber + '  请确认！');

}


function qcconfirm() {
    console.log(floor);
    console.log(roomNumber);
    console.log(id);
    if(clickStatic==0) {
        $.ajax({
            //请求方式
            type: "POST",
            //请求地址
            url: "/occupied",
            //数据，json字符串
            data: {"id": id},
            //请求成功
            success: function (result) {
                console.log(result);
                if(result.code == 0){
                    $("#modal-body-texts").append('<br/><b style="color: #1cff70">' + result.data + '</b>');
                }
                if (result.code == 1) {
                    $("#modal-body-texts").append('<br/><b style="color: #ff4832">' + result.message + '</b>');
                }
                if(result.code == 2){
                    $(location).prop('href', '/loginym')
                }

                clickStatic=1;
            },
            //请求失败，包含具体的错误信息
            error: function (e) {
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    }
}






// function qcconfirm() {
//
//         $.ajax({
//             //请求方式
//             type: "POST",
//             //请求地址
//             url: "/occupied",
//             //数据，json字符串
//             data: {"id": id},
//             //请求成功
//             success: function (result) {
//                 console.log(result);
//                 if (result.code == 1) {
//                     $("#modal-body-texts").append('<br/><b style="color: #ff4832">' + result.message + '</b>');
//                 }else if(result.code == 2){
//                     $(location).prop('href', '/loginym')
//                 }else if(result.code == 0){
//                     $("#modal-body-texts").append('<br/><b style="color: #1cff70">' + result.data + '</b>');
//                 }
//                 clickStatic=1;
//             },
//             //请求失败，包含具体的错误信息
//             error: function (e) {
//                 console.log(e.status);
//                 console.log(e.responseText);
//             }
//         });
//
// }