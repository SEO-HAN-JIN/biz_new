let CustomTuiGrid;


if (typeof CustomTuiGrid === "undefined") {

    // 기존 CustomTuiGrid 정의 바로 아래에 추가
    class CustomTuiGridButtonRenderer {
        constructor(props) {

            const el = document.createElement('button');
            el.innerText = props.value || props.columnInfo.renderer.options.buttonText || 'Button';
            el.style.cursor = 'pointer';
            el.style.padding = '5px 10px';
            el.style.border = 'none';
            el.style.borderRadius = '4px';
            el.style.backgroundColor = '#435EBE'; // 신뢰감 있는 파란색
            el.style.color = '#fff'; // 흰색 텍스트
            el.style.fontWeight = 'bold'; // 굵은 텍스트
            el.style.boxShadow = '0 2px 4px rgba(0, 0, 0, 0.1)';

            // 버튼 상태 설정
            if (props.disabled === false) {
                this.disable();
            }

            // 마우스 오버 시 효과
            el.addEventListener('mouseover', () => {
                el.style.backgroundColor = '#3a50a6'; // 어두운 파란색
            });
            el.addEventListener('mouseout', () => {
                el.style.backgroundColor = '#435EBE'; // 원래 색상
            });

            // 클릭 이벤트
            el.addEventListener('click', () => {
                const grid = props.grid;
                const rowKey = props.rowKey;
                const rowData = grid.getRow(rowKey);

                const onClick = props.columnInfo.renderer.options.onClick;
                if (typeof onClick === 'function') {
                    onClick(rowData, rowKey); // 클릭 이벤트 핸들러 호출
                }
            });

            this.el = el;
        }

        getElement() {
            return this.el;
        }

        // 버튼 텍스트 업데이트 메서드
        updateText(newText) {
            this.el.innerText = newText || ''; // 텍스트 업데이트 또는 비우기
        }

        disable() {
            if (this.el) {
                this.el.disabled = true; // 비활성화 처리
                this.el.style.opacity = '0.5'; // 시각적 효과
                this.el.style.cursor = 'not-allowed';
            } else {
                console.error('Element not initialized for disable.');
            }
        }

        enable() {
            if (this.el) {
                this.el.disabled = false; // 활성화 처리
                this.el.style.opacity = '1';
                this.el.style.cursor = 'pointer';
            }
        }

        render(props) {
            // 필요 시 props 기반 상태 업데이트
            if (props.value === false) {
                this.disable();
            } else if (props.value) {
                this.updateText(props.value);
            }
        }
    }


    CustomTuiGrid = (function() {

        function CustomTuiGrid() {
            this.grid = null;
            this.columns = [];
            this.fitStyle = 'none';  // 기본적으로 fitStyle은 'none'
            this.summaryOptions = {}; // 전체 summary 설정 저장
            this.data = []; // 현재 그리드 데이터 저장
            return this;
        }

        CustomTuiGrid.prototype = {

            // fitStyle 설정 메소드
            setFitStyle: function(style) {
                this.fitStyle = style;
                return this;
            },

            // 그리드 초기화 메소드
            init: function(el, data) {

                const self = this;

                this.grid = new tui.Grid({
                    el: document.getElementById(el),
                    data: data,
                    header: {
                        height: 50
                    },
                    columns: this.columns,
                    rowHeight: 35,
                    bodyHeight: 600,
                    scrollX: true,
                    scrollY: true,
                    columnOptions: {
                        resizable: true,         // 컬럼 크기 조정 가능
                        frozenCount: 0           // 고정 컬럼 없음
                    },
                    summary: {  // 전체 summary 옵션 설정
                        height: 40,
                        position: 'bottom',
                        columnContent: this.summaryOptions  // 컬럼별 summary 내용
                    }
                });

                // fitStyle 옵션에 따른 비율 조정
                if (this.fitStyle === 'fill') {
                    setTimeout(function() {
                        const containerWidth = self.grid.el.offsetWidth;

                        // 모든 컬럼의 초기 width 합계 계산
                        const totalInitialWidth = self.columns.reduce((sum, column) => sum + (column.width || 100), 0);

                        // 각 컬럼의 비율에 맞춰 너비 조정
                        self.columns.forEach(column => {
                            const widthRatio = (column.width || 80) / totalInitialWidth;
                            column.width = Math.floor(containerWidth * widthRatio);  // 비율에 맞게 너비 설정
                        });

                        self.grid.setColumns(self.columns);  // 업데이트된 컬럼 설정 반영
                        self.grid.refreshLayout();  // 레이아웃 갱신
                    }, 100);
                }

                // 레이아웃 갱신
                setTimeout(() => {
                    this.grid.refreshLayout();
                }, 100);

                return this;
            },

            // AJAX 데이터 바인딩 메소드
            bind: function(data) {
                if (this.grid) {
                    this.data = data;  // 데이터 저장
                    this.grid.resetData(this.data);  // 데이터 리셋 및 요약 갱신
                }
            },

            // 일반 컬럼 추가 메소드
            add: function(id, header, width, options = {}) {
                const column = {
                    header: header,
                    name: id,
                    width: width || "auto",
                    align: options.align || "center",
                    sortable: options.sortable || false, // 정렬 여부 설정
                    hidden: options.visible === false, // visible이 false면 hidden: true 설정
                    ...options
                };

                this.columns.push(column);

                // summary 설정이 있으면 summaryOptions에 추가
                if (options.summary) {
                    this.summaryOptions[id] = options.summary;
                }

                return this;
            },

            // 날짜 컬럼 추가 메소드
            addDate: function(id, header, width, options = {}) {
                const defaultOptions = {
                    align: options.align || "center",
                    editor: {
                        type: 'datePicker',
                        options: {
                            format: 'yyyy-MM-dd'  // 날짜 형식 지정
                        }
                    },
                    renderer: {
                        type: 'date',
                        options: {
                            format: 'yyyy-MM-dd'
                        }
                    }
                };
                //return this.add(id, header, width, {defaultOptions, options });
                return this.add(id, header, width, { ...options, defaultOptions });
            },

            // 셀렉트 박스 컬럼 추가 메소드
            addSelect: function(id, header, width, options = {}) {
                const defaultOptions = {
                    editor: {
                        type: 'select',
                        options: {
                            listItems: options.listItems || [] // 리스트 항목 전달
                        }
                    },
                    renderer: {
                        type: 'select',
                        options: {
                            listItems: options.listItems || []
                        }
                    }
                };
                //return this.add(id, header, width, {defaultOptions, options });
                return this.add(id, header, width, { ...options, defaultOptions });
            },

            // 숫자 컬럼 추가 메소드
            addNumber: function(id, header, width, options = {}) {
                const defaultOptions = {
                    dataType: 'number',
                    formatter: function({ value }) {
                        value = value || '0';
                        if (value != null) {
                            let formattedValue = options.comma ? value.replace(/\B(?=(\d{3})+(?!\d))/g, ',') : value.toString(); // 콤마 적용
                            if (options.textColor) {
                                return `<span style="color: ${options.textColor}">${formattedValue}</span>`; // 텍스트 색상 적용
                            }
                            return formattedValue;
                        }
                        return '';
                    }
                };

                return this.add(id, header, width, { ...options, ...defaultOptions });
            },

            // 버튼 컬럼 추가 메소드
            addButton: function(id, header, width, options = {}) {
                const column = {
                    name: id,
                    header: header,
                    width: width || 150,
                    align: options.align || 'center',
                    renderer: {
                        type: CustomTuiGridButtonRenderer,
                        options: {
                            buttonText: options.buttonText || 'Button',
                            textColor: options.textColor || '#000', // 텍스트 색상
                            onClick: options.onClick || function() {} // 클릭 이벤트 핸들러
                        }
                    }
                };

                this.columns.push(column);
                return this;
            },

            // 그리드 렌더링 메소드
            render: function() {
                if (this.grid) {
                    this.grid.setColumns(this.columns);
                    console.log('그리드 렌더링 완료');
                } else {
                    console.error('그리드가 초기화되지 않음');
                }
                return this;
            },

            // 행 추가 메소드
            appendRow: function(rowData) {
                if (this.grid) {
                    this.grid.appendRow(rowData);
                } else {
                    console.error('그리드가 초기화되지 않았습니다. appendRow 호출 불가');
                }
            },

            clear:function() {
                this.grid.clear();
            },

            getGrid: function() {
                return this.grid;
            },

            setFocus: function(rowKey) {
                if (this.grid) {
                    // 특정 행(rowKey)에 포커스를 설정
                    this.grid.focus(rowKey);
                } else {
                    console.error('Grid is not initialized. Cannot set focus.');
                }
            },

            load : function(url, param, callback) {
                const self = this;

                if (typeof param === 'function') {
                    callback = param;
                    param = undefined;
                }

                self.clear(); // 기존 데이터 초기화

                if (param) {
                    param.perPage = param.perPage || 1; // 기본 perPage 설정
                    param.page = param.page || 1; // 기본 페이지 설정
                }

                self.grid.dispatch('setLoadingState', 'LOADING'); // 로딩 상태 설정

                $.get(url, param, function(data) {
                    self.bind(data); // 데이터 바인딩

                    if (data.length > 0) {
                        self.grid.dispatch('setLoadingState', 'DONE');
                        self.setFocus(0); // 첫 번째 행에 포커스 설정
                    } else {
                        self.grid.dispatch('setLoadingState', 'EMPTY'); // 데이터가 없는 경우
                    }

                    if (callback) {
                        callback(data); // 콜백 실행
                    }
                }).fail(function(error) {
                    console.error('Error loading data:', error);
                    self.grid.dispatch('setLoadingState', 'ERROR'); // 로딩 에러 상태
                });
            }
        };

        return CustomTuiGrid;
    })();
}
