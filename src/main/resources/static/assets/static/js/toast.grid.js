let CustomTuiGrid;

if (typeof CustomTuiGrid === "undefined") {
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
                        const containerWidth = self.grid.el.offsetWidth - 10;

                        // 모든 컬럼의 초기 width 합계 계산
                        const totalInitialWidth = self.columns.reduce((sum, column) => sum + (column.width || 100), 0);

                        // 각 컬럼의 비율에 맞춰 너비 조정
                        self.columns.forEach(column => {
                            const widthRatio = (column.width || 100) / totalInitialWidth;
                            column.width = Math.floor(containerWidth * widthRatio);  // 비율에 맞게 너비 설정
                        });

                        self.grid.setColumns(self.columns);  // 업데이트된 컬럼 설정 반영
                        self.grid.refreshLayout();  // 레이아웃 갱신
                    }, 100);
                }
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
            }
        };

        return CustomTuiGrid;
    })();
}
