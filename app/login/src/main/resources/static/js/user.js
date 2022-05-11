let user_func = {
    init:function(){
        $("#btn-save").on("click",()=>{
            this.save();
        });
        $("#btn-check-email").on("click",()=>{
            this.check_email();
        });
        $("#btn-email-auth").on("click",()=> {
            this.email_auth();
        });
        $("#btn-login").on("click",()=>{
            this.login();
        });
    },

    save:function (){
        let data = {
            email: $("#email").val(),
            password: $("#password").val(),
            nickname: $("#nickname").val()
        }
        $.ajax({
            type: "post",
            url: "/user",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터 타입
            dataType: "json",
        }).done (function(response){
            alert("회원가입 완료");
            location.href="/main";
        }).fail (function (error){
            alert("회원가입 에러");
        });
    },


    check_email:function(){
        let email = $("#email").val();

        $.ajax({
            type: "post",
            url: "/user/check/"+email,
            dataType : "json",
            contentType : "application/json; charset=utf-8",
        }).done (function (response){
            console.log(response);
            if(response.status == 500){
                $(".chk").html("회원가입 가능");
                $("#btn-email-auth").prop("disabled",false);
            }else{
                $(".chk").html("회원가입 불가능");
                $("#btn-email-auth").prop("disabled",true);
            }
        }).fail (function(error){
            alert(JSON.stringify(error));

        });
    },

    email_auth:function (){
        let data = {
            email: $("#email").val(),
            nickname: $("#nickname").val()
        }
        $.ajax({
            type: "post",
            url: "/user/email",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function(msg){
            if(msg == true) {
                alert("이메일을 확인해 주세요");
            }else{
                alert("이메일 전송 실패");
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    },
    login:function (){
        let data = {
            email: $("#email").val(),
            password: $("#password").val()
        }
        $.ajax({
            type: "post",
            url: "/user/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }). done(function (response){
            if(response.status == 200) {
                alert("로그인 성공");
                location.href="/main";
            }else{
                alert("로그인 실패");
                location.href="/main";
            }
        }). fail (function (error){
            location.href="/login";
            alert(JSON.stringify(error));
        });
    },

}
user_func.init();