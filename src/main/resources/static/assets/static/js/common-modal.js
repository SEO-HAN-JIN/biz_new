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

        // 모달이 열렸을 때 Choices.js 초기화
        $(modalElement).off('shown.bs.modal').on('shown.bs.modal', function () {
            const selectElements = modalElement.querySelectorAll('select.choices');
            selectElements.forEach(selectElement => {
                // Choices.js 인스턴스가 이미 있는 경우 제거
                if (selectElement.choicesInstance) {
                    selectElement.choicesInstance.destroy();
                }

                // Choices 인스턴스 생성 및 할당
                const instance = new Choices(selectElement);
                selectElement.choicesInstance = instance;
            });
        });

        // 모달 타이틀 변경
        const modalTitleElement = modalElement.querySelector('#modalTitle');
        if (modalTitleElement && options.title) {
            modalTitleElement.textContent = options.title;
        }

        // 너비 설정 (옵션에 width가 있으면 적용)
        const modalDialog = modalElement.querySelector('.modal-dialog');
        if (options.width) {
            modalDialog.style.width = options.width;
        } else {
            modalDialog.style.width = ''; // 기본값으로 복원
        }

        // 모달 바디의 overflow-y 제거
        const modalBody = modalElement.querySelector('.modal-body');
        if (modalBody) {
            modalBody.style.overflowY = ''; // overflow-y 속성 제거
        }

        // '적용' 버튼 클릭 시 콜백 함수 연결
        const applyButton = modalElement.querySelector('#modalApplyBtn');
        if (applyButton) {
            applyButton.onclick = () => {
                if (options.onApply && typeof options.onApply === 'function') {
                    const result = options.onApply();
                    if (result !== false) {
                        this.closeModal('commonModal');
                    }
                } else {
                    this.closeModal('commonModal');
                }
            };
        }

        // 모달 열렸을 때 onOpened 콜백 호출
        $(modalElement).on('shown.bs.modal', function () {
            if (options.onOpened && typeof options.onOpened === 'function') {
                setTimeout(() => {
                    options.onOpened();
                }, 0);
            }
        });

        // 모달 표시 (스크롤 없애기 위해 body에 클래스 추가)
        $('body').addClass('modal-open');
        // 모달 표시
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

    resetFormFields: function (modalId) {
        const modalElement = document.getElementById(modalId);
        if (!modalElement) {
            return;
        }

        const inputs = modalElement.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            switch (input.tagName.toLowerCase()) {
                case 'input':
                    if (['text', 'number', 'email', 'date'].includes(input.type)) {
                        input.value = ''; // 초기화
                    } else if (['checkbox', 'radio'].includes(input.type)) {
                        input.checked = false;
                    }
                    break;
                case 'select':
                    input.selectedIndex = 0; // 첫 번째 항목 선택
                    break;
                case 'textarea':
                    input.value = ''; // 텍스트 영역 초기화
                    break;
                default:
                    break;
            }
        });
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
        const modalContainer = document.createElement('div');
        modalContainer.innerHTML = modalHtml.trim();
        document.body.appendChild(modalContainer.firstChild);

        return document.getElementById('commonModal');
    }
};
