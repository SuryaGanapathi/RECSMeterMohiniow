package com.recsmeterreading.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 19-04-2018.
 */

public class LocalTarrifModel {

    /**
     * category : {"100":{"50":{"unit_charge":"1.45"},"100":{"unit_charge":"2.60"},"200":{"unit_charge":"3.60"},"201":{"unit_charge":"6.90"}},"101":{"50":{"unit_charge":"1.45"},"100":{"unit_charge":"2.60"},"200":{"unit_charge":"3.60"},"201":{"unit_charge":"6.90"}},"102":{"50":{"unit_charge":"2.60"},"100":{"unit_charge":"2.60"},"200":{"unit_charge":"3.60"},"300":{"unit_charge":"6.90"},"301":{"unit_charge":"7.75"}},"103":{"50":{"unit_charge":"2.68"},"100":{"unit_charge":"3.35"},"200":{"unit_charge":"5.42"},"300":{"unit_charge":"7.11"},"400":{"unit_charge":"7.98"},"500":{"unit_charge":"8.52"},"501":{"unit_charge":"9.06"}},"200":{"50":{"unit_charge":"5.40"},"51":{"unit_charge":"6.90"},"100":{"unit_charge":"7.69"},"300":{"unit_charge":"9.06"},"500":{"unit_charge":"9.61"},"501":{"unit_charge":"10.19"}},"201":{"50":{"unit_charge":"5.40"},"51":{"unit_charge":"6.90"},"100":{"unit_charge":"7.69"},"300":{"unit_charge":"9.06"},"500":{"unit_charge":"9.61"},"501":{"unit_charge":"10.19"}},"202":{"0":{"unit_charge":"12.28"}},"203":{"0":{"unit_charge":"11.77"}},"300":{"0":{"unit_charge":"6.71"}},"301":{"0":{"unit_charge":"3.86"}},"302":{"0":{"unit_charge":"3.86"}},"303":{"0":{"unit_charge":"4.89"}},"304":{"0":{"unit_charge":"5.91"}},"305":{"0":{"unit_charge":"5.91"}},"306":{"0":{"unit_charge":"6.33"}},"307":{"50":{"unit_charge":"3.86","hp":100}},"308":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"400":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"401":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"501":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"502":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"503":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"504":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"505":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"506":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"507":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"601":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"602":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"603":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"604":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"605":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"700":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"701":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"702":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}},"800":{"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}}
     */

