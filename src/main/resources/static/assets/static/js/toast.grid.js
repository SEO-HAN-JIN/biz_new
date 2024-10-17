let CustomTuiGrid;

if (typeof CustomTuiGrid === "undefined") {
    CustomTuiGrid = (function() {

        function CustomTuiGrid() {
            this.grid = null;
            this.columns = [];
            return this;
        }

        CustomTuiGrid.prototype = {
            // 그리드 초기화 메소드
            init: function(el, data) {
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
                    scrollY: true
                });
                return this;
            },

            // 일반 컬럼 추가 메소드
            add: function(id, header, width, options = {}) {
                const column = {
                    header: header,
                    name: id,
                    width: width || 100,
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
