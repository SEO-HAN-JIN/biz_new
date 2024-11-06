const isNull = (v) => {
    return (v === undefined || v === null || v === 'undefined' || v === '');
}

// 콤마 형식을 추가하는 함수
function formatNumber(num) {
    // 입력 값에서 숫자가 아닌 문자 제거
    let value = String(num).replace(/\D/g, '');

    // 숫자에 콤마 추가
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    return value;
}

function formatToCurrency(value) {
    // 숫자가 아닌 문자를 제거
    value = value.replace(/[^0-9]/g, '');

    // 값이 비어있으면 빈 문자열 반환
    if (value === '') {
        return '';
    }

    // 천 단위로 콤마 추가
    return Number(value).toLocaleString();  // 콤마 추가
}

const isValidEmail = (email) => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
}


$.fn.addOptions = function(options) {
    const $select = this;
    $select.empty(); // 기존 옵션을 지웁니다.

    if (typeof options.placeholder !== "undefined") {
        $select.append(`<option value="">${options.placeholder}</option>`); // 기본 옵션 추가
    }

    $.each(options.items, function(index, item) {
        $select.append(`<option value="${item[options.value]}">${item[options.text]}</option>`);
    });
}

const CommonJs = {
    initFindCodes: (param) => {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: "/api/system/codes/pages",
                data: JSON.stringify(param),
                contentType:'application/json',
                type: 'POST',
                success: (result) => {
                    resolve(result);
                },error: (xhr, status, error) => {
                    alert(xhr.responseText);

                }
            })
        })
    }
}
