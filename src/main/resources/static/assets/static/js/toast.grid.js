let CustomTuiGrid;

if (typeof CustomTuiGrid === "undefined") {
    CustomTuiGrid = (function() {

        function CustomTuiGrid() {
            this.grid = null;
            this.columns = [];
            this.fitStyle = 'none';  // 기본적으로 fitStyle은 'none'
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
                    }
                });

                // fitStyle 옵션에 따른 비율 조정
                if (this.fitStyle === 'fill') {
                    setTimeout(function() {
                        const containerWidth = self.grid.el.offsetWidth - 20;

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

            // 일반 컬럼 추가 메소드
            add: function(id, header, width, options = {}) {
                const column = {
                    header: header,
                    name: id,
                    width: width || "auto",
                    ...options
                };
                this.columns.push(column);
                return this;
            },

            // 날짜 컬럼 추가 메소드
            addDate: function(id, header, width, options = {}) {
                const defaultOptions = {
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
                return this.add(id, header, width, {defaultOptions, options });
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
                return this.add(id, header, width, {defaultOptions, options });
            },

            // 숫자 컬럼 추가 메소드
            addNumber: function(id, header, width, options = {}) {
                const defaultOptions = {
                    dataType: 'number',
                    editor: {
                        type: 'number'
                    },
                    align: 'right'
                };
                return this.add(id, header, width, {defaultOptions, options });
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

            // 데이터 바인딩 메소드
            bind: function(data) {
                if (this.grid) {
                    this.grid.resetData(data);  // 그리드에 데이터를 바인딩
                    console.log('데이터 바인딩 완료');
                } else {
                    console.error('그리드가 초기화되지 않아서 데이터를 바인딩할 수 없음');
                }
                return this;
            },

            clear:function() {
                this.grid.clear();
            },
        };

        return CustomTuiGrid;
    })();
}
