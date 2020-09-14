package com.hly.learn.util;

import java.util.List;

public class WeatherInfo {
    private DataBean data;
    private int status;
    private String desc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class DataBean {
        private YesterdayBean yesterday;
        private String city;
        private String aqi;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String toString() {
                return "{" + "\"date\":\"" + getDate() + "\"," + "\"high\":\"" + getHigh() + "\","
                        + "\"fx\":\"" + getFx() + "\"," + "\"low\":\"" + getLow() + "\","
                        + "\"fl\":\"" + getFl() +
                        "\"," + "\"type\":\"" + getType() + "\"}";
            }
        }

        public static class ForecastBean {

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String toString() {
                return "{" + "\"date\":\"" + getDate() + "\"," + "\"fengli\":\"" + getFengli()
                        + "\"," + "\"dengxiang\":\"" + getFengxiang() + "\"," + "\"high\":\""
                        + getHigh() + "\"," + "\"low\":\"" + getLow() + "\"," + "\"type\":\""
                        + getType() + "\"}";
            }
        }

        public String toString() {
            String s = "";
            for (ForecastBean fb : forecast) {
                s += fb.toString() + ",";
            }
            return "\"data\":" + "{" + "\"yesterday\":" + getYesterday().toString() + "," +
                    "\"city\":\"" + getCity() + "\"," + "\"forest\":" + "[" + s + "]" + "," +
                    "\"ganmao\":\"" + getGanmao() + "\"," + "\"wendu\":\"" + getWendu() + "\""
                    + "}";
        }
    }

    public String toString() {
        return "{" + getData().toString() + "," + "\"status\":" + getStatus() + "," + "\"desc\":\""
                + getDesc() + "\"" + "}";
    }
}