    private CategoryBean category;

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public static class CategoryBean {
        /**
         * 100 : {"50":{"unit_charge":"1.45"},"100":{"unit_charge":"2.60"},"200":{"unit_charge":"3.60"},"201":{"unit_charge":"6.90"}}
         * 101 : {"50":{"unit_charge":"1.45"},"100":{"unit_charge":"2.60"},"200":{"unit_charge":"3.60"},"201":{"unit_charge":"6.90"}}
         * 102 : {"50":{"unit_charge":"2.60"},"100":{"unit_charge":"2.60"},"200":{"unit_charge":"3.60"},"300":{"unit_charge":"6.90"},"301":{"unit_charge":"7.75"}}
         * 103 : {"50":{"unit_charge":"2.68"},"100":{"unit_charge":"3.35"},"200":{"unit_charge":"5.42"},"300":{"unit_charge":"7.11"},"400":{"unit_charge":"7.98"},"500":{"unit_charge":"8.52"},"501":{"unit_charge":"9.06"}}
         * 200 : {"50":{"unit_charge":"5.40"},"51":{"unit_charge":"6.90"},"100":{"unit_charge":"7.69"},"300":{"unit_charge":"9.06"},"500":{"unit_charge":"9.61"},"501":{"unit_charge":"10.19"}}
         * 201 : {"50":{"unit_charge":"5.40"},"51":{"unit_charge":"6.90"},"100":{"unit_charge":"7.69"},"300":{"unit_charge":"9.06"},"500":{"unit_charge":"9.61"},"501":{"unit_charge":"10.19"}}
         * 202 : {"0":{"unit_charge":"12.28"}}
         * 203 : {"0":{"unit_charge":"11.77"}}
         * 300 : {"0":{"unit_charge":"6.71"}}
         * 301 : {"0":{"unit_charge":"3.86"}}
         * 302 : {"0":{"unit_charge":"3.86"}}
         * 303 : {"0":{"unit_charge":"4.89"}}
         * 304 : {"0":{"unit_charge":"5.91"}}
         * 305 : {"0":{"unit_charge":"5.91"}}
         * 306 : {"0":{"unit_charge":"6.33"}}
         * 307 : {"50":{"unit_charge":"3.86","hp":100}}
         * 308 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 400 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 401 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 501 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 502 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 503 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 504 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 505 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 506 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 507 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 601 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 602 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 603 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 604 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 605 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 700 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 701 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 702 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         * 800 : {"50":{"unit_charge":"1.46"},"100":{"unit_charge":"1.46"},"200":{"unit_charge":"1.46"},"201":{"unit_charge":"1.46"}}
         */

        @SerializedName("100")
        private _$100BeanX _$100;
        @SerializedName("101")
        private _$101Bean _$101;
        @SerializedName("102")
        private _$102Bean _$102;
        @SerializedName("103")
        private _$103Bean _$103;
        @SerializedName("200")
        private _$200BeanXXXX _$200;
        @SerializedName("201")
        private _$201BeanXX _$201;
        @SerializedName("202")
        private _$202Bean _$202;
        @SerializedName("203")
        private _$203Bean _$203;
        @SerializedName("300")
        private _$300BeanXXXX _$300;
        @SerializedName("301")
        private _$301BeanX _$301;
        @SerializedName("302")
        private _$302Bean _$302;
        @SerializedName("303")
        private _$303Bean _$303;
        @SerializedName("304")
        private _$304Bean _$304;
        @SerializedName("305")
        private _$305Bean _$305;
        @SerializedName("306")
        private _$306Bean _$306;
        @SerializedName("307")
        private _$307Bean _$307;
        @SerializedName("308")
        private _$308Bean _$308;
        @SerializedName("400")
        private _$400BeanX _$400;
        @SerializedName("401")
        private _$401Bean _$401;
        @SerializedName("501")
        private _$501BeanXXX _$501;
        @SerializedName("502")
        private _$502Bean _$502;
        @SerializedName("503")
        private _$503Bean _$503;
        @SerializedName("504")
        private _$504Bean _$504;
        @SerializedName("505")
        private _$505Bean _$505;
        @SerializedName("506")
        private _$506Bean _$506;
        @SerializedName("507")
        private _$507Bean _$507;
        @SerializedName("601")
        private _$601Bean _$601;
        @SerializedName("602")
        private _$602Bean _$602;
        @SerializedName("603")
        private _$603Bean _$603;
        @SerializedName("604")
        private _$604Bean _$604;
        @SerializedName("605")
        private _$605Bean _$605;
        @SerializedName("700")
        private _$700Bean _$700;
        @SerializedName("701")
        private _$701Bean _$701;
        @SerializedName("702")
        private _$702Bean _$702;
        @SerializedName("800")
        private _$800Bean _$800;

        public _$100BeanX get_$100() {
            return _$100;
        }

        public void set_$100(_$100BeanX _$100) {
            this._$100 = _$100;
        }

        public _$101Bean get_$101() {
            return _$101;
        }

        public void set_$101(_$101Bean _$101) {
            this._$101 = _$101;
        }

        public _$102Bean get_$102() {
            return _$102;
        }

        public void set_$102(_$102Bean _$102) {
            this._$102 = _$102;
        }

        public _$103Bean get_$103() {
            return _$103;
        }

        public void set_$103(_$103Bean _$103) {
            this._$103 = _$103;
        }

        public _$200BeanXXXX get_$200() {
            return _$200;
        }

        public void set_$200(_$200BeanXXXX _$200) {
            this._$200 = _$200;
        }

        public _$201BeanXX get_$201() {
            return _$201;
        }

        public void set_$201(_$201BeanXX _$201) {
            this._$201 = _$201;
        }

        public _$202Bean get_$202() {
            return _$202;
        }

        public void set_$202(_$202Bean _$202) {
            this._$202 = _$202;
        }

        public _$203Bean get_$203() {
            return _$203;
        }

        public void set_$203(_$203Bean _$203) {
            this._$203 = _$203;
        }

        public _$300BeanXXXX get_$300() {
            return _$300;
        }

        public void set_$300(_$300BeanXXXX _$300) {
            this._$300 = _$300;
        }

        public _$301BeanX get_$301() {
            return _$301;
        }

        public void set_$301(_$301BeanX _$301) {
            this._$301 = _$301;
        }

        public _$302Bean get_$302() {
            return _$302;
        }

        public void set_$302(_$302Bean _$302) {
            this._$302 = _$302;
        }

        public _$303Bean get_$303() {
            return _$303;
        }

        public void set_$303(_$303Bean _$303) {
            this._$303 = _$303;
        }

        public _$304Bean get_$304() {
            return _$304;
        }

        public void set_$304(_$304Bean _$304) {
            this._$304 = _$304;
        }

        public _$305Bean get_$305() {
            return _$305;
        }

        public void set_$305(_$305Bean _$305) {
            this._$305 = _$305;
        }

        public _$306Bean get_$306() {
            return _$306;
        }

        public void set_$306(_$306Bean _$306) {
            this._$306 = _$306;
        }

        public _$307Bean get_$307() {
            return _$307;
        }

        public void set_$307(_$307Bean _$307) {
            this._$307 = _$307;
        }

        public _$308Bean get_$308() {
            return _$308;
        }

        public void set_$308(_$308Bean _$308) {
            this._$308 = _$308;
        }

        public _$400BeanX get_$400() {
            return _$400;
        }

        public void set_$400(_$400BeanX _$400) {
            this._$400 = _$400;
        }

        public _$401Bean get_$401() {
            return _$401;
        }

        public void set_$401(_$401Bean _$401) {
            this._$401 = _$401;
        }

        public _$501BeanXXX get_$501() {
            return _$501;
        }

        public void set_$501(_$501BeanXXX _$501) {
            this._$501 = _$501;
        }

        public _$502Bean get_$502() {
            return _$502;
        }

        public void set_$502(_$502Bean _$502) {
            this._$502 = _$502;
        }

        public _$503Bean get_$503() {
            return _$503;
        }

        public void set_$503(_$503Bean _$503) {
            this._$503 = _$503;
        }

        public _$504Bean get_$504() {
            return _$504;
        }

        public void set_$504(_$504Bean _$504) {
            this._$504 = _$504;
        }

        public _$505Bean get_$505() {
            return _$505;
        }

        public void set_$505(_$505Bean _$505) {
            this._$505 = _$505;
        }

        public _$506Bean get_$506() {
            return _$506;
        }

        public void set_$506(_$506Bean _$506) {
            this._$506 = _$506;
        }

        public _$507Bean get_$507() {
            return _$507;
        }

        public void set_$507(_$507Bean _$507) {
            this._$507 = _$507;
        }

        public _$601Bean get_$601() {
            return _$601;
        }

        public void set_$601(_$601Bean _$601) {
            this._$601 = _$601;
        }

        public _$602Bean get_$602() {
            return _$602;
        }

        public void set_$602(_$602Bean _$602) {
            this._$602 = _$602;
        }

        public _$603Bean get_$603() {
            return _$603;
        }

        public void set_$603(_$603Bean _$603) {
            this._$603 = _$603;
        }

        public _$604Bean get_$604() {
            return _$604;
        }

        public void set_$604(_$604Bean _$604) {
            this._$604 = _$604;
        }

        public _$605Bean get_$605() {
            return _$605;
        }

        public void set_$605(_$605Bean _$605) {
            this._$605 = _$605;
        }

        public _$700Bean get_$700() {
            return _$700;
        }

        public void set_$700(_$700Bean _$700) {
            this._$700 = _$700;
        }

        public _$701Bean get_$701() {
            return _$701;
        }

        public void set_$701(_$701Bean _$701) {
            this._$701 = _$701;
        }

        public _$702Bean get_$702() {
            return _$702;
        }

        public void set_$702(_$702Bean _$702) {
            this._$702 = _$702;
        }

        public _$800Bean get_$800() {
            return _$800;
        }

        public void set_$800(_$800Bean _$800) {
            this._$800 = _$800;
        }

        public static class _$100BeanX {
            /**
             * 50 : {"unit_charge":"1.45"}
             * 100 : {"unit_charge":"2.60"}
             * 200 : {"unit_charge":"3.60"}
             * 201 : {"unit_charge":"6.90"}
             */

            @SerializedName("50")
            private _$50Bean _$50;
            @SerializedName("100")
            private _$100Bean _$100;
            @SerializedName("200")
            private _$200Bean _$200;
            @SerializedName("201")
            private _$201Bean _$201;

            public _$50Bean get_$50() {
                return _$50;
            }

            public void set_$50(_$50Bean _$50) {
                this._$50 = _$50;
            }

            public _$100Bean get_$100() {
                return _$100;
            }

            public void set_$100(_$100Bean _$100) {
                this._$100 = _$100;
            }

            public _$200Bean get_$200() {
                return _$200;
            }

            public void set_$200(_$200Bean _$200) {
                this._$200 = _$200;
            }

            public _$201Bean get_$201() {
                return _$201;
            }

            public void set_$201(_$201Bean _$201) {
                this._$201 = _$201;
            }

            public static class _$50Bean {
                /**
                 * unit_charge : 1.45
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100Bean {
                /**
                 * unit_charge : 2.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200Bean {
                /**
                 * unit_charge : 3.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201Bean {
                /**
                 * unit_charge : 6.90
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$101Bean {
            /**
             * 50 : {"unit_charge":"1.45"}
             * 100 : {"unit_charge":"2.60"}
             * 200 : {"unit_charge":"3.60"}
             * 201 : {"unit_charge":"6.90"}
             */

            @SerializedName("50")
            private _$50BeanX _$50;
            @SerializedName("100")
            private _$100BeanXX _$100;
            @SerializedName("200")
            private _$200BeanX _$200;
            @SerializedName("201")
            private _$201BeanX _$201;

            public _$50BeanX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanX {
                /**
                 * unit_charge : 1.45
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXX {
                /**
                 * unit_charge : 2.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanX {
                /**
                 * unit_charge : 3.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanX {
                /**
                 * unit_charge : 6.90
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$102Bean {
            /**
             * 50 : {"unit_charge":"2.60"}
             * 100 : {"unit_charge":"2.60"}
             * 200 : {"unit_charge":"3.60"}
             * 300 : {"unit_charge":"6.90"}
             * 301 : {"unit_charge":"7.75"}
             */

            @SerializedName("50")
            private _$50BeanXX _$50;
            @SerializedName("100")
            private _$100BeanXXX _$100;
            @SerializedName("200")
            private _$200BeanXX _$200;
            @SerializedName("300")
            private _$300Bean _$300;
            @SerializedName("301")
            private _$301Bean _$301;

            public _$50BeanXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXX _$200) {
                this._$200 = _$200;
            }

            public _$300Bean get_$300() {
                return _$300;
            }

            public void set_$300(_$300Bean _$300) {
                this._$300 = _$300;
            }

            public _$301Bean get_$301() {
                return _$301;
            }

            public void set_$301(_$301Bean _$301) {
                this._$301 = _$301;
            }

            public static class _$50BeanXX {
                /**
                 * unit_charge : 2.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXX {
                /**
                 * unit_charge : 2.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXX {
                /**
                 * unit_charge : 3.60
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$300Bean {
                /**
                 * unit_charge : 6.90
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$301Bean {
                /**
                 * unit_charge : 7.75
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$103Bean {
            /**
             * 50 : {"unit_charge":"2.68"}
             * 100 : {"unit_charge":"3.35"}
             * 200 : {"unit_charge":"5.42"}
             * 300 : {"unit_charge":"7.11"}
             * 400 : {"unit_charge":"7.98"}
             * 500 : {"unit_charge":"8.52"}
             * 501 : {"unit_charge":"9.06"}
             */

            @SerializedName("50")
            private _$50BeanXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXX _$200;
            @SerializedName("300")
            private _$300BeanX _$300;
            @SerializedName("400")
            private _$400Bean _$400;
            @SerializedName("500")
            private _$500Bean _$500;
            @SerializedName("501")
            private _$501Bean _$501;

            public _$50BeanXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXX _$200) {
                this._$200 = _$200;
            }

            public _$300BeanX get_$300() {
                return _$300;
            }

            public void set_$300(_$300BeanX _$300) {
                this._$300 = _$300;
            }

            public _$400Bean get_$400() {
                return _$400;
            }

            public void set_$400(_$400Bean _$400) {
                this._$400 = _$400;
            }

            public _$500Bean get_$500() {
                return _$500;
            }

            public void set_$500(_$500Bean _$500) {
                this._$500 = _$500;
            }

            public _$501Bean get_$501() {
                return _$501;
            }

            public void set_$501(_$501Bean _$501) {
                this._$501 = _$501;
            }

            public static class _$50BeanXXX {
                /**
                 * unit_charge : 2.68
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXX {
                /**
                 * unit_charge : 3.35
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXX {
                /**
                 * unit_charge : 5.42
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$300BeanX {
                /**
                 * unit_charge : 7.11
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$400Bean {
                /**
                 * unit_charge : 7.98
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$500Bean {
                /**
                 * unit_charge : 8.52
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$501Bean {
                /**
                 * unit_charge : 9.06
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$200BeanXXXX {
            /**
             * 50 : {"unit_charge":"5.40"}
             * 51 : {"unit_charge":"6.90"}
             * 100 : {"unit_charge":"7.69"}
             * 300 : {"unit_charge":"9.06"}
             * 500 : {"unit_charge":"9.61"}
             * 501 : {"unit_charge":"10.19"}
             */

            @SerializedName("50")
            private _$50BeanXXXX _$50;
            @SerializedName("51")
            private _$51Bean _$51;
            @SerializedName("100")
            private _$100BeanXXXXX _$100;
            @SerializedName("300")
            private _$300BeanXX _$300;
            @SerializedName("500")
            private _$500BeanX _$500;
            @SerializedName("501")
            private _$501BeanX _$501;

            public _$50BeanXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXX _$50) {
                this._$50 = _$50;
            }

            public _$51Bean get_$51() {
                return _$51;
            }

            public void set_$51(_$51Bean _$51) {
                this._$51 = _$51;
            }

            public _$100BeanXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$300BeanXX get_$300() {
                return _$300;
            }

            public void set_$300(_$300BeanXX _$300) {
                this._$300 = _$300;
            }

            public _$500BeanX get_$500() {
                return _$500;
            }

            public void set_$500(_$500BeanX _$500) {
                this._$500 = _$500;
            }

            public _$501BeanX get_$501() {
                return _$501;
            }

            public void set_$501(_$501BeanX _$501) {
                this._$501 = _$501;
            }

            public static class _$50BeanXXXX {
                /**
                 * unit_charge : 5.40
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$51Bean {
                /**
                 * unit_charge : 6.90
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXX {
                /**
                 * unit_charge : 7.69
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$300BeanXX {
                /**
                 * unit_charge : 9.06
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$500BeanX {
                /**
                 * unit_charge : 9.61
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$501BeanX {
                /**
                 * unit_charge : 10.19
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$201BeanXX {
            /**
             * 50 : {"unit_charge":"5.40"}
             * 51 : {"unit_charge":"6.90"}
             * 100 : {"unit_charge":"7.69"}
             * 300 : {"unit_charge":"9.06"}
             * 500 : {"unit_charge":"9.61"}
             * 501 : {"unit_charge":"10.19"}
             */

            @SerializedName("50")
            private _$50BeanXXXXX _$50;
            @SerializedName("51")
            private _$51BeanX _$51;
            @SerializedName("100")
            private _$100BeanXXXXXX _$100;
            @SerializedName("300")
            private _$300BeanXXX _$300;
            @SerializedName("500")
            private _$500BeanXX _$500;
            @SerializedName("501")
            private _$501BeanXX _$501;

            public _$50BeanXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$51BeanX get_$51() {
                return _$51;
            }

            public void set_$51(_$51BeanX _$51) {
                this._$51 = _$51;
            }

            public _$100BeanXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$300BeanXXX get_$300() {
                return _$300;
            }

            public void set_$300(_$300BeanXXX _$300) {
                this._$300 = _$300;
            }

            public _$500BeanXX get_$500() {
                return _$500;
            }

            public void set_$500(_$500BeanXX _$500) {
                this._$500 = _$500;
            }

            public _$501BeanXX get_$501() {
                return _$501;
            }

            public void set_$501(_$501BeanXX _$501) {
                this._$501 = _$501;
            }

            public static class _$50BeanXXXXX {
                /**
                 * unit_charge : 5.40
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$51BeanX {
                /**
                 * unit_charge : 6.90
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXX {
                /**
                 * unit_charge : 7.69
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$300BeanXXX {
                /**
                 * unit_charge : 9.06
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$500BeanXX {
                /**
                 * unit_charge : 9.61
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$501BeanXX {
                /**
                 * unit_charge : 10.19
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$202Bean {
            /**
             * 0 : {"unit_charge":"12.28"}
             */

            @SerializedName("0")
            private _$0Bean _$0;

            public _$0Bean get_$0() {
                return _$0;
            }

            public void set_$0(_$0Bean _$0) {
                this._$0 = _$0;
            }

            public static class _$0Bean {
                /**
                 * unit_charge : 12.28
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$203Bean {
            /**
             * 0 : {"unit_charge":"11.77"}
             */

            @SerializedName("0")
            private _$0BeanX _$0;

            public _$0BeanX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanX {
                /**
                 * unit_charge : 11.77
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$300BeanXXXX {
            /**
             * 0 : {"unit_charge":"6.71"}
             */

            @SerializedName("0")
            private _$0BeanXX _$0;

            public _$0BeanXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXX {
                /**
                 * unit_charge : 6.71
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$301BeanX {
            /**
             * 0 : {"unit_charge":"3.86"}
             */

            @SerializedName("0")
            private _$0BeanXXX _$0;

            public _$0BeanXXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXXX {
                /**
                 * unit_charge : 3.86
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$302Bean {
            /**
             * 0 : {"unit_charge":"3.86"}
             */

            @SerializedName("0")
            private _$0BeanXXXX _$0;

            public _$0BeanXXXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXXXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXXXX {
                /**
                 * unit_charge : 3.86
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$303Bean {
            /**
             * 0 : {"unit_charge":"4.89"}
             */

            @SerializedName("0")
            private _$0BeanXXXXX _$0;

            public _$0BeanXXXXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXXXXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXXXXX {
                /**
                 * unit_charge : 4.89
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$304Bean {
            /**
             * 0 : {"unit_charge":"5.91"}
             */

            @SerializedName("0")
            private _$0BeanXXXXXX _$0;

            public _$0BeanXXXXXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXXXXXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXXXXXX {
                /**
                 * unit_charge : 5.91
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$305Bean {
            /**
             * 0 : {"unit_charge":"5.91"}
             */

            @SerializedName("0")
            private _$0BeanXXXXXXX _$0;

            public _$0BeanXXXXXXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXXXXXXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXXXXXXX {
                /**
                 * unit_charge : 5.91
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$306Bean {
            /**
             * 0 : {"unit_charge":"6.33"}
             */

            @SerializedName("0")
            private _$0BeanXXXXXXXX _$0;

            public _$0BeanXXXXXXXX get_$0() {
                return _$0;
            }

            public void set_$0(_$0BeanXXXXXXXX _$0) {
                this._$0 = _$0;
            }

            public static class _$0BeanXXXXXXXX {
                /**
                 * unit_charge : 6.33
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$307Bean {
            /**
             * 50 : {"unit_charge":"3.86","hp":100}
             */

            @SerializedName("50")
            private _$50BeanXXXXXX _$50;

            public _$50BeanXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXX _$50) {
                this._$50 = _$50;
            }

            public static class _$50BeanXXXXXX {
                /**
                 * unit_charge : 3.86
                 * hp : 100
                 */

                private String unit_charge;
                private int hp;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }

                public int getHp() {
                    return hp;
                }

                public void setHp(int hp) {
                    this.hp = hp;
                }
            }
        }

        public static class _$308Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXX _$201;

            public _$50BeanXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$400BeanX {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXX _$201;

            public _$50BeanXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$401Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXX _$201;

            public _$50BeanXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$501BeanXXX {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXX _$201;

            public _$50BeanXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$502Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$503Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$504Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$505Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$506Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$507Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$601Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$602Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$603Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$604Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$605Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$700Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$701Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$702Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }

        public static class _$800Bean {
            /**
             * 50 : {"unit_charge":"1.46"}
             * 100 : {"unit_charge":"1.46"}
             * 200 : {"unit_charge":"1.46"}
             * 201 : {"unit_charge":"1.46"}
             */

            @SerializedName("50")
            private _$50BeanXXXXXXXXXXXXXXXXXXXXXXXXX _$50;
            @SerializedName("100")
            private _$100BeanXXXXXXXXXXXXXXXXXXXXXXXXX _$100;
            @SerializedName("200")
            private _$200BeanXXXXXXXXXXXXXXXXXXXXXXX _$200;
            @SerializedName("201")
            private _$201BeanXXXXXXXXXXXXXXXXXXXXX _$201;

            public _$50BeanXXXXXXXXXXXXXXXXXXXXXXXXX get_$50() {
                return _$50;
            }

            public void set_$50(_$50BeanXXXXXXXXXXXXXXXXXXXXXXXXX _$50) {
                this._$50 = _$50;
            }

            public _$100BeanXXXXXXXXXXXXXXXXXXXXXXXXX get_$100() {
                return _$100;
            }

            public void set_$100(_$100BeanXXXXXXXXXXXXXXXXXXXXXXXXX _$100) {
                this._$100 = _$100;
            }

            public _$200BeanXXXXXXXXXXXXXXXXXXXXXXX get_$200() {
                return _$200;
            }

            public void set_$200(_$200BeanXXXXXXXXXXXXXXXXXXXXXXX _$200) {
                this._$200 = _$200;
            }

            public _$201BeanXXXXXXXXXXXXXXXXXXXXX get_$201() {
                return _$201;
            }

            public void set_$201(_$201BeanXXXXXXXXXXXXXXXXXXXXX _$201) {
                this._$201 = _$201;
            }

            public static class _$50BeanXXXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$100BeanXXXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$200BeanXXXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }

            public static class _$201BeanXXXXXXXXXXXXXXXXXXXXX {
                /**
                 * unit_charge : 1.46
                 */

                private String unit_charge;

                public String getUnit_charge() {
                    return unit_charge;
                }

                public void setUnit_charge(String unit_charge) {
                    this.unit_charge = unit_charge;
                }
            }
        }
    }
}
