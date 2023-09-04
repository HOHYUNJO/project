/* ========================================= * 
		        BEST VIEWED FULLSCREEN
   https://codepen.io/ig_design/full/KKVQpVP
 * ========================================= */

// ID중복여부
function checkIdDuplicate() {
    var memberId = $("#memberId").val(); // 아이디 입력 필드의 값을 가져옵니다.

    $.ajax({
        url: "/checkIdDuplicate",
        type: "GET",
        data: { "memberId": memberId },
        success: function(result) {
            if (result) {
                // 중복된 아이디인 경우 처리할 로직을 작성합니다.
                alert("중복된 아이디입니다.")
                // 예: 오류 메시지 표시, 아이디 입력 필드 초기화 등
            } else {
                // 중복되지 않은 아이디인 경우 처리할 로직을 작성합니다.
                alert("사용가능한 아이디 입니다.")
                // 예: 아이디 사용 가능 메시지 표시 등
            }
        }
    });
}
