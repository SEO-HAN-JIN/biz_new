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

// 이벤트 처리 함수
function formatPriceInputs() {
    // 모든 price-format 클래스에 이벤트 적용
    const priceInputs = document.querySelectorAll('.is-num');
    priceInputs.forEach(input => {
        this.value = addComma(this.value);
    });
}

var numberKeyupEvent = function(e){
    addComma(this);
};

function addComma(obj)
{
    var rgx1 = /[^-\.0-9]/g
    var rgx2 = new RegExp(/(-?\d+)(\d{3})/);
    var num = obj.value.replace(rgx1,"");

    var bExists = num.indexOf(".", 0);//0번째부터 .을 찾는다.
    var strArr = num.split('.');

    while (rgx2.test(strArr[0])) strArr[0] = strArr[0].replace(rgx2, '$1' + ',' + '$2');

    if (bExists > -1) {
        //. 소수점 문자열이 발견되지 않을 경우 -1 반환
        num = strArr[0] + "." + strArr[1];
    } else { //정수만 있을경우 //소수점 문자열 존재하면 양수 반환
        num = strArr[0];
    }
    obj.value = num;
}

// set comma
var setComma = function (val) {
    if(val){
        var v = removeComma(val);
        return v.replace(/(\d)(?=(?:\d{3}){2,}(?:\.|$))|(\d)(\d{3}(?:\.\d*)?$)/g
            , '$1$2' + ',' + '$3');
    }
    return val;
};

// remove comma
var removeComma = function(val) {
    return val.toString().replace(/,/g, '');
};

$('.is-num').bind('keyup', numberKeyupEvent);

// 날짜 포맷팅 함수 추가
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}


function isNumeric(str) {
    if (typeof str != "string") return false // we only process strings!
    return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
        !isNaN(parseFloat(str)) // ...and ensure strings of whitespace fail
}