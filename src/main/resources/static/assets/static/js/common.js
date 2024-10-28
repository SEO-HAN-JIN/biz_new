const isNull = (v) => {
    return (v === undefined || v === null || v === 'undefined' || v === '');
}

// 콤마 형식을 추가하는 함수
function formatNumber($input) {
    // 입력 값에서 숫자가 아닌 문자 제거
    let value = $input.val().replace(/\D/g, '');

    // 숫자에 콤마 추가
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    // 입력 값 업데이트
    $input.val(value);
}
