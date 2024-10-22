const ModalManager = {
    openModal: function (contentHtml, options = {}) {
        let modalElement = document.getElementById('commonModal');

        // 공통 모달이 없으면 동적으로 생성
        if (!modalElement) {
            modalElement = this.createCommonModal();
        }

        const contentElement = modalElement.querySelector('#modalContent');
        if (contentElement) {
            // 전달된 콘텐츠를 모달에 삽입
            contentElement.innerHTML = contentHtml.innerHTML;
        }

        // 모달 타이틀 변경
        const modalTitleElement = modalElement.querySelector('#modalTitle');
        if (modalTitleElement && options.title) {
            modalTitleElement.textContent = options.title; // options에 title 값이 있으면 타이틀 변경
        }

        // 입력 필드 초기화
        this.resetFormFields('commonModal');

        // '적용' 버튼 클릭 시 콜백 함수 연결
        const applyButton = modalElement.querySelector('#modalApplyBtn');
        if (applyButton) {
            applyButton.onclick = () => {
                if (options.onApply && typeof options.onApply === 'function') {
                    // onApply의 반환 값에 따라 모달 유지 또는 닫기
                    const result = options.onApply();
                    if (result !== false) { // onApply가 false가 아닌 경우에만 모달 닫기
                        this.closeModal('commonModal');
                    }
                } else {
                    this.closeModal('commonModal');
                }
            };
        }

        // 모달이 화면에 올바르게 표시되도록 설정
        $(modalElement).modal({ backdrop: 'static', keyboard: false });
        $(modalElement).modal('show');
    },

    closeModal: function (modalId) {
        const modalElement = document.getElementById(modalId);
        if (!modalElement) {
            console.error("모달을 찾을 수 없습니다: " + modalId);
            return;
        }

        $(modalElement).modal('hide');
    },

    // 다른 메서드는 그대로 유지
    resetFormFields: function (modalId) {
        // 필드 초기화 로직
    },

    createCommonModal: function () {
        const modalHtml = `
            <div class="modal fade" id="commonModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle">공통 모달</h5>
                            <button type="button" class="btn-close" aria-label="Close" onclick="ModalManager.closeModal('commonModal')"></button>
                        </div>
                        <div class="modal-body" id="modalContent">
                            <!-- 동적으로 콘텐츠가 삽입됩니다. -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-light-secondary" onclick="ModalManager.closeModal('commonModal')">닫기</button>
                            <button type="button" class="btn btn-primary ms-1" id="modalApplyBtn">적용</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        // 새 모달 요소를 생성하고 body에 추가
        const modalContainer = document.createElement('div');
        modalContainer.innerHTML = modalHtml.trim();
        document.body.appendChild(modalContainer.firstChild);

        return document.getElementById('commonModal');
    }
};
