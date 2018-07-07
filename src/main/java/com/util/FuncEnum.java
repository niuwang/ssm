package com.util;

/**
 * @Auther: apple
 * @Date: 2018/7/3 19:19
 * @Description:
 */
public enum FuncEnum {
        NATURE("$natureOrder(",1),INDEX("$indexOrder(",2),CHAR("$charOrder(",3),CHARDESC("$charOrderDESC(",4);

        private String name;
        private int index;

        FuncEnum(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

}
