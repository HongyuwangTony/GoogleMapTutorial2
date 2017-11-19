package com.example.android.googlemaptutorial2;


public class TransportationStops {
    private String changeset_id;
    private boolean new_changeset;
    private String time;
    private StatusCode status;
    private RequestData rqst;
    private StopInfo[] stops;

    public TransportationStops() {
    }

    public TransportationStops(String changeset_id, boolean new_changeset, String time, StatusCode status, RequestData rqst, StopInfo[] stops) {
        this.changeset_id = changeset_id;
        this.new_changeset = new_changeset;
        this.time = time;
        this.status = status;
        this.rqst = rqst;
        this.stops = stops;
    }

    public String getChangeset_id() {
        return changeset_id;
    }

    public void setChangeset_id(String changeset_id) {
        this.changeset_id = changeset_id;
    }

    public boolean isNew_changeset() {
        return new_changeset;
    }

    public void setNew_changeset(boolean new_changeset) {
        this.new_changeset = new_changeset;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StatusCode getStatus() {
        return status;
    }

    public void setStatus(StatusCode status) {
        this.status = status;
    }

    public RequestData getRqst() {
        return rqst;
    }

    public void setRqst(RequestData rqst) {
        this.rqst = rqst;
    }

    public StopInfo[] getStops() {
        return stops;
    }

    public void setStops(StopInfo[] stops) {
        this.stops = stops;
    }

    public class StopInfo{
        private String stop_id;
        private String stop_name;
        private String code;
        private double distance;
        private StopPointsData[] stop_points;

        public StopInfo() {
        }

        public StopInfo(String stop_id, String stop_name, String code, double distance, StopPointsData[] stop_points) {
            this.stop_id = stop_id;
            this.stop_name = stop_name;
            this.code = code;
            this.distance = distance;
            this.stop_points = stop_points;
        }

        public String getStop_id() {
            return stop_id;
        }

        public void setStop_id(String stop_id) {
            this.stop_id = stop_id;
        }

        public String getStop_name() {
            return stop_name;
        }

        public void setStop_name(String stop_name) {
            this.stop_name = stop_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public StopPointsData[] getStop_points() {
            return stop_points;
        }

        public void setStop_points(StopPointsData[] stop_points) {
            this.stop_points = stop_points;
        }

        public class StopPointsData{
            private String code;
            private String stop_id;
            private double stop_lat;
            private double stop_lon;
            private String stop_name;

            public StopPointsData() {
            }

            public StopPointsData(String code, String stop_id, double stop_lat, double stop_lon, String stop_name) {
                this.code = code;
                this.stop_id = stop_id;
                this.stop_lat = stop_lat;
                this.stop_lon = stop_lon;
                this.stop_name = stop_name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getStop_id() {
                return stop_id;
            }

            public void setStop_id(String stop_id) {
                this.stop_id = stop_id;
            }

            public double getStop_lat() {
                return stop_lat;
            }

            public void setStop_lat(double stop_lat) {
                this.stop_lat = stop_lat;
            }

            public double getStop_lon() {
                return stop_lon;
            }

            public void setStop_lon(double stop_lon) {
                this.stop_lon = stop_lon;
            }

            public String getStop_name() {
                return stop_name;
            }

            public void setStop_name(String stop_name) {
                this.stop_name = stop_name;
            }
        }
    }
    public class RequestData{
        private String method;
        private Coordinate params;

        public RequestData() {
        }

        public RequestData(String method, Coordinate params) {
            this.method = method;
            this.params = params;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Coordinate getParams() {
            return params;
        }

        public void setParams(Coordinate params) {
            this.params = params;
        }

        public class Coordinate{
            private double lat;
            private double lon;

            public Coordinate() {
            }

            public Coordinate(double lat, double lon) {
                this.lat = lat;
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }
    }
    public class StatusCode{
        private int code;
        private String msg;

        public StatusCode() {
        }

        public StatusCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}

